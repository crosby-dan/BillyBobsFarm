package main;
//TODO Add method javadoc prefixes like in Main

/**
 * @author Dan Crosby & Leanne Thornton
 * Class for Plant objects, which are extended by the different plant types.
 * This class should not be instantiated directly, only through the appropriate child class.
 * An ArrayList of type ArrayList<Plant> is contained in the Farm class.
 */
public abstract class Plant {
	protected int plantQuantity;
	protected double spaceUsed;
	protected double purchaseCost;
	protected int maturityRound;
	protected int maxHarvestRound;
	protected int purchaseRound;

	//Constructor for the plant class
	//Which will tally the costs & space required and throw an error if necessary.
	Plant (int plant,int quantity) {
		this.plantQuantity=quantity;
		purchaseCost=Main.seedCost[plant][Main.currentRound]*quantity;
		spaceUsed=Main.squareFootage[plant]*quantity;
		//System.out.format("Current farm is %d of %d\n", Main.currentFarm, Main.farmList.size());
		//System.out.format("Player cash is %5.2f\n", Main.farmList.get(Main.currentFarm).playerCash);
		if (purchaseCost>Main.farmList.get(Main.currentFarm).getPlayerCash()) {
			System.out.format("Insufficient cash, purchase failed.\n",Main.farmList.get(Main.currentFarm).getPlayerCash());
			throw new RuntimeException();
		}
		if (spaceUsed>Main.farmList.get(Main.currentFarm).getSpaceAvailable()) {
			System.out.format("Insufficient land space available, purchase failed.\n",Main.farmList.get(Main.currentFarm).getSpaceAvailable());
			throw new RuntimeException();
		}

		//All checks passed, go ahead and complete purchase.
		Main.farmList.get(Main.currentFarm).changeCash(purchaseCost*-1);
		Main.farmList.get(Main.currentFarm).changeSpace(spaceUsed);
		//System.out.format("Purchased %d of %s seed(s) for %5.2f.  Player cash is %5.2f, available space is %5.1f.\n",quantity,getType(),plantCost,Main.farmList.get(Main.currentFarm).playerCash,Main.totalSquareFeet-Main.farmList.get(Main.currentFarm).spaceUsed);
	}
	
	public void checkPlantProgress()
	{
		int productionImpact=0;
		//Check to see if the tractor broke- probability 30%, impact from 1 to 40% of production reduced
		if (Main.currentRound>=maturityRound && Main.currentRound<=maxHarvestRound) {
			System.out.format("%d %s plant(s) are ready for harvest!\n", plantQuantity, getType());
			
			// Check for any tractor issues
			if ((Math.random()*100)>70) {
				//If a tractor issue occurs, reduce round revenues by up to 40%.
				productionImpact=(int)(40*Math.random())+1;
				//check for a portion of plants destroyed
				try {
					Main.textToConsole("img/tractor.txt");
				}
				catch (Exception ex) {
					//not important if this fails so not doing anything
				}
				System.out.format("Billy Bob's tractor broke and reduced your revenues by %d%%.\n", productionImpact);
			}
			//System.out.format("DEBUG: Plant Quantity %d ((100.0-productionImpact)/100)=%d\n" ,plantQuantity, ((100-productionImpact)/100));
			//System.out.format("DEBUG: Index %d Round %d\n" ,getIndex(),Main.currentRound );
			//System.out.format("DEBUG: Market Price %5.2f\n" ,Main.marketPrice[getIndex()][Main.currentRound-1] );
			//Calculate money earned
			float cashEarned = (float) (plantQuantity * Main.marketPrice[getIndex()][Main.currentRound-1] * ((100.0-productionImpact)/100.0));
			Main.farmList.get(Main.currentFarm).changeCash(cashEarned);
			//If plant is available for a single harvest only, then reduce the square footage and plantQuantity

			// TODO Leanne - Add code to display treasure chest (See try catch block above for tractor.txt)
			System.out.format("Congratulations, you have earned %5.2f from your %d %s plant(s) planted in round %d.\n", cashEarned, plantQuantity, getType(), purchaseRound);
		}
	}
	abstract int getIndex();
	abstract String getType();
}
