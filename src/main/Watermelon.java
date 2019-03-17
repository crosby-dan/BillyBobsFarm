package main;

public class Watermelon extends Plant {

	@Override
	public void checkPlantProgress()
	{
		//Update current marketPrice of watermelon
		// Check to see if the watermelon patch was hit by a tornado
		//If yes, reduce the plantQuantity for any plants killed as appropriate
		//If yes, reduce the moneyEarned as appropriate
		//output to console the fact that the Tornado happened
		//if not to both of the above, call super(checkPlantProgress) to see if any of the other event types happened
	}

	@Override
	public void buyPlant()
	{
		//Call the super (buyPlant) function (which will err if the square footage will be exceeded)
		//Increase plant purchased quantity
		//The calling function must subtract availableCash by the returned cost (which will be zero if the purchase failed)
		//Set the maturity round to current round + 3 (watermelon)
		//output purchase confirmation to console
	}

}
