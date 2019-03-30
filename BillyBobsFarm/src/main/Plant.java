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
	
	// TODO Leanne Implement Carrot class based on Watermelon
	// TODO Leanne Implement Corn class based on Watermelon
	// TODO Leanne Implement Tomato class based on Watermelon
	// TODO Leanne Implement Potato class based on Watermelon

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
			
			// TODO Leanne - Check to see if there is a late frost (or maybe we implement this as "great weather" that can boost productivity.

			//Calculate money earned
			float cashEarned = (float) (plantQuantity * Main.marketPrice[getIndex()][Main.currentRound] * ((100-productionImpact)/100));
			Main.farmList.get(Main.currentFarm).changeCash(cashEarned);
			//If plant is available for a single harvest only, then reduce the square footage and plantQuantity

			// TODO Leanne - Add code to display treasure chest (See try catch block above for tractor.txt)
			System.out.format("Congratulations, you have earned %5.2f from your round %d %s plant(s).\n", cashEarned, purchaseRound, getType());
		}
	}
	abstract int getIndex();
	abstract String getType();
}
