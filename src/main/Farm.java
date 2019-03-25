/**
 * 
 */
package main;

/**
 * @author Dan Crosby
 *
 */
public class Farm {
	int farmSize;
	double playerCash;
	String playerName;
	int currentRound;

	//ArrayList of type Plant
	
	//Constructor	
	Farm (String playerName,int farmSize)
	{
		this.currentRound=0;
		this.playerCash=1.00;
		this.farmSize=farmSize;
		this.playerName=playerName;
	}

	public void processRound ()
	{
		//Loop through each plant in the plants  array
		//Call the CheckPlantProgress method
		//Maintain Running total of money earned
	}

	protected void saveResults ()
	{
		//Determine the name of the file to save
		//Save player name, date, number of rounds, and cash raised
		//Save an inventory of the garden
		//Save ending cash
	}

}
