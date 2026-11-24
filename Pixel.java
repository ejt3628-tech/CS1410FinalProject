package assign10;

/**
 * 
 * @author Leonorah
 * @version 11/5/2026
 * 
 * 
 * this class contains the instance variables for the primary colors
 */
public class Pixel {
	private int red;
	private int green;
	private int blue;
	
	/**
	 * this method creates a pixel with the color value parameters
	 * @param red - int value for red
	 * @param green - int value for green
	 * @param blue - int value for blue
	 */
	public Pixel(int red, int green, int blue) {
		if (red < 0 || red > 255) {
			throw new IllegalArgumentException("The value for red is out of bounds");
		}
		if (green < 0 || green > 255) {
			throw new IllegalArgumentException("The value for green is out of bounds");
		}
		if (blue < 0 || blue > 255) {
			throw new IllegalArgumentException("The value for blue is out of bounds");
		}
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	/**
	 * this method returns the int value for red
	 * 
	 * @return red - int value for red
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * this method returns the int value for green
	 * 
	 * @return green - int value for green
	 */
	public int getGreen() {
		return green;
	}
	
	/**
	 * this method returns the int value for blue
	 * 
	 * @return blue - int value for blue
	 */
	public int getBlue() {
		return blue;
	}
	
	/**
	 * this method returns the red, green, and blue amount of a pixel object packed into a four-byte int value
	 * @return - int value of the colors
	 */
	public int packRGB() {
		int redValue = red << 16;
		int greenValue = green << 8;
		int blueValue = blue << 0;
		
		return redValue + greenValue + blueValue;
	}
	
	/**
	 * this method checks to see if the color values of one pixel are equal to the color values of another pixel
	 * 
	 * @return - boolean value of another pixel
	 */
	public boolean equals(Object other) {
		if (!(other instanceof Pixel))
			return false;
		Pixel otherPixel = (Pixel) other;
		return (this.red == otherPixel.red && this.green == otherPixel.green && this.blue == otherPixel.blue);
	}

}


