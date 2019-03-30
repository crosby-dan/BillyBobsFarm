package main;

/**
 * @author Dan Crosby & Leanne Kendrick
 * Custom class for Carrots which has an "Is-A" relationship to a Plant.
 */
public class Corn extends Plant {
	final private int plantIndex=3;
	final private String plantName="Corn";

	//constructor 
	Corn(int quantity) {
			//Any method which calls this constructor must trap any exceptions that result
			super(0,quantity);
			//Recording when these plants will be available for harvest.
			super.maturityRound=Main.currentRound+1;
			//Recording the round when these plant(s) were purchased for posterity sake.
			super.purchaseRound=Main.currentRound;
			//Plants will yield harvest until this date.
			super.maxHarvestRound=maturityRound+Main.harvestRounds[plantIndex];
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
		System.out.format("Max Harvest Round=%d\n",super.maxHarvestRound);
		if (super.plantQuantity<1) return;
		if (super.maxHarvestRound<Main.currentRound) {
			int plantsDestroyed=super.plantQuantity;
			System.out.format("Your %d %s plant(s) from round %d are no longer productive and are being retired.", plantsDestroyed, plantName, super.purchaseRound);
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
			super.plantQuantity=0;
		}

		// Check to see if the corn patch was hit by a tornado
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		//A chance of a tornado in any given round
		if ((Math.random()*100)>55) {
			//If a tornado occurs, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/tornado.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Your corn patch has been hit by a tornado, and %d of %d plants were destroyed!", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).changeSpace(-1*plantsDestroyed*Main.squareFootage[plantIndex]);
		}
	}
}
