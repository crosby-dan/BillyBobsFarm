package main;

/**
 * @author Leanne Kendrick
 * Custom exception for shortage of money to add plants
 */
@SuppressWarnings("serial")
public class NotEnoughLandException extends RuntimeException {
	public NotEnoughLandException() {
		super("Not enough land to purchase this plant.");
	}
}

