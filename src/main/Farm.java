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
	//Name of the player (each Farm has-a player)
	private String playerName;
	//Size of the farm
	private int farmSize;
	//Current space used on the farm.  This value is checked before new plants can be purchased.
	private float spaceUsed=0;
	//The current amount of cash that the player has for use on this farm.
	private double playerCash;

	//ArrayList of type Plant for storing all plants that have been purchased for this farm.
	//This is will be used with Polymorphism since the actual objects will be on custom plant types that extend Plant.
	protected ArrayList<Plant> plantList = new ArrayList<>();

	/**
	 * Create an instance of the Farm class 
	 * @param String playerName - name of the player
	 * @param int farmSize - how much space their will be
	 * @param double playerCash - how much money the player will have
	 */
	Farm (String playerName,int farmSize, double playerCash)
	{
		this.playerCash=playerCash;
		this.farmSize=farmSize;
		this.playerName=playerName;
	}

	/**
	 * Getter to return the name of the player 
	 * @return String - name of the player
	 */
	public String getPlayerName() {
		return this.playerName;
	}
	
	/**
	 * Getter to return the amount of cash the player has 
	 * @return double - current cash amount
	 */
	public double getPlayerCash() {
		return this.playerCash;
	}
	
	/**
	 * Getter to return the amount of farm space available
	 * @return double - current space available
	 */
	public double getSpaceAvailable() {
		return this.farmSize-spaceUsed;
	}
	
	/**
	 * Getter to return the total farm size
	 * @return double - current farm size
	 */
	public double getFarmSize() {
		return this.farmSize;
	}
	
	/**
	 * Setter to change the amount of player cash
	 * @param double - change current player cash amount
	 */
	protected void changeCash(double amount) {
		// TODO Throw exception if cash will be less than zero
		
		this.playerCash+=amount;
	}
	
	/**
	 * Setter to change the amount of available space
	 * @param double - change current farm available space
	 */
	protected void changeSpace(double space) {
		// TODO Throw exception if space will be more than maximum or less than zero
		
		this.spaceUsed+=space;
	}
	
	/**
	 * At the end of each round, call this method 
	 * Method will loop through each plant use the checkPlantProgress, which is polymorphic
	 */
	public void processRound ()
	{
		//Loop through each plant in the plants arrayList
		for (Plant pl: plantList) {
			pl.checkPlantProgress();
		}
				
		Main.promptEnterKey();
	}

	/**
	 * Getter for counting the number of plants of a certain name.
	 * @param String - the name of the plant to check
	 * @return int - the count of plants
	 */
	public int getCount(String plant) {
		//Temporary variable for calculating the quantity of plants with a certain name
		int qty=0;
		for (Plant pl: plantList) {
		   if (pl.getType()==plant) qty+=pl.plantQuantity;
		}
		return qty;
	}

	/**
	 * Method to create custom plant objects for use in polymorphism
	 * @param int - the index of the plant to add
	 * @param int - the count of plants
	 */
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
