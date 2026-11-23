package assign07;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents an image as a two-dimensional array of pixels and provides a number 
 * of image filters (via instance methods) for producing a new Image with an altered appearance.
 * 
 * Note:
 *   - The pixel in the northwest corner of the image is stored in the first row, first column.
 *   - The pixel in the northeast corner of the image is stored in the first row, last column.
 *   - The pixel in the southeast corner of the image is stored in the last row, last column.
 *   - The pixel in the southwest corner of the image is stored in the last row, first column.
 *   
 * DO NOT MODIFY ANY PROVIDED CODE, unless directed in the assignment instructions.
 * 
 * @author Prof. Parker and ?
 * @version ?
 */
public class Image {
	private Pixel[][] pixels;
	
	/**
	 * Creates a new Image object from the given two-dimensional array of pixels.
	 * 
	 * @param pixels - two-dimensional array of pixels, with cannot be jagged
	 */
	public Image(Pixel[][] pixels) {		
		this.pixels = new Pixel[pixels.length][pixels[0].length];
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i].length != pixels[0].length)
				throw new IllegalArgumentException("Given two-dimensional array of pixels must have " +
						"the same number of columns for each row.");
			this.pixels[i] = new Pixel[pixels[0].length];
			for(int j = 0; j < pixels[0].length; j++)
				this.pixels[i][j] = pixels[i][j];
		}
	}
	
	/**
	 * Creates a new Image object by reading the image file with the given filename.
	 * 
	 * @param filename - name of the image file to read
	 * @throws IOException if file does not exist or cannot be read
	 */
	public Image(String filename) {
		BufferedImage imageInput = null;
		try {
			imageInput = ImageIO.read(new File(filename));
		}
		catch(IOException e) {
			System.out.println("Image file " + filename + " does not exist or cannot be read.");
		}
		
		this.pixels = new Pixel[imageInput.getHeight()][imageInput.getWidth()];
		for(int i = 0; i < this.pixels.length; i++)
			for(int j = 0; j < this.pixels[0].length; j++) {
				int rgb = imageInput.getRGB(j, i);
				this.pixels[i][j] = new Pixel((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
			}		
	}
	
	/**
	 * Creates a new "default" Image object, whose purpose is to be used in testing.
	 * 
	 * The orientation of this image:
	 * 		cyan 	 red
	 *		green	 magenta
	 *		yellow	 blue
	 */
	public Image() {		
		this.pixels = new Pixel[3][2];
		this.pixels[0][0] = new Pixel(0, 255, 255);  // cyan
		this.pixels[0][1] = new Pixel(255, 0, 0);  // red
		this.pixels[1][0] = new Pixel(0, 255, 0);  // green
		this.pixels[1][1] = new Pixel(255, 0, 255);  // magenta
		this.pixels[2][0] = new Pixel(255, 255, 0);  // yellow
		this.pixels[2][1] = new Pixel(0, 0, 255);  // blue
	}
	
	/**
	 * Gets the pixel at the specified row and column indexes.
	 * 
	 * @param rowIndex - row index
	 * @param columnIndex - column index
	 * @return pixel at the given row and column indexes
	 * @throws IndexOutOfBoundsException if row or column index is out of bounds
	 */
	public Pixel getPixel(int rowIndex, int columnIndex) {
		if(rowIndex < 0 || rowIndex >= this.pixels.length)
			throw new IndexOutOfBoundsException("The row index must be in range 0-" + (this.pixels.length - 1) + ".");

		if(columnIndex < 0 || columnIndex >= this.pixels[0].length)
			throw new IndexOutOfBoundsException("The column index must be in range 0-" + (this.pixels[0].length - 1) + ".");

		return this.pixels[rowIndex][columnIndex];
	}
	
	/**
	 * Writes the image represented by this object to file.
	 * Does nothing if the image length is 0.
	 * 
	 * @param filename - name of image file to write
	 * @throws IOException if file cannot be written
	 */
	public void writeImage(String filename) {
		if(this.pixels.length > 0) {
			BufferedImage imageOutput = new BufferedImage(this.pixels[0].length, 
					this.pixels.length, BufferedImage.TYPE_INT_RGB);
		
			for(int i = 0; i < this.pixels.length; i++)
				for(int j = 0; j < this.pixels[0].length; j++) 
					imageOutput.setRGB(j, i, this.pixels[i][j].packRGB());
			
			try {
				ImageIO.write(imageOutput, "png", new File(filename));
			}
			catch(IOException e) {
				System.out.println("The image cannot be written to file " + filename + ".");
			}
		}
	}
	
	/**
	 * Generates a new image that is a red-green swapped version of this Image object
	 * (i.e., each pixel's color has the red and green amounts of this Image's pixel swapped).
	 * 
	 * @return new image that is a red-green swapped version of this image
	 */
	public Image swapRedGreen() {
		Pixel[][] swappedPixel = new Pixel[pixels.length][pixels[0].length];
		for ( int i = 0; i < pixels.length; i ++) {
			for (int j = 0; j < pixels[0].length; j++) {
			Pixel originalPixel = pixels[i][j];	
			Pixel newPixel = new Pixel(originalPixel.getGreen(), originalPixel.getRed(), originalPixel.getBlue());
			swappedPixel[i][j] = newPixel;
			}
		}
		Image swappedImage = new Image(swappedPixel);
		return swappedImage;
	}
	
	/**
	 * Generates a new image that is a flipped version of this Image object
	 * (i.e., mirror image around x-axis).
	 * 
	 * @return new image that is a flipped version of this image
	 */
	public Image flip() {
		Pixel[][] flippedPixel = new Pixel[pixels.length][pixels[0].length];
		for ( int i = 0; i < pixels.length; i ++) {
			for (int j = 0; j < pixels[0].length; j++) {
				Pixel originalPixel = pixels[i][j];	
				int flippedRow = (pixels.length - 1) - i;
				flippedPixel[flippedRow][j] = originalPixel;
				}
			}
		Image flippedImage = new Image(flippedPixel);
		return flippedImage;
	}

	/**
	 * Generates a new image that is a blurred version of this Image object
	 * (i.e., each pixel's red amount is an average of the red amount for itself 
	 * and its four neighbors, and similarly for green and blue).
	 * 
	 * CAUTION: For pixels on the edge of the image, use only the existing neighbors;
	 *          e.g., the pixel at [0][0] has only two neighbors at [0][1] and [1][0].
	 * 
	 * @return new image that is a blurred version of this image
	 */
	public Image blur() {
		Pixel[][] blurredPixel = new Pixel[pixels.length][pixels[0].length];
		for ( int i = 0; i < pixels.length; i ++) {
			for (int j = 0; j < pixels[0].length; j++) {
				Pixel originalPixel = pixels[i][j];
				int redSum = originalPixel.getRed();
				int blueSum = originalPixel.getBlue();
				int greenSum = originalPixel.getGreen();
				int pixelCount = 1;
				if (i > 0) {
					Pixel abovePixel = pixels[i - 1][j];
				    redSum = redSum + abovePixel.getRed();
				    greenSum = greenSum + abovePixel.getGreen();
				    blueSum = blueSum + abovePixel.getBlue();
				    pixelCount = pixelCount + 1;
				}
				if (i < pixels.length - 1) {
					Pixel belowPixel = pixels[i + 1][j];
				    redSum = redSum + belowPixel.getRed();
				    greenSum = greenSum + belowPixel.getGreen();
				    blueSum = blueSum + belowPixel.getBlue();
				    pixelCount = pixelCount + 1;
				}
				if (j > 0) {
					Pixel leftPixel = pixels[i][j - 1];
				    redSum = redSum + leftPixel.getRed();
				    greenSum = greenSum + leftPixel.getGreen();
				    blueSum = blueSum + leftPixel.getBlue();
				    pixelCount = pixelCount + 1;
				}
				if (j < pixels[0].length - 1) {
					Pixel rightPixel = pixels[i][j + 1];
				    redSum = redSum + rightPixel.getRed();
				    greenSum = greenSum + rightPixel.getGreen();
				    blueSum = blueSum + rightPixel.getBlue();
				    pixelCount = pixelCount + 1;
				}
				int averageRed = redSum/pixelCount;
				int averageGreen = greenSum/pixelCount;
				int averageBlue = blueSum/pixelCount;
				Pixel newPixel = new Pixel(averageRed, averageGreen, averageBlue);
				blurredPixel[i][j] = newPixel;
			}
			
		}
		Image blurredImage = new Image(blurredPixel);
		return blurredImage;
	}
	
	/**
	 * Darkens the image by dividing each color integer by a factor of 2 and
	 * passing those new color integers into a new pixel 
	 * 
	 * @return - the new image with darker colors
	 */
	public Image darken() {
		Pixel[][] darkenedPixel = new Pixel[pixels.length][pixels[0].length];
		for ( int i = 0; i < pixels.length; i ++) {
			for (int j = 0; j < pixels[0].length; j++) {
			Pixel originalPixel = pixels[i][j];	
			int newRed = originalPixel.getRed()/2;
			int newBlue = originalPixel.getBlue()/2;
			int newGreen = originalPixel.getGreen()/2;
			Pixel newPixel = new Pixel(newRed, newGreen, newBlue);
			darkenedPixel[i][j] = newPixel;
			}
		}
		Image darkenedImage = new Image(darkenedPixel);
		return darkenedImage;
	}
}