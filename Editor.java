import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class Editor extends JFrame implements ActionListener
{
	private JPanel menuP, toolBarP, toolsP, colorsP, imageP;
	private JScrollPane scrollPane;
	private JRadioButton brushRB, eraserRB, fillRB, oRectRB, fRectRB;
	private JRadioButton blackRB, whiteRB, blueRB, cyanRB, darkGrayRB;
	private JRadioButton grayRB, greenRB, magentaRB, orangeRB, pinkRB, redRB, yellowRB;
	private ButtonGroup toolsG, colorsG, thickG;
	private JLabel imageL;
	private ImageIcon ic;
	private ImageEdit image, subImage;
	private int tool, color, thickness;
	private EditorMIL mil;
	
	private JMenuBar menuBar;
	private JMenu fileM, editM, thickM;
	private JMenuItem newMI, openMI, saveMI, saveAsMI, clearMI, dimensionMI;
	private JRadioButtonMenuItem oneTMI, threeTMI, fiveTMI, sevenTMI;

	public Editor(String file)
	{
		super("Image Editor");
		
		//initial settings
		tool = 0;
		color = -16777216;
		thickness = 3;
		
		//initialize mouse listener
		mil = new EditorMIL();
		
		//create the image for editing
		if (file != null)
			image = new ImageEdit(new File(file));
		else
			image = new ImageEdit(1,1);

		//create JLabel for image
		ic = new ImageIcon(image.buildImage());
		imageL = new JLabel(ic);
		imageL.setHorizontalAlignment(JLabel.LEFT);
		imageL.setVerticalAlignment(JLabel.TOP);
		
		//create tools radio buttons	
		brushRB = new JRadioButton("Brush");
		brushRB.setActionCommand("Brush");
		brushRB.setSelected(true);
		brushRB.addActionListener(this);
		eraserRB = new JRadioButton("Eraser");
		eraserRB.setActionCommand("Eraser");
		eraserRB.addActionListener(this);
		fillRB = new JRadioButton("Fill");
		fillRB.setActionCommand("Fill");
		fillRB.addActionListener(this);
		oRectRB = new JRadioButton("Open Rect");
		oRectRB.setActionCommand("Open Rect");
		oRectRB.addActionListener(this);
		fRectRB = new JRadioButton("Filled Rect");
		fRectRB.setActionCommand("Filled Rect");
		fRectRB.addActionListener(this);
		
		//create colors radio buttons	
		blackRB = new JRadioButton("Black");
		blackRB.setActionCommand("Black");
		blackRB.setSelected(true);
		blackRB.addActionListener(this);
		whiteRB = new JRadioButton("White");
		whiteRB.setActionCommand("White");
		whiteRB.addActionListener(this);
		blueRB = new JRadioButton("Blue");
		blueRB.setActionCommand("Blue");
		blueRB.addActionListener(this);
		cyanRB = new JRadioButton("Cyan");
		cyanRB.setActionCommand("Cyan");
		cyanRB.addActionListener(this);
		darkGrayRB = new JRadioButton("Dark Gray");
		darkGrayRB.setActionCommand("Dark Gray");
		darkGrayRB.addActionListener(this);
		grayRB = new JRadioButton("Gray");
		grayRB.setActionCommand("Gray");
		grayRB.addActionListener(this);
		greenRB = new JRadioButton("Green");
		greenRB.setActionCommand("Green");
		greenRB.addActionListener(this);
		magentaRB = new JRadioButton("Magenta");
		magentaRB.setActionCommand("Magenta");
		magentaRB.addActionListener(this);
		orangeRB = new JRadioButton("Orange");
		orangeRB.setActionCommand("Orange");
		orangeRB.addActionListener(this);
		pinkRB = new JRadioButton("Pink");
		pinkRB.setActionCommand("Pink");
		pinkRB.addActionListener(this);
		redRB = new JRadioButton("Red");
		redRB.setActionCommand("Red");
		redRB.addActionListener(this);
		yellowRB = new JRadioButton("Yellow");
		yellowRB.setActionCommand("Yellow");
		yellowRB.addActionListener(this);
		
		//create and populate button groups for 
		//tools and colors
		toolsG = new ButtonGroup();
		toolsG.add(brushRB);
		toolsG.add(eraserRB);
		toolsG.add(fillRB);
		toolsG.add(fRectRB);
		toolsG.add(oRectRB);
		colorsG = new ButtonGroup();
		colorsG.add(blackRB);
		colorsG.add(blueRB);
		colorsG.add(cyanRB);
		colorsG.add(darkGrayRB);
		colorsG.add(grayRB);
		colorsG.add(greenRB);
		colorsG.add(whiteRB);
		colorsG.add(magentaRB);
		colorsG.add(orangeRB);
		colorsG.add(pinkRB);
		colorsG.add(redRB);
		colorsG.add(yellowRB);
		
		//create and populate menu bar
		menuBar = new JMenuBar();
		fileM = new JMenu("File");
		editM = new JMenu("Edit");
		menuBar.add(fileM);
		menuBar.add(editM);
		newMI = new JMenuItem("New");
		newMI.addActionListener(this);
		openMI = new JMenuItem("Open");
		openMI.addActionListener(this);
		saveMI = new JMenuItem("Save");
		saveMI.addActionListener(this);
		saveAsMI = new JMenuItem("Save As");
		saveAsMI.addActionListener(this);
		fileM.add(newMI);
		fileM.add(openMI);
		fileM.add(saveMI);
		fileM.add(saveAsMI);
		thickM = new JMenu("Thickness");
		clearMI = new JMenuItem("Clear");
		clearMI.addActionListener(this);
		dimensionMI = new JMenuItem("Set Dimensions");
		dimensionMI.addActionListener(this);
		editM.add(thickM);
		editM.add(clearMI);
		editM.add(dimensionMI);
		oneTMI = new JRadioButtonMenuItem("1 px");
		oneTMI.setActionCommand("1 px");
		oneTMI.addActionListener(this);
		threeTMI = new JRadioButtonMenuItem("3 px");
		threeTMI.addActionListener(this);
		threeTMI.setSelected(true);
		threeTMI.setActionCommand("3 px");
		fiveTMI = new JRadioButtonMenuItem("5 px");
		fiveTMI.addActionListener(this);
		fiveTMI.setActionCommand("5 px");
		sevenTMI = new JRadioButtonMenuItem("7 px");
		sevenTMI.addActionListener(this);
		sevenTMI.setActionCommand("7 px");
		thickM.add(oneTMI);
		thickM.add(threeTMI);
		thickM.add(fiveTMI);
		thickM.add(sevenTMI);
		
		//create and populate button groups for 
		//thickness
		thickG = new ButtonGroup();
		thickG.add(oneTMI);
		thickG.add(threeTMI);
		thickG.add(fiveTMI);
		thickG.add(sevenTMI);
		
		//create JPanels amd get Container
		Container content = getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		menuP = new JPanel();
		menuP.setLayout(new GridLayout(1,1));
		menuP.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		toolBarP = new JPanel();
		toolBarP.setLayout(new GridLayout(1,2));
		toolBarP.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		toolsP = new JPanel();
		toolsP.setLayout(new GridLayout(1,3));
		colorsP = new JPanel();
		colorsP.setLayout(new GridLayout(2,6));
		imageP = new JPanel();
		imageP.setLayout(new GridLayout(1,1));
		imageP.addMouseListener(mil);
		imageP.addMouseMotionListener(mil);
		scrollPane = new JScrollPane();
		scrollPane.getViewport().add(imageP, BorderLayout.WEST);
		
		//populate JPanels and Container
		menuP.add(menuBar);
		toolBarP.add(toolsP);
		toolBarP.add(colorsP);
		toolsP.add(brushRB);
		toolsP.add(eraserRB);
		toolsP.add(fillRB);
		toolsP.add(oRectRB);
		toolsP.add(fRectRB);
		colorsP.add(blackRB);
		colorsP.add(blueRB);
		colorsP.add(cyanRB);
		colorsP.add(darkGrayRB);
		colorsP.add(grayRB);
		colorsP.add(greenRB);
		colorsP.add(whiteRB);
		colorsP.add(magentaRB);
		colorsP.add(orangeRB);
		colorsP.add(pinkRB);
		colorsP.add(redRB);
		colorsP.add(yellowRB);
		imageP.add(imageL, BorderLayout.WEST);
		content.add(menuP);
		content.add(toolBarP);
		content.add(scrollPane);

		//window settings
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
	}
	/**
	* Called after changes to update the image
	* users see.
	*/
	public void updateGUI()
	{
		imageP.removeAll();
		ic = new ImageIcon(image.buildImage());
		imageL = new JLabel(ic);
		imageL.setHorizontalAlignment(JLabel.LEFT);
		imageL.setVerticalAlignment(JLabel.TOP);
		imageP.add(imageL);
		imageP.updateUI(); 
		scrollPane.repaint();
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			int w, h;
			String temp;
			//switch for radiobuttons
			//sets colors, tools, and thickness
			switch(e.getActionCommand())
			{
				case "Brush":
					tool = 0;
					break;
				case "Eraser":
					tool = 1;
					break;
				case "Fill":
					tool = 2;
					break;
				case "Open Rect":
					tool = 3;
					break;
				case "Filled Rect":
					tool = 4;
					break;
				case "Black":
					color = -16777216;
					break;
				case "White":
					color = -1;
					break;
				case "Blue":
					color = -16776961;
					break;
				case "Cyan":
					color = -16711681;
					break;
				case "Dark Gray":
					color = -12566464;
					break;
				case "Gray":
					color = -8355712;
					break;
				case "Green":
					color = -16711936;
					break;
				case "Magenta":
					color = -65281;
					break;
				case "Orange":
					color = -14336;
					break;
				case "Pink":
					color = -20561;
					break;
				case "Red":
					color = -65536;
					break;
				case "Yellow":
					color = -256;
					break;
				case "1 px":
					thickness = 1;
					break;
				case "3 px":
					thickness = 3;
					break;
				case "5 px":
					thickness = 5;
					break;
				case "7 px":
					thickness = 7;
					break;
			}
			//if e is not from a radio button
			//check for menuitem
			if (!(e.getSource() instanceof JMenuItem))
				return;
			JFileChooser chooser = new JFileChooser();
			//switch for menuitems
			switch(((JMenuItem)e.getSource()).getText())
			{
				case "Open":
					chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp", "gif"));
					chooser.setDialogType(JFileChooser.OPEN_DIALOG);
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.showOpenDialog(null);
					image = new ImageEdit(chooser.getSelectedFile());
					updateGUI();
					break;
				case "New":
					temp = JOptionPane.showInputDialog(this, "Enter the new Width");
					w = Integer.parseInt(temp);
					temp = JOptionPane.showInputDialog(this, "Enter the new Height");
					h = Integer.parseInt(temp);
					image = new ImageEdit(w,h);
					updateGUI();
					break;
				case "Save":
					if (image.getFile() == null)
						break;
					image.write();
					break;
				case "Save As":
					chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "bmp", "gif"));
					chooser.setDialogType(JFileChooser.SAVE_DIALOG);
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					chooser.showSaveDialog(null);
					image.setFile(chooser.getSelectedFile());
					image.write();
					break;
				case "Clear":
					image.clearPixel();
					updateGUI();
					break;
				case "Set Dimensions":
					temp = JOptionPane.showInputDialog(this, "Enter the new Width");
					w = Integer.parseInt(temp);
					temp = JOptionPane.showInputDialog(this, "Enter the new Height");
					h = Integer.parseInt(temp);
					image.setDimensions(w,h);
					updateGUI();
					break;
			}
		}
		catch(IOException ioe)
		{
			System.out.println("Invalid File");
		}
	}
	//inner class for mouse listener
	private class EditorMIL extends MouseInputAdapter
	{
		//old coordinates are the points before being dragged
		//new coords. are the current points of the cursor
		int oldX, oldY, newX, newY;
		public void mousePressed(MouseEvent e)
		{
			//set old x and y for use in mouseDragged and mouseReleased
			oldX = e.getX();
			oldY = e.getY();

			//check for fill tool
			switch (tool)
			{
				case 2:
					image.fillPixel(e.getX(),e.getY(), color);
					updateGUI();
					break;
			}
		}
		public void mouseReleased(MouseEvent e)
		{
			//check for rectangle tools
			switch (tool)
			{
				case 3:
					image.setPixelRect(oldX, oldY, e.getX(), e.getY(), thickness, color);
					updateGUI();
					break;
				case 4:
					image.fillPixelRect(oldX, oldY, e.getX(), e.getY(), thickness, color);
					updateGUI();
					break;
			}
		}
		public void mouseDragged(MouseEvent e) 
		{
			newX = e.getX();
			newY = e.getY();

			//check for brush or eraser tools
			switch (tool)
			{
				case 0:
					image.setPixelLine(oldX, oldY, newX, newY, thickness, color);
					updateGUI();
					//update points
					oldX = newX;
					oldY = newY;
					break;
				case 1:
					image.setPixelLine(oldX, oldY, newX, newY, thickness,  -1);
					updateGUI();
					//update points
					oldX = newX;
					oldY = newY;
					break;
			}
		}
	}
	public static void main(String[] args)
	{
		//check for provided file
		if (args.length > 0)
		{
			//open provided file in editor
			Editor gui = new Editor(args[0]);
		}
		else
		{
			//no file given; open a new image
			Editor gui = new Editor(null);
		}
	}
}
