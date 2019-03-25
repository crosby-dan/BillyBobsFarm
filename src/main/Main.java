package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import java.util.Random; 

public class Main {

	static ArrayList<Farm> list = new ArrayList<>();
	private static int playerCount;
	
	//Initialize variable constants
	final static boolean verbose=false;
	final static int maxRounds=10;
	final static String[] plants = { "Carrot", "Tomato", "Potato", "Corn", "Watermelon"};
	final static double[] squareFootage = {.2,1,.5,.5,5};
	final static int[] maturityRounds = {1,2,2,3,5};
	final static double[] baseSeedCost = {.02,.15,.20,.10,.25};
	final static double[] baseMarketPrice = {.20,1.5,1,.75,7.5};
	//Array element 1=plant, 2=round
	static double[][] seedCost;
	//Array element 1=plant, 2=round
	static double[][] marketPrice;
	
	public static void main(String[] args) {
		System.out.println("\r\n" + 
				"    _______   __  __  __                  _______             __       __               ________                                \r\n" + 
				"   /       \\ /  |/  |/  |                /       \\           /  |     /  |             /        |                               \r\n" + 
				"   $$$$$$$  |$$/ $$ |$$ | __    __       $$$$$$$  |  ______  $$ |____ $$/_______       $$$$$$$$/______    ______   _____  ____  \r\n" + 
				"   $$ |__$$ |/  |$$ |$$ |/  |  /  |      $$ |__$$ | /      \\ $$      \\$//       |      $$ |__  /      \\  /      \\ /     \\/    \\ \r\n" + 
				"   $$    $$< $$ |$$ |$$ |$$ |  $$ |      $$    $$< /$$$$$$  |$$$$$$$  |/$$$$$$$/       $$    | $$$$$$  |/$$$$$$  |$$$$$$ $$$$  |\r\n" + 
				"   $$$$$$$  |$$ |$$ |$$ |$$ |  $$ |      $$$$$$$  |$$ |  $$ |$$ |  $$ |$$      \\       $$$$$/  /    $$ |$$ |  $$/ $$ | $$ | $$ |\r\n" + 
				"   $$ |__$$ |$$ |$$ |$$ |$$ \\__$$ |      $$ |__$$ |$$ \\__$$ |$$ |__$$ | $$$$$$  |      $$ |   /$$$$$$$ |$$ |      $$ | $$ | $$ |\r\n" + 
				"   $$    $$/ $$ |$$ |$$ |$$    $$ |      $$    $$/ $$    $$/ $$    $$/ /     $$/       $$ |   $$    $$ |$$ |      $$ | $$ | $$ |\r\n" + 
				"   $$$$$$$/  $$/ $$/ $$/  $$$$$$$ |      $$$$$$$/   $$$$$$/  $$$$$$$/  $$$$$$$/        $$/     $$$$$$$/ $$/       $$/  $$/  $$/ \r\n" + 
				"   ______                /  \\__$$ |                                                                                             \r\n" + 
				"    |   |                $$    $$/                                                                                              \r\n" + 
				"    | __|-----|           $$$$$$/                                                                                               \r\n" + 
				"    |.'_'.---.|  .................. Created by Dan Crosby & Leanne Kendrick for CIT 260 BYU Idaho ...................................\r\n" +
				"     '._.'   ( ) ................. Font bigmoney-sw credit to nathan bloomfield (xzovik@gmail.com) ..................................\r\n");
	
		//This block of code will capture the player name.
		Pattern pattern = Pattern.compile("(0|1|2|3|4)");
		Matcher matcher;
		boolean matchResult;
		do {
			System.out.format("\nPlease enter the number of players (1 to 4): ");
			Scanner in = new Scanner (System.in);
			String input=in.nextLine();
			matcher = pattern.matcher(input);
			matchResult=matcher.find();
			if (!matchResult) System.out.println("Invalid player count (1 to 4)."); 
		}
		while (!matchResult);

		playerCount=Integer.parseInt(matcher.group(1));
		if (playerCount<1) {
			//Invalid player count after we've already made an effort to get a valid value, abort program.
			System.out.println("Invalid player count, exiting program.");
			return;
		}
		
		for (int i=0; i<playerCount;i++) {
			//This block of code will capture the player name.
			pattern = Pattern.compile("(\\w{3,10})");
			do {
				System.out.format("\nPlayer %d, please enter your first name (3 to 10 digits): ",i+1);
				@SuppressWarnings("resource")
				Scanner in = new Scanner (System.in);
				String input=in.nextLine();
				matcher = pattern.matcher(input);
				matchResult=matcher.find();
				if (!matchResult) System.out.println("Invalid first name, please try again."); 
			}
			while (!matchResult);
	
			//Create a farm for the new player with a farm size of 50 square feet.   
			list.add(new Farm(matcher.group(1),50));
		}

		//System.out.format("Example of using the seedCost array for Watermelon: %s\n", getPlantIndex("Watermelon"));
		//System.out.format("Example of using the seedCost array for Carrot: %s\n", getPlantIndex("Carrot"));
		MainMenu();
	}
	
	public static int getPlantIndex(String plantName) {
		int i=0;
		for (String element: plants) {
			if (element.equalsIgnoreCase(plantName)) 
				return i;
			else
				i++;
		}
		System.out.format("Error: Failed to locate plant named '%s'.  Valid values include: %s\n",plantName,Arrays.toString(plants));
		throw new NoSuchElementException();
	}
		
	public static void MainMenu() {
		//This block of code will capture the commands S=Start Game, H=High Scores, Q=Quit
		setPrices();
		
		Pattern pattern = Pattern.compile("(?i).*?(s|h|q).*?");
		Matcher matcher;
		boolean matchResult;
		
		do {
			System.out.format("\nEnter S to start new game, H for high scores, or Q for quit.\n");
			@SuppressWarnings("resource")
			Scanner in = new Scanner (System.in);
			String input=in.nextLine();
			matcher = pattern.matcher(input);
			matchResult=matcher.find();
			if (!matchResult) System.out.println("\nEnter S to start new game, H for high scores, or Q for quit.\n"); 
		}
		while (!matchResult);
		//assign the filled in or not value to a variable
		switch (matcher.group(1)) {
		case "S": 
			StartGame();  
			break;
		case "H":
			HighScores();
			break;
		case "Q":
			return;
		}
	}
	
	public static void HighScores() {
		System.out.println ("\r\n" + 
				"      __    __  __            __               ______                                                    \r\n" + 
				"     /  |  /  |/  |          /  |             /      \\                                                   \r\n" + 
				"     $$ |  $$ |$$/   ______  $$ |____        /$$$$$$  |  _______   ______    ______    ______    _______ \r\n" + 
				"     $$ |__$$ |/  | /      \\ $$      \\       $$ \\__$$/  /       | /      \\  /      \\  /      \\  /       |\r\n" + 
				"     $$    $$ |$$ |/$$$$$$  |$$$$$$$  |      $$      \\ /$$$$$$$/ /$$$$$$  |/$$$$$$  |/$$$$$$  |/$$$$$$$/ \r\n" + 
				"     $$$$$$$$ |$$ |$$ |  $$ |$$ |  $$ |       $$$$$$  |$$ |      $$ |  $$ |$$ |  $$/ $$    $$ |$$      \\ \r\n" + 
				"     $$ |  $$ |$$ |$$ \\__$$ |$$ |  $$ |      /  \\__$$ |$$ \\_____ $$ \\__$$ |$$ |      $$$$$$$$/  $$$$$$  |\r\n" + 
				"     $$ |  $$ |$$ |$$    $$ |$$ |  $$ |      $$    $$/ $$       |$$    $$/ $$ |      $$       |/     $$/ \r\n" + 
				"     $$/   $$/ $$/  $$$$$$$ |$$/   $$/        $$$$$$/   $$$$$$$/  $$$$$$/  $$/        $$$$$$$/ $$$$$$$/  \r\n" + 
				"                   /  \\__$$ |                                                                            \r\n" + 
				"                   $$    $$/                                                                             \r\n" + 
				"                    $$$$$$/                                                                              \r\n"  );
		

		//Add code here to open high score files and then print it out to the console.
	}
	
	public static void StartGame() {
		boolean roundSuccess=true;
		
		//Display welcome message to each player
		for (int i=0; i<list.size(); i++) {
			System.out.format("Welcome player %s!\n", list.get(i).playerName);
		}

		//Display welcome message to each player
		for (int k=1; k<=maxRounds && roundSuccess; k++) {
			roundSuccess=ProcessGameRound(k);
		}
	
	
	}
	
	/**
	 * Loops through each player and gathers input for commands that can be processed by each player
	 * in each round.
	 * @param int round The number of the round being processed
	 * @return      void
	 */
	public static boolean ProcessGameRound(int round) {
		
		//Display welcome message to each player
		for (int i=0; i<list.size(); i++) {
			System.out.format("**----------------------------**\n");
			System.out.format("Round %d Player %s\n", round,list.get(i).playerName);

		//Display cost and forecasted market prices
		showPrices(round);
			
		//This block of code will capture the commands S=Start Game, H=High Scores, Q=Quit
		Pattern pattern = Pattern.compile("(?i)(buy|quit|no changes)?\\s?(\\d{1,3})?\\s?(tomato|carrot|water|corn|potato)?.*");
		Matcher matcher;
		boolean matchResult;
		
		//until a user enters Quit or No Changes commands, capture user commands such as "buy 3 carrots".
		do {
			System.out.format("Enter commands, i.e. 'Buy 3 carrots', or 'no changes'.\n");
			@SuppressWarnings("resource")
			Scanner in = new Scanner (System.in);
			String input=in.nextLine();
			matcher = pattern.matcher(input);
			matchResult=matcher.find();
			if (!matchResult) 
				{
					System.out.println("\\nEnter commands, i.e. 'Buy 3 carrots', or 'no changes.\n"); 
				}
			else 
			{
				System.out.format("Received command: %s",matcher.group(1));
				switch (matcher.group(1).toUpperCase()) {
				case "BUY": 
					System.out.format("Attempting to buy quantity: %s of %s\n",matcher.group(2),matcher.group(3));

					//Validate the number of vegetable purchased as being between 1 and 999
					//Validate the name of the vegetable purchased (i.e. make sure it is present)
					int plant=getPlantIndex(matcher.group(3));
					int qty=Integer.parseInt(matcher.group(2));
					if (plant>=0 && plant<=plants.length && qty>=1 && qty<=999) 
					{
						//valid add plant command received
						
						//Calculate the total cost and verify funds are available
						//Calculate total space required and verify it is available
						//Get user confirmation for purchase
						//Call buyPlants method using polymorphism
						//Display updated inventory & cash
					}
					break;
				case "NO CHANGES":
					System.out.println("\n*** No additional changes, round will now be processed. ***");
					// allow the loop to capture more command input to exit
					break;
				case "QUIT":
					System.out.println("\n*** Quit command received; Billy Bob is going home disappointed. ***");
					return false;
				}		
				
			}
		}
		while (!matchResult || (matcher.group(1).toUpperCase()!="QUIT" && matcher.group(1).toUpperCase()!="NO CHANGES"));
		}
	
		return true;
	}

	/**
	 * For the selected round, show the current seed costs.
	 * For the upcoming round, show forecasted market prices.
	 * @param int round Display seed costs & forecasted prices for this game round.
	 * @return      void
	 */
	private static void showPrices(int round) {
		System.out.println("Plant      Seed Cost      Forecasted Market Price");
		String costDescription, priceDescription="";
		for (int plant=0; plant<plants.length; plant++) {

			//Calculations for seed cost display message
			double difference=seedCost[plant][round]/baseSeedCost[plant];
			if (seedCost[plant][round]==baseSeedCost[plant]) {
				costDescription = String.format("$%5.2f              ",baseSeedCost[plant]);
			}
			else if (difference<1){
				if (verbose) 
					costDescription = String.format("$%5.2f ***On Sale***  (%3.0f%% discount) %.2f",seedCost[plant][round],(difference-1)*-100,baseSeedCost[plant]);
				else
					costDescription = String.format("$%5.2f ***On Sale***  (%3.0f%% discount)",seedCost[plant][round],(difference-1)*-100);
			}
			else {
				if (verbose) 
					costDescription = String.format("$%5.2f ---Shortage--  (%3.0f%% premium ) %.2f",seedCost[plant][round],(difference-1)*100,baseSeedCost[plant]);
				else
					costDescription = String.format("$%5.2f ---Shortage--  (%3.0f%% premium )",seedCost[plant][round],(difference-1)*100);
			}

			//Calculations for market price display message
			//A current weakness of this code is it only forecasts 1 round ahead
			//which doesn't help someone with plant maturities of >1 round.
			for (int x=round+1;x<maxRounds;x++) {
				if (round+1<maxRounds) {
					difference=marketPrice[plant][x]/baseMarketPrice[plant];
					if (marketPrice[plant][x]==baseMarketPrice[plant]) {
						priceDescription = "..";
					}
					else if (difference<1){
						if (verbose)
							priceDescription = priceDescription + String.format("---(%3.0f%% discount) %.2f",(difference-1)*-100,baseMarketPrice[plant]);
						else
							priceDescription = priceDescription + "--";
					}
					else {
						if (verbose)
							priceDescription = priceDescription + String.format("+++(%3.0f%% premium ) %.2f",(difference-1)*100,baseMarketPrice[plant]);
						else
							priceDescription = "--";
					}
				}
			}

			System.out.format("%14s  %15s  %15s\n" ,plants[plant],costDescription,priceDescription);
			//next line is full output for debug use only
			//System.out.format("Plant: %s, Base seed cost: %5.3f Round %d cost: %5.3f, Base market price: %5.3f Market Price: %5.3f\n" ,plants[plant],baseSeedCost[plant],round+1,seedCost[plant][round],baseMarketPrice[plant],marketPrice[plant][round]);
		}
	}
	

	/**
	 * Loops through all plants and rounds and calculates seed costs and market prices.
	 * @return      void
	 */
	private static void setPrices() {
		//Array element 1=plant, 2=round
		seedCost=new double[plants.length][maxRounds];
		//marketPrice=new double[plants.length][maxRounds];
		marketPrice=new double[plants.length][maxRounds];
		for (int plant=0; plant<plants.length; plant++) {
			for (int round=0; round<maxRounds; round++) {
				seedCost[plant][round]=roundDigits(baseSeedCost[plant]+baseSeedCost[plant]*((Math.random()-.5)),2);
				marketPrice[plant][round]=baseMarketPrice[plant]+baseMarketPrice[plant]*((Math.random()-.5));
				//this next line is for debugging only
				System.out.format("Plant: %d, Base seed cost: %5.2f Round %d cost: %5.2f, Base market price: %5.2f Market Price: %5.2f\n" , plant,baseSeedCost[plant],round+1,seedCost[plant][round],baseMarketPrice[plant],marketPrice[plant][round]);
			}
		}		
	}

	
	/**
	 * Round a number to the provided precision
	 * @param - double for the number to be rounded
	 * @param - integer for the number of digits of precision
	 * @return double
	 */
	public static double roundDigits (double number,int digits) {
		number=(float)(int)(number*Math.pow(10, digits)+.5);
		number=number/Math.pow(10, digits);
		return number;
	}
	
}
