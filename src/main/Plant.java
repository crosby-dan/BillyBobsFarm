package main;

public abstract class Plant {
	protected int plantQuantity;
	protected double plantSize;
	protected double plantCost;
	protected int maturityRound;
	protected int maxHarvestRound;

	//Constructor for the plant class
	//Which will tally the costs & space required and throw an error if necessary.
	Plant (int plant,int quantity) {
		this.plantQuantity=quantity;
		plantCost=Main.seedCost[plant][Main.currentRound]*quantity;
		plantSize=Main.squareFootage[plant]*quantity;
		//System.out.format("Current farm is %d of %d\n", Main.currentFarm, Main.farmList.size());
		//System.out.format("Player cash is %5.2f\n", Main.farmList.get(Main.currentFarm).playerCash);
		if (plantCost>Main.farmList.get(Main.currentFarm).playerCash) {
			System.out.format("Insufficient cash, purchase failed.\n",Main.farmList.get(Main.currentFarm).playerCash);
			throw new RuntimeException();
		}
		if (plantSize+Main.farmList.get(Main.currentFarm).spaceUsed>Main.farmList.get(Main.currentFarm).farmSize) {
			System.out.format("Insufficient land space available, purchase failed.\n",Main.farmList.get(Main.currentFarm).spaceUsed);
			throw new RuntimeException();
		}

		//All checks passed, go ahead and complete purchase.
		//System.out.format("Updating cash and space used for farm %d\n", Main.currentFarm);
		Main.farmList.get(Main.currentFarm).playerCash-=plantCost;
		Main.farmList.get(Main.currentFarm).spaceUsed+=plantSize;
		//System.out.format("Purchased %d of %s seed(s) for %5.2f.  Player cash is %5.2f, available space is %5.1f.\n",quantity,getType(),plantCost,Main.farmList.get(Main.currentFarm).playerCash,Main.totalSquareFeet-Main.farmList.get(Main.currentFarm).spaceUsed);
	}
	
	public void checkPlantProgress()
	{
		int productionImpact=0;
		//Check to see if the tractor broke- probability 30%, impact from 1 to 40% of production reduced
		if (maturityRound==Main.currentRound) {
			System.out.format("%d %s plant(s) are ready for harvest!", plantQuantity, getType());
			
			// Check for any tractor issues
			if ((Math.random()*100)>70) {
				//If a tractor issue occurs, reduce round revenues by up to 30%.
				productionImpact=(int)(40*Math.random())+1;
				//check for a portion of plants destroyed
				try {
					Main.textToConsole("img/tractor.txt");
				}
				catch (Exception ex) {
					//not important if this fails so not doing anything
				}
				System.out.format("Billy Bob's tractor broke and reduced your revenues by %d%%.", productionImpact);
			}
			
			//Leanne - Check to see if there is a late frost (or maybe we implement this as "great weather" that can boost productivity.

			//Calculate money earned
			float cashEarned = (float) (plantQuantity * Main.marketPrice[getIndex()][Main.currentRound] * ((100-productionImpact)/100));
			Main.farmList.get(Main.currentFarm).playerCash+=cashEarned;
			//If plant is available for a single harvest only, then reduce the square footage and plantQuantity
			

		}
	}
	abstract int getIndex();
	abstract String getType();
}
