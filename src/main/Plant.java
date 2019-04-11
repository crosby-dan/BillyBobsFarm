package main;
/**
 * @author Dan Crosby & Leanne Thornton
 * Class for Plant objects, which are extended by the different plant types.
 * This class should not be instantiated directly, only through the appropriate child class.
 * An ArrayList of type ArrayList<Plant> is contained in the Farm class.
 */
public abstract class Plant {
	//The number of plants purchased, which is independent of type of plant
	protected int plantQuantity;
	//The amount of space used, which is updated up on the purchase of a specific plant
	protected double spaceUsed;

	//The round number when this plant was purchased, which is updated up on the purchase of a specific plant
	protected int purchaseRound;
	//The amount of money that was spent to purchase the plant, which is updated up on the purchase of a specific plant
	protected double purchaseCost;
	//The round number when this plant will begin harvesting, which is updated up on the purchase of a specific plant
	protected int maturityRound;
	//The round number when this plant will end harvesting, which is updated up on the purchase of a specific plant
	protected int maxHarvestRound;

	/**
	 * The Plant method (constructor)
	 * Purpose:  Constructor to create an instance of the Plant class 
	 * @param int plant - types of plant
	 * @param int quantity - how many plants
	 */
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
	
	/**
	 * The checkPlantProgress method
	 * Purpose: To check the progress of a plant at the end of each round.
	 */
	public void checkPlantProgress()
	{
		//productionImpact is a temporary variable calculated via random number generator if plants are to be destroyed
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
			try {
				Main.textToConsole("img/treasure.txt");
			}
			catch (Exception ex) {
				//not important if this fails so not doing anything
			}
			System.out.format("Congratulations, you have earned %5.2f from your %d %s plant(s) planted in round %d.\n", cashEarned, plantQuantity, getType(), purchaseRound);
		}
	}
	
	/**
	 * The getIndex abstract method
	 * Purpose: All methods that extend Plant will have to implement a getIndex
	 * @return The integer  index of the plant
	 */
	abstract int getIndex();

	/**
	 * The getType abstract method
	 * Purpose: All methods that extend Plant will have to implement a getType
	 * @return The String name of the plant
	 */
	abstract String getType();
}
