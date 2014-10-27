package editingFunctions;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class editTitle {

	private JButton _jbChoose;
	private JButton _jbPreview;
	private JButton _jbTitlePage;
	private JTextField _userInfo;
	private JTextField _titleName;
	private JTextField _titleText;
	private JTextField _mp4Choice;
	private JTextField _mp4Display;
	private JTextField _fontSelection;
	private JTextField _fontSizeSelection;
	private JTextField _titleTextInformer;
	private JTextField _titleTextRaw;
	private JComboBox _fonts;
	private JComboBox _fontSize;
	private String _fontName;
	private String _fontSizeString;
	private String _selectedTitleText;
	private String _drawText = " -vf drawtext=\"fontfile=/usr/share/fonts/truetype/freefont/Free";
	private String _drawText2 = ".ttf: text=";
	private String _avconvFontSize;
	private String _drawTextVideo = " -strict experimental -vf drawtext=\"fontfile=/usr/share/fonts/truetype/freefont/Free";
	private String _drawTextVideo2 = ".ttf: text='";
	private String _drawTextVideo3 = "':x=(w-text_w)/2:y=150: fontsize=";
	private String _drawTextVideo4 = ": draw='lt(t,10)'\"";
	private String _outputName;
	private String _newFile;
	private Image preview;
	protected File _previewPicture;
	protected File _titleInputFile;
	private Integer _exitStatus;
	SpringLayout _layout = new SpringLayout();





	// Method to insert the Title tab into JTabbedFrame.
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Create Title Page", titlePagePanel);
		setTitlePageFeatures(titlePagePanel);
		_fontName = "Sans";
		_fontSizeString = "46";
	}


	// Inserts the features for the Add Title Page JPanel
	private void setTitlePageFeatures(final JPanel panel){	
		panel.setLayout(_layout);
		addVideoChooser(panel);
		addPanelFeatures(panel);
		addFontOptions(panel);
		addFontSizeOptions(panel);

		//Create the two buttons, show preview and create title page
		_jbPreview = new JButton("Show Preview");
		_jbTitlePage = new JButton("Create Title Page");
		panel.add(_jbTitlePage);
		panel.add(_jbPreview);

		//Sort out where all the components go in the panel/
		_layout.putConstraint(SpringLayout.NORTH, _jbPreview, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbPreview, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbTitlePage, 130, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _jbTitlePage, 160, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fonts,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fonts,105,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSelection,80,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSelection, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH,_fontSize,77,SpringLayout.NORTH,_mp4Choice);
		_layout.putConstraint(SpringLayout.WEST, _fontSize, 300, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontSizeSelection, 80, SpringLayout.NORTH, _mp4Choice);
		_layout.putConstraint(SpringLayout.WEST,_fontSizeSelection,187,SpringLayout.WEST,panel);


		//Listener for the preview button
		_jbPreview.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				//Calls the swingworker to grab a screenshot from the mp4
				getTitleScreenshotSW tSW = new getTitleScreenshotSW();
				try{
					tSW.execute();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});




		//Listener for the Create Title Page button
		_jbTitlePage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Calls the videoConverterSW to add text to video
					videoConverterSW vcSW = new videoConverterSW();
					vcSW.execute();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}



	// Method to add font options
	private void addFontOptions(JPanel panel){
		String[] fontOptions = {"Sans","Mono","Serif"};
		_fonts = new JComboBox(fontOptions);
		panel.add(_fonts);
		_fontSelection = new JTextField();
		_fontSelection.setText("Select Font: ");
		_fontSelection.setBorder(null);
		_fontSelection.setEditable(false);
		panel.add(_fontSelection);
		_fonts.setSelectedIndex(0);
		_fonts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				_fontName = (String)cb.getSelectedItem();
			}

		});

	}




	//Method to add font size options
	private void addFontSizeOptions(JPanel panel){
		String[] fontSizeOptions = {"28","34","40","46","52","58","64"};
		_fontSize = new JComboBox(fontSizeOptions);
		panel.add(_fontSize);
		_fontSizeSelection = new JTextField();
		_fontSizeSelection.setText("Select Font Size: ");
		_fontSizeSelection.setBorder(null);
		_fontSizeSelection.setEditable(false);
		panel.add(_fontSizeSelection);
		_fontSize.setSelectedIndex(3);
		_fontSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cbSize = (JComboBox)e.getSource();
				_fontSizeString = (String)cbSize.getSelectedItem();

			}

		});


	}




	// Swingworker that creates a screenshot from the mp4 to be used soon for preview image
	private class getTitleScreenshotSW extends SwingWorker<Integer,String>{

		protected void done(){
			previewTextSW ptSW = new previewTextSW();
			try {
				ptSW.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		@Override
		protected Integer doInBackground() throws Exception {
			_avconvFontSize = ":x=(w-text_w)/2:y=150: fontsize="+_fontSizeString;
			_selectedTitleText = _titleTextRaw.getText();
			String file = _titleInputFile.getAbsolutePath();
			String[] cmd = {"avconv","-i",file,"-vframes","1","-an","-s","800x444","-ss","30","TitleScreenShot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			process.waitFor();
			_previewPicture = new File("TitleScreenShot.jpg");
			return null;		
		}


	}

	// Swingworker that creates the preview image with text from screenshot
	private class previewTextSW extends SwingWorker<Integer,String>{

		protected void done(){
			if (_exitStatus == 0){
				Desktop test = Desktop.getDesktop();
				File previewPicture = new File("previewTitle.jpg");
				try {
					test.open(previewPicture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_userInfo.setText("Showing Preview");
			}
			else {
				_userInfo.setText("Preview failed");
			}
		}

		@Override
		protected Integer doInBackground() throws Exception {
			_newFile = _previewPicture.getAbsolutePath();
			String cmd2 = "avconv -i " + _newFile + _drawText + _fontName + _drawText2+_selectedTitleText+"'"+_avconvFontSize+"\" " + "previewTitle.jpg";
			ProcessBuilder builder2 = new ProcessBuilder("/bin/bash","-c",cmd2);
			Process process2 = builder2.start();	
			_exitStatus = process2.waitFor();
			return null;
		}

	}



	//Swingworker that creates an mp4 with Text on it for the first 10 seconds
	private class videoConverterSW extends SwingWorker<Integer,String>{

		protected void done(){
			if (_exitStatus == 0){

			_userInfo.setText("Title Successfully added");
			}
			else{
				_userInfo.setText("Error adding Title Text");
			}
		}

		@Override
		protected Integer doInBackground() throws Exception {
			_outputName = _titleName.getText() +".mp4";
			String file2 = _titleInputFile.getAbsolutePath();
			_selectedTitleText = _titleTextRaw.getText();
			String command = "avconv -i " + file2 + _drawTextVideo + _fontName + _drawTextVideo2 + _selectedTitleText + _drawTextVideo3 +_fontSizeString+ _drawTextVideo4 + " " + _outputName;
			ProcessBuilder builder3 = new ProcessBuilder("/bin/bash","-c",command);
			_userInfo.setText("Adding Title Text...");
			Process process3 = builder3.start();
			_exitStatus = process3.waitFor();
			return null;
		}

	}







	// Adds the features to the JPanel
	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_titleText = new JTextField();
		_titleText.setText("Output file name :");
		_titleText.setBorder(null);
		_titleText.setPreferredSize(new Dimension(115,20));
		_titleText.setEditable(false);
		panel.add(_titleText);
		_layout.putConstraint(SpringLayout.WEST, _titleText, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _titleText, 42, SpringLayout.NORTH, panel);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_titleName);
		_layout.putConstraint(SpringLayout.WEST, _titleName, 125, SpringLayout.WEST, _titleText);
		_layout.putConstraint(SpringLayout.NORTH, _titleName, 42, SpringLayout.NORTH, panel);


		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setBorder(null);
		_mp4Display.setEditable(false);
		_titleName.setPreferredSize(new Dimension(115,20));
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 115, SpringLayout.WEST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 42, SpringLayout.NORTH, panel);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(275,20));
		_userInfo.setEditable(false);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 117, SpringLayout.NORTH, panel);
		panel.add(_userInfo);

		_titleTextInformer = new JTextField();
		_titleTextInformer.setText("Please specify text to add to video: ");
		_titleTextInformer.setBorder(null);
		_titleTextInformer.setEditable(false);
		_layout.putConstraint(SpringLayout.NORTH, _titleTextInformer, 68,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _titleTextInformer, 15, SpringLayout.WEST, panel);
		panel.add(_titleTextInformer);

		_titleTextRaw = new JTextField();
		_titleTextRaw.setPreferredSize(new Dimension (300,20));
		panel.add(_titleTextRaw);
		_layout.putConstraint(SpringLayout.NORTH, _titleTextRaw, 68,SpringLayout.NORTH, panel);
		_layout.putConstraint(SpringLayout.WEST, _titleTextRaw, 240, SpringLayout.WEST, _jbChoose);

	}




	//Method that adds a functionality to choose a file.
	private void addVideoChooser(final JPanel panel){
		//create and add functionality for file choosing button
		_jbChoose = new JButton("Choose File");
		JFileChooser jfile = null;
		_jbChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();
				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_titleInputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_mp4Choice.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChoose);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChoose, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChoose, 10, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_mp4Choice = new JTextField();
		_mp4Choice.setPreferredSize(new Dimension(150,20));
		_mp4Choice.setEditable(false);
		panel.add(_mp4Choice);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Choice, 125, SpringLayout.WEST, _jbChoose);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Choice, 12, SpringLayout.NORTH, panel);



	}



}