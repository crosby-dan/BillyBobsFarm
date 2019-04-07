/**
 * 
 */
package main;

/**
 * @author Leanne Kendrick
 * Custom exception for shortage of available space to add plants
 */

@SuppressWarnings("serial")
public class NotEnoughMoneyException extends RuntimeException {
	public NotEnoughMoneyException() {
		super("Not enough money to purchase this plant.");
	}
}
