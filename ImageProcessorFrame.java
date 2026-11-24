package assign10;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ImageProcessorFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public ImageProcessorFrame() {
		JPanel imagePanel = new JPanel();
		this.add(imagePanel);
		Dimension frameSize = new Dimension(150, 150);
		JMenuItem crop = new JMenuItem("Crop");
		JMenuItem brightness = new JMenuItem("Brightness");
		JMenuItem redBlue = new JMenuItem("Red-blue swap");
		JMenuItem rotate = new JMenuItem("Rotate");
		
		JMenuItem open = new JMenuItem("Open New Image File");
		JMenuItem save = new JMenuItem("Save New Image File");
		
		JMenu file = new JMenu("File");
		file.add(open);
		file.add(save);
		
		JMenu filter = new JMenu("Filters");
		filter.add(crop);
		filter.add(brightness);
		filter.add(redBlue);
		filter.add(rotate);
		
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(file);
		menuBar.add(filter);
		
		this.setJMenuBar(menuBar);
		
		this.setPreferredSize(frameSize);
		this.pack();
	}
}
