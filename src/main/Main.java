package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
//import java.util.Random; 

public class Main {

	protected static ArrayList<Farm> farmList = new ArrayList<>();
	private static int playerCount;
	
	//Initialize global properties
	final static int maxRounds=10;
	final static int totalSquareFeet=25;
	final static double startingMoney=2;
	
	//Arrays to initialize plant level properties
	final static String[] plants = { "Carrot", "Tomato", "Potato", "Corn", "Watermelon"};
	final static double[] squareFootage = {.2,1,.5,.5,5};
	final static int[] maturityRounds = {1,2,2,3,5};
	final static double[] baseSeedCost = {.02,.15,.20,.10,.25};
	final static double[] baseMarketPrice = {.20,1.5,1,.75,7.5};

	//This is the number of rounds in which plants produce crops
	final static int[] harvestRounds = {1, 5, 1, 3, 2};
	
	//Array element 1=plant, 2=round
	static double[][] seedCost;
	//Array element 1=plant, 2=round
	static double[][] marketPrice;
	static int currentRound;
	static int currentFarm;
	
	/**
	 * Main program execution thread
	 * @param String[] args - which are not used in this method.
	 * @return      void
	 */
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
			@SuppressWarnings("resource")
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
			farmList.add(new Farm(matcher.group(1),totalSquareFeet,startingMoney));
		}

		//System.out.format("Example of using the seedCost array for Watermelon: %s\n", getPlantIndex("Watermelon"));
		//System.out.format("Example of using the seedCost array for Carrot: %s\n", getPlantIndex("Carrot"));
		MainMenu();
	}
	
	/**
	 * Loops through each of the "plants" array and if the provided plant name matches, return the index.
	 * @param String plantName
	 * @return int
	 */	
	public static int getPlantIndex(String plantName) {
		int i=0;
		for (String element: plants) {
			if (element.equalsIgnoreCase(plantName)) 
				return i;
			else
				i++;
		}
		System.out.format("Error: Failed to locate plant named '%s'.  Valid values include: %s\n",plantName,Arrays.toString(plants));
		// TODO Leanne - replace this with one of the new custom Exception classes.
		throw new NoSuchElementException();
	}
		
	/**
	 * Main menu with options for high scores, quit, and play game.
	 * @return      void
	 */
	private static void MainMenu() {
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
			startGame();  
			break;
		case "H":
			highScores();
			break;
		case "Q":
			return;
		}
	}
	
	/**
	 * Display high scores from file
	 * @return      void
	 */
	public static void highScores() {
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
	
	/**
	 * Play the game:
	 * Loop through each round
	 * 	  Loop through each player within each round
	 * 	  Player moves to the next round by typing "no changes"
	 * 	  If the player types Quit (and then ProcessGameRound returns false), then the game should end.
	 * @return      void
	 */
	public static void startGame() {
		boolean roundSuccess=true;
		
		//Display welcome message to each player
		for (currentRound=1; currentRound<=maxRounds && roundSuccess; currentRound++) {
			for (currentFarm=0; currentFarm<farmList.size(); currentFarm++) {
				System.out.format("Welcome player %s on farm %d!\n", farmList.get(currentFarm).getPlayerName(), currentFarm);
				roundSuccess=processGameRound();
				//graceful abort if the user entered quit within the round.
				if (!roundSuccess) return;
			}
			//System.out.format("Advancing to next round.");
			//At the end of the round, loop through each plant and check progress
			for (currentFarm=0; currentFarm<farmList.size(); currentFarm++) {
				//System.out.format("Updates for player %s on farm %d!\n", farmList.get(currentFarm).playerName, currentFarm);
			    farmList.get(currentFarm).processRound();
			}
		}
	}
	
	/**
	 * Loops through each player and gathers input for commands that can be processed by each player
	 * in each round.
	 * @param int round The number of the round being processed
	 * @return      void
	 */
	public static boolean processGameRound() {
		
		Pattern pattern = Pattern.compile("(?i)(buy|quit|no changes)?\\s?(\\d{1,3})?\\s?(tomato|carrot|watermelon|corn|potato)?.*");
		Matcher matcher;
		boolean matchResult;
		
		//This block of code will capture the commands S=Start Game, H=High Scores, Q=Quit
		//until a user enters Quit or No Changes commands, capture user commands such as "buy 3 carrots".
		showPrices();
		do {
			//System.out.format("Purchased %d of %s seed(s) for %5.2f.  Player cash is %5.2f, available space is %5.1f.\n",quantity,getType(),plantCost,Main.farmList.get(Main.currentFarm).playerCash,Main.totalSquareFeet-Main.farmList.get(Main.currentFarm).spaceUsed);
			System.out.format("Round %d Player %s Cash $%5.2f Space %4.1f:  Enter commands, 'Buy 3 carrots', 'Quit' or 'no changes'>>\n", currentRound,farmList.get(currentFarm).getPlayerName(),farmList.get(currentFarm).getPlayerCash(),farmList.get(Main.currentFarm).getSpaceAvailable());
			@SuppressWarnings("resource")
			Scanner in = new Scanner (System.in);
			String input=in.nextLine();
			matcher = pattern.matcher(input);
			matchResult=matcher.find();
			if (!matchResult || matcher.group(1)==null) 
				{
					System.out.println("\\nEnter commands, i.e. 'Buy 3 carrots', or 'no changes.\n"); 
				}
			else 
			{
				//System.out.format("Received command: %s",matcher.group(1));
				switch (matcher.group(1).toUpperCase()) {
				case "BUY": 
					//System.out.format("Attempting to buy quantity: %s of %s\n",matcher.group(2),matcher.group(3));

					//Validate the number of vegetable purchased as being between 1 and 999
					//Validate the name of the vegetable purchased (i.e. make sure it is present)
					int plant=getPlantIndex(matcher.group(3));
					int qty=Integer.parseInt(matcher.group(2));

					//System.out.format("Attempting to buy quantity: %d of plant %d\n",qty,plant);
					
					if (plant>=0 && plant<=plants.length && qty>=1 && qty<=999) 
					{
						//valid add plant command received

						//The AddPlant call is in a try block since this may fail if there is
						//insufficient cash or insufficient land space available.
						try {
							farmList.get(currentFarm).addPlant(plant,qty);
						}
						catch (Exception ex) {
							System.out.format("Failed to complete purchase.  Command cancelled.\n");
						}
					}
					break;
				case "NO CHANGES":
					// allow the loop to capture more command input to exit
					break;
				case "QUIT":
					System.out.println("\n*** Quit command received; Billy Bob is going home disappointed. ***");
					return false;
				}		
			}
		}
		while (!matchResult || matcher.group(1)==null || (!matcher.group(1).equalsIgnoreCase("QUIT") && !matcher.group(1).equalsIgnoreCase("NO CHANGES")));
		//System.out.println("\n*** No additional changes, round will now be processed. ***");
	
		return true;
	}

	/**
	 * For the selected round, show the current seed costs.
	 * For the upcoming round, show forecasted market prices.
	 * @param int round Display seed costs & forecasted prices for this game round.
	 * @return      void
	 */
	private static void showPrices() {
		//clear some blank lines so that the rounds don't blend together in the output
		System.out.println("\n\n\n\n\n\n\n\n");
		//show the seed & vegetable market information
		System.out.format("**-----------------------------------------------------------------------------------------**\n");
		System.out.format("**------------------------------Stormin' Norman's Farmers Market---------------------------**\n");
		System.out.format("**-----------------------------------------------------------------------------------------**\n");
		System.out.println("** Plant       Seed    Current          Space      Rounds     Harvest  Harvest             **");
		System.out.println("**             Cost    Promotion        Required   to mature  Price    Forecast            **");
		String costDescription, priceDescription="";
		for (int plant=0; plant<plants.length; plant++) {

			//Calculations for seed cost display message
			double difference=seedCost[plant][currentRound]/baseSeedCost[plant];
			if (seedCost[plant][currentRound]==baseSeedCost[plant]) {
				costDescription = String.format("$%-5.2f",baseSeedCost[plant]);
			}
			else if (difference<1){
				costDescription = String.format("$%-5.2f  Save %-3.0f%%",seedCost[plant][currentRound],(difference-1)*-100);
			}
			else {
				costDescription = String.format("$%-5.2f  Surcharge %-3.0f%%",seedCost[plant][currentRound],(difference-1)*100);
			}

			//Calculations for market price display message
			//A current weakness of this code is it only forecasts 1 round ahead
			//which doesn't help someone with plant maturities of >1 round.
			priceDescription="";
			for (int x=currentRound+1;x<maxRounds;x++) {
				difference=marketPrice[plant][x]/baseMarketPrice[plant];
				if (marketPrice[plant][x]==baseMarketPrice[plant]) {
					priceDescription = priceDescription + ".";
				}
				else if (difference<1){
					priceDescription = priceDescription + "-";
				}
				else {
					priceDescription = priceDescription + "+";
				}
			}
			//This shows the current cost & price list for each plant
			System.out.format("** %-10s  %-23s  %-8.2f   %-9d  $%-7.2f %-20s**\n" ,plants[plant],costDescription,squareFootage[plant],maturityRounds[plant],baseMarketPrice[plant],priceDescription);
		}
		System.out.format("**-----------------------------------------------------------------------------------------**\n");
		//System.out.format("** Round %d of %d   Player %-12s    Funds: $%5.2f     Available Space:  %3.1f         **\n", currentRound,maxRounds,farmList.get(currentFarm).playerName,farmList.get(currentFarm).playerCash,(float)totalSquareFeet-farmList.get(currentFarm).spaceUsed);
		System.out.format("** Carrots %3d   Tomatoes %3d   Potatoes %3d   Corn %3d   Watermelons %3d                  **\n",farmList.get(currentFarm).getCount("Carrot"),farmList.get(currentFarm).getCount("Tomato"),farmList.get(currentFarm).getCount("Potato"),farmList.get(currentFarm).getCount("Corn"),farmList.get(currentFarm).getCount("Watermelon"));
		System.out.format("**-----------------------------------------------------------------------------------------**\n");

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
				//System.out.format("Plant: %d, Base seed cost: %5.2f Round %d cost: %5.2f, Base market price: %5.2f Market Price: %5.2f\n" , plant,baseSeedCost[plant],round+1,seedCost[plant][round],baseMarketPrice[plant],marketPrice[plant][round]);
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

	/**
	 * A simple function to prompt and catch an enter key to manage the flow of user input.
	 */
	public static void promptEnterKey(){
	    System.out.format("Press ENTER to continue.");
	    try {
			@SuppressWarnings("resource")
			Scanner in = new Scanner (System.in);
			@SuppressWarnings("unused")
			String input=in.nextLine();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * A simple function to prompt and catch an enter key to manage the flow of user input.
	 * @param String fn - the path and file name of the text file to output to console, i.e. "img/tractor.txt"
	 */
	public static void textToConsole(String fn) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fn));
		String line = in.readLine();
		while(line != null)
		{
		  System.out.println(line);
		  line = in.readLine();
		}
		in.close();		
	}
}
