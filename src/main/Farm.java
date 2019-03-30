/**
 * 
 */
package main;

import java.util.ArrayList;
//TODO Add method javadoc prefixes like in Main

/**
 * @author Dan Crosby & Leanne Kendrick
 * Each player will have their own farm.  (The correct farm can be selected using the Main.currentFarm variable)
 * The main class will have an ArrayList of type ArrayList<Farm>
 * To access the appropriate Farm use farmList.get(Main.currentFarm)
 * Farms can contain plants via ArrayList plantList
 * Farms have their own fields for playerCash, playerName, farmSize, and spaceUsed.
 */
public class Farm {
	private String playerName;
	private int farmSize;
	private float spaceUsed=0;
	private double playerCash;

	//ArrayList of type Plant
	protected ArrayList<Plant> plantList = new ArrayList<>();

	//Constructor	
	Farm (String playerName,int farmSize, double playerCash)
	{
		this.playerCash=playerCash;
		this.farmSize=farmSize;
		this.playerName=playerName;
	}

	public String getPlayerName() {
		return this.playerName;
	}
	
	public double getPlayerCash() {
		return this.playerCash;
	}
	
	public double getSpaceAvailable() {
		return this.farmSize-spaceUsed;
	}
	
	public double getFarmSize() {
		return this.farmSize;
	}
	
	protected void changeCash(double amount) {
		// TODO Throw exception if cash will be less than zero
		
		this.playerCash+=amount;
	}
	
	protected void changeSpace(double space) {
		// TODO Throw exception if space will be more than maximum or less than zero
		
		this.spaceUsed+=space;
	}
	
	public void processRound ()
	{
		//Loop through each plant in the plants arrayList
		for (Plant pl: plantList) {
			pl.checkPlantProgress();
		}
				
		Main.promptEnterKey();
	}

	public void saveResults ()
	{
		//TODO Create a process to save the results to a file.
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

	protected void addPlant(int plant, int qty) {
		switch (plant) {
		
		case 0: 
			this.plantList.add(new Carrot (qty));
		    break;
			
		case 1: 
			this.plantList.add(new Tomato (qty));
			break;
			
		case 2: 
			this.plantList.add(new Potato (qty));
			break;
			
		case 3: 
			this.plantList.add(new Corn (qty));
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
