package main;
//TODO Add method javadoc prefixes like in Main

/**
 * @author Dan Crosby & Leanne Kendrick
 * Custom class for Watermelons which has an "Is-A" relationship to a Plant.
 */
public class Watermelon extends Plant {
	//plantIndex is a number from 0 to 4 that identifies the plant.
	final private static int plantIndex=4;
	//plantName is a String identifies the plant.
	final private static String plantName="Watermelon";

	/**
	 * Create an instance of the Watermelon class 
	 * @param int quantity - The number of watermelons
	 */
	Watermelon(int quantity) {
		//Any method which calls this constructor must trap any exceptions that result
		super(plantIndex,quantity);
		//Recording the round when these plant(s) were purchased for posterity sake.
		super.purchaseRound=Main.currentRound;
		//Recording when these plants will be available for harvest.
		super.maturityRound=Main.currentRound+Main.maturityRounds[plantIndex]-1;
		//Recording until which round these plants will continue to yield fruit
		super.maxHarvestRound=maturityRound+Main.harvestRounds[plantIndex]-1;
		System.out.format("Thank you for purchasing %d watermelon seed(s), which are now germinating (1/5).\n", quantity);
	}
	
	/**
	 * Return the type of the class, which overrides the Plant class 
	 * @return String - the name of the plant
	 */
	@Override
	String getType() {
		return plantName;
	}

	/**
	 * Return the type of the class, which overrides the Plant class 
	 * @return int - the ID of the plant
	 */
	@Override
	int getIndex() {
		return plantIndex;
	}

	/**
	 * At the end of each round, this method will be called on each carrot purchas
	 * If a harvest occurs, increase cash
	 * If a disaster occurs, decrease plant quantity
	 * If maxHarvestRounds exceeded, decrease plant quantity to zero
	 */
	@Override
	public void checkPlantProgress()
	{
		//System.out.format("Debug %s purchased round %d maturity round %d max harvest round %d\n",plantName,super.purchaseRound,super.maturityRound,super.maxHarvestRound);
		if (super.plantQuantity<1) return;
		if (super.maxHarvestRound<Main.currentRound) {
			int plantsDestroyed=super.plantQuantity;
			System.out.format("Your %d %s plant(s) from round %d are no longer productive and are being retired.", plantsDestroyed, plantName, super.purchaseRound);
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
			super.plantQuantity=0;
		}

		// Check to see if the watermelon patch was hit by a tornado
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		//A 8% chance of a tornado in any given round
		if ((Math.random()*100)>85) {
			//If a tornado occur, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/tornado.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Your watermelon patch has been hit by a tornado, and %d of %d plants were destroyed!\n", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
		}
		
		if (Main.currentRound>=super.maturityRound && Main.currentRound<=super.maxHarvestRound) {
			//System.out.format("%d watermelon plant(s) are ready for harvest! (5/5).", super.plantQuantity);
			super.checkPlantProgress();
			return;
		}
		if (super.maturityRound==Main.currentRound+1) {
			System.out.format("%d watermelon plant(s) are now growing fruit (4/5).\n", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+2) {
			System.out.format("%d watermelon plant(s) are now blossoming (3/5).\n", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+3) {
			System.out.format("%d watermelon plant(s) are now vining (2/5).\n", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+4) {
			System.out.format("%d watermelon plant(s) are now germinating (1/5).\n", super.plantQuantity);
		}
	}
}
