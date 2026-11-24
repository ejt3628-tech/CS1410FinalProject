package assign10;

import javax.swing.UIManager;


public class ImageProcessorProgram {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch(Exception e){
			e.printStackTrace(); 
		}
		
		ImageProcessorFrame imageFrame = new ImageProcessorFrame();
		imageFrame.setVisible(true);
	}

}

