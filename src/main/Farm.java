/**
 * 
 */
package main;

import java.util.ArrayList;

/**
 * @author Dan Crosby
 * Each player will have their own farm.
 * Farms can have plants
 * Farms have their own bank account total (playerCash)
 */
public class Farm {
	String playerName;
	int farmSize;
	float spaceUsed=0;
	double playerCash;

	//ArrayList of type Plant
	protected ArrayList<Plant> plantList = new ArrayList<>();

	//Constructor	
	Farm (String playerName,int farmSize, double playerCash)
	{
		this.playerCash=playerCash;
		this.farmSize=farmSize;
		this.playerName=playerName;
	}

	public void processRound ()
	{
		//Loop through each plant in the plants arrayList
		for (Plant pl: plantList) {
			pl.checkPlantProgress();
		}
				
		Main.promptEnterKey();
	}

	protected void saveResults ()
	{
		//Determine the name of the file to save
		//Save player name, date, number of rounds, and cash raised
		//Save an inventory of the garden
		//Save ending cash
	}
	
	public int getCount(String plant) {
		int qty=0;
		for (Plant pl: plantList) {
		   if (pl.getType()==plant) qty+=pl.plantQuantity;
		}
		return qty;
	}

	public void addPlant(int plant, int qty) {
		switch (plant) {
		case 0: 
			//Leanne - replace these "not implemented" lines with calls to add the appropriate vegetable (like it is on case 4:).
			System.out.format("Plant %s not implemented.",Main.plants[plant]);
			break;
		case 1: 
			System.out.format("Plant %s not implemented.",Main.plants[plant]);
			break;
		case 2: 
			System.out.format("Plant %s not implemented.",Main.plants[plant]);
			break;
		case 3: 
			System.out.format("Plant %s not implemented.",Main.plants[plant]);
			break;
		case 4: 
			this.plantList.add(new Watermelon(qty));
			//System.out.format("Added new watermelon.  New list count=%d.\n",plantList.size());
			break;
		default:
			System.out.format("Error: Plant ID %d not implemented.",plant);
			break;
		}
	}
}
