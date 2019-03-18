package main;

public class Plant {
	protected String name;
	protected int maturityRound;
	protected int plantQuantity;
	protected double plantSize;
	protected double marketPrice;
	protected double moneyEarned;

	public void checkPlantProgress()
	{
		//Check to see if the tractor broke
		//Check to see if there is a late frost.
		//If yes to either of the above, reduce the plantQuantity for any plants killed as appropriate
		//If yes to either of the above, reduce the moneyEarned as appropriate
		//if yes to either of the above, output to console the fact that the Tornado happened

	}

	public void buyPlant()
	{
		// This method will check to make sure there is enough square footage available for this plant.
		// Out of money
		// If not, output failure to console.
	}
}
