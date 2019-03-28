package main;

public class Watermelon extends Plant {
	final private int plantIndex=4;
	final private String plantName="Watermelon";


	//constructor 
	Watermelon(int quantity) {
		//Any method which calls this constructor must trap any exceptions that result
		super(4,quantity);
		super.maturityRound=Main.currentRound+4;
		System.out.format("Thank you for purchasing %d watermelon seed(s), which are now germinating (1/5).\n", quantity);
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
		if (super.plantQuantity<1) return;
		//An 8% chance of a tornado in any given round
		if ((Math.random()*100)>80) {
			//If a tornado occur, there will be an evenly weighted chance for the number of plants destroyed.
			int plantsDestroyed=(int)(super.plantQuantity*Math.random())+1;
			//check for a portion of plants destroyed
			try {
				Main.textToConsole("img/tornado.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Your watermelon patch has been hit by a tornado, and %d of %d plants were destroyed!", plantsDestroyed,super.plantQuantity);
			super.plantQuantity-=plantsDestroyed;
			//Decrease the amount of space used based on plants destroyed
			Main.farmList.get(Main.currentFarm).spaceUsed-=plantsDestroyed*Main.squareFootage[4];
		}
		
		// Check to see if the watermelon patch was hit by a tornado
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		if (super.maturityRound==Main.currentRound) {
			//System.out.format("%d watermelon plant(s) are ready for harvest! (5/5).", super.plantQuantity);
			super.checkPlantProgress();
			return;
		}
		if (super.maturityRound==Main.currentRound+1) {
			System.out.format("%d watermelon plant(s) are now fruiting (4/5).", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+2) {
			System.out.format("%d watermelon plant(s) are now flowering (3/5).", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+3) {
			System.out.format("%d watermelon plant(s) are now vining (2/5).", super.plantQuantity);
		}
		else if (super.maturityRound==Main.currentRound+4) {
			System.out.format("%d watermelon plant(s) are now germinating (1/5).", super.plantQuantity);
		}
	}
}
