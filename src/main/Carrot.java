package main;

/**
 * @author Dan Crosby & Leanne Kendrick
 * Custom class for Carrots which has an "Is-A" relationship to a Plant.
 */
public class Carrot extends Plant {
	//plantIndex is a number from 0 to 4 that identifies the plant.
	final private static int plantIndex=0;
	//plantName is a String identifies the plant.
	final private static String plantName="Carrot";

	/**
	 * Create an instance of the Carrot class 
	 * @param int quantity - The number of carrots
	 */ 
	Carrot(int quantity) {
			//Any method which calls this constructor must trap any exceptions that result
			super(plantIndex,quantity);
			//Recording the round when these plant(s) were purchased for posterity sake.
			super.purchaseRound=Main.currentRound;
			//Recording when these plants will be available for harvest.
			super.maturityRound=Main.currentRound+Main.maturityRounds[plantIndex]-1;
			//Recording until which round these plants will continue to yield fruit
			super.maxHarvestRound=maturityRound+Main.harvestRounds[plantIndex]-1;
			System.out.format("Thank you for purchasing %d Carrot seed(s), which are now growing (1/1).\n", quantity);
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
			
			//Carrots are always only for 1 round so no point announcing this.
			System.out.format("Your %d %s plant(s) from round %d are no longer productive and are being retired.\n", plantsDestroyed, plantName, super.purchaseRound);
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
			super.plantQuantity=0;
		}

		// Check to see if the carrot patch was invaded by rabbits
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		if ((Math.random()*100)>85 && super.plantQuantity>0) {
			//If a rabbits occur, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/rabbiteatscarrot.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Your carrot patch has been invaded by Rabbits, and %d of %d plants were destroyed!\n", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
		}
		else
		// Instead, check to see if carrots are soggy due to excessive rain.
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		if ((Math.random()*100)>85 && super.plantQuantity>0) {
			//If a rabbits occur, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/soggycarrot.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Monsoon season! Your carrots have become soggy, and %d of %d plants were destroyed!\n", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
		}

		if (Main.currentRound>=super.maturityRound && Main.currentRound<=super.maxHarvestRound && super.plantQuantity>0) {
			//System.out.format("%d carrot plant(s) are ready for harvest! (1/1).\n", super.plantQuantity);
			super.checkPlantProgress();
			return;
		}

	}
}

	
	


