/**
 * 
 */
package main;

/**
 * @author Dan Crosby
 *
 */
@SuppressWarnings("serial")
public class NotEnoughMoneyException extends RuntimeException {
	public NotEnoughMoneyException() {
		super("Not enough money to purchase this plant.");
	}
}
