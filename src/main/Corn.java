package main;

/**
 * @author Dan Crosby & Leanne Kendrick
 * Custom class for Carrots which has an "Is-A" relationship to a Plant.
 */
public class Corn extends Plant {
	final private static int plantIndex=3;
	final private static String plantName="Corn";

	//constructor 
	Corn(int quantity) {
			//Any method which calls this constructor must trap any exceptions that result
			super(plantIndex,quantity);
			//Recording the round when these plant(s) were purchased for posterity sake.
			super.purchaseRound=Main.currentRound;
			//Recording when these plants will be available for harvest.
			super.maturityRound=Main.currentRound+Main.maturityRounds[plantIndex]-1;
			//Recording until which round these plants will continue to yield fruit
			super.maxHarvestRound=maturityRound+Main.harvestRounds[plantIndex]-1;
			System.out.format("Thank you for purchasing %d Corn seed(s), which are now growing (1/3).\n", quantity);
			}
	@Override
	String getType() {
		return plantName;
	}

	@Override
	int getIndex() {
		return plantIndex;
	}

	@Override
	public void checkPlantProgress()
	{
		//System.out.format("Debug %s purchased round %d maturity round %d max harvest round %d\n",plantName,super.purchaseRound,super.maturityRound,super.maxHarvestRound);
		if (super.plantQuantity<1) return;
		if (super.maxHarvestRound<Main.currentRound) {
			int plantsDestroyed=super.plantQuantity;
			System.out.format("Your %d %s plant(s) from round %d are no longer productive and are being retired.\n", plantsDestroyed, plantName, super.purchaseRound);
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
			super.plantQuantity=0;
		}

		// Check to see if the corn patch was hit by high temperatures
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		if ((Math.random()*100)>85 && super.plantQuantity>0) {
			//If a tornado occurs, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/popcorn.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("It is 128 degrees outside and your corn is popping on the vine!  %d of %d plants were destroyed!\n", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
		}

		if (Main.currentRound>=super.maturityRound && Main.currentRound<=super.maxHarvestRound && super.plantQuantity>0) {
			//System.out.format("%d corn plant(s) are ready for harvest! (3/3).\n", super.plantQuantity);
			super.checkPlantProgress();
			return;
		}
		if (super.maturityRound==Main.currentRound+2 && super.plantQuantity>0) {
			System.out.format("%d corn is growing about yee haw high. (1/3).\n", super.plantQuantity);
		}
		if (super.maturityRound==Main.currentRound+1 && super.plantQuantity>0) {
			System.out.format("%d baby corn cobs are now visible. (2/3).\n", super.plantQuantity);
		}
	
	}
}
