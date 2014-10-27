package editingFunctions;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;


public class editWatermark {
	
	
	
	private JButton _jbPreview;
	private JButton _jbWatermark;
	SpringLayout _layout = new SpringLayout();

	protected File _watermarkInputFile;
	private JTextField _mp4Display;
	private JTextField _titleName;
	private JTextField _userInfo;
	private JTextField _titleText;
	private JButton _jbChooseVid;
	private JTextField _inputVideo;
	private JTextField _positionText;
	private JComboBox _positionChooser;
	private JButton _jbChooseImage;
	private String _topLeft = "overlay=10:10[outv]\"";
	private String _topRight = "overlay=main_w-overlay_w-10:10[outv]\"";
	private String _bottomRight = "overlay=(main_w-overlay_w-10)/2:(main_h-overlay_h-10)/2[outv]\"";
	private String _bottomLeft = "overlay=10:main_h-overlay_h-10[outv]\"";
	private String _overlayAvconv1 = "\"[1:v]scale=50:40[wat];[0:v][wat]";
	private String _waterMarkmp4Path;
	private String _watermarkImagePath;
	private String _outputName;
	private String _positionString;
	private String _avconvPosition;
	private Integer _exitStatus;
	private File _previewPicture;
	private Integer _ssExitStatus;
	protected File _inputImageFile;
	private JTextField _inputImage;
	private String _newFile;
	
	
	// Method to insert the tab into the JTabbedPane
	public void insertWaterMarkTab(final JTabbedPane pane){
		JPanel watermarkPagePanel = new JPanel();
		pane.addTab("Add Watermark", watermarkPagePanel);
		setWatermarkPageFeatures(watermarkPagePanel);
		_avconvPosition = _topRight;
	}

	// Sets out some of the GUI features of the Watermark Tab
	private void setWatermarkPageFeatures(final JPanel panel){
		panel.setLayout(_layout);
		addVidFileChooser(panel,"Choose Video File");
		addImageFileChooser(panel,"Choose Image for Watermark");
		addPanelFeatures(panel);
		addPositionOptions(panel);
		_avconvPosition = _topLeft;
		_jbWatermark = new JButton("Add Watermark");
		_jbPreview = new JButton("Show Preview");
		
		
		panel.add(_jbWatermark);
		panel.add(_jbPreview);
		_layout.putConstraint(SpringLayout.WEST, _jbWatermark, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbWatermark, 70, SpringLayout.NORTH, _userInfo);
		
		_layout.putConstraint(SpringLayout.WEST, _jbPreview, 190, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbPreview, 70,  SpringLayout.NORTH, _userInfo);
		
		_layout.putConstraint(SpringLayout.NORTH,_positionText,35,SpringLayout.NORTH,_userInfo);
		_layout.putConstraint(SpringLayout.WEST, _positionText, 15, SpringLayout.WEST, panel);
		
		_layout.putConstraint(SpringLayout.NORTH,_positionChooser,32,SpringLayout.NORTH,_userInfo);
		_layout.putConstraint(SpringLayout.WEST, _positionChooser, 170, SpringLayout.WEST, panel);
		
		// Listener for the create Watermark button
		_jbWatermark.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				watermarkSW wmSW = new watermarkSW();
				wmSW.execute();
				
			}
			
		});
		// Listener for the preview button
		_jbPreview.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				getScreenshotSW gsSW = new getScreenshotSW();
				gsSW.execute();
				_userInfo.setText("Making Preview...");
			}
			
		});
		

		
	}
	
	// Adds the JComboBox with the position options
	private void addPositionOptions(JPanel panel){
		String[] positionOptions = {"Bottom Left","Bottom Right","Top Left","Top Right"};
		_positionChooser = new JComboBox(positionOptions);
		panel.add(_positionChooser);
		_positionText = new JTextField();
		_positionText.setText("Watermark Position: ");
		_positionText.setBorder(null);
		_positionText.setEditable(false);
		panel.add(_positionText);
		_positionChooser.setSelectedIndex(3);
		_positionChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cbSize = (JComboBox)e.getSource();
				_positionString = (String)cbSize.getSelectedItem();
				if (_positionString.equals("Bottom Left")){
					_avconvPosition = _bottomLeft;
				}
				else if (_positionString.equals("Bottom Right")){
					_avconvPosition = _bottomRight;
				}
				else if (_positionString.equals("Top Right")){
					_avconvPosition = _topRight;
				}
				else {
					_avconvPosition = _topLeft;
				}
			}

		});


	}
	
	// Adds a chooser allowing the user to choose a video to edit
	private void addVidFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		_jbChooseVid = new JButton(text);

		_jbChooseVid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();

				// remove the accept all filter.
				jfile.setAcceptAllFileFilterUsed(false);
				// add mp4 as filter.
				jfile.addChoosableFileFilter(new FileNameExtensionFilter("MPEG-4", "mp4","avi"));

				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_watermarkInputFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_inputVideo.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChooseVid);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChooseVid, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChooseVid, 15, SpringLayout.NORTH, panel);

		//creates text field to store which file the user input
		_inputVideo = new JTextField();
		_inputVideo.setPreferredSize(new Dimension(248,20));
		_inputVideo.setEditable(false);
		_inputVideo.setBackground(new Color(245,245,245));
		panel.add(_inputVideo);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _inputVideo, 15, SpringLayout.EAST, _jbChooseVid);
		_layout.putConstraint(SpringLayout.NORTH, _inputVideo, 3, SpringLayout.NORTH, _jbChooseVid);



	}
	
	// Adds an Image chooser allowing the user to select a watermark
	private void addImageFileChooser(final JPanel panel,String text){
		//create and add functionality for file choosing button
		_jbChooseImage = new JButton(text);


		_jbChooseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens JFileChooser when button pressed
				JFileChooser jfile = new JFileChooser();

				// remove the accept all filter
				jfile.setAcceptAllFileFilterUsed(false);
				// filters results so only audio files can be added
				jfile.setFileFilter(new FileNameExtensionFilter("jpeg & png Images", "jpg", "png","jpeg"));

				int response = jfile.showOpenDialog(null);
				if (response == JFileChooser.APPROVE_OPTION) {
					_inputImageFile = jfile.getSelectedFile();
					String chosenFile = jfile.getSelectedFile().toString();
					_inputImage.setText(chosenFile);
				}
				jfile.setVisible(true);
			}
		});
		panel.add(_jbChooseImage);


		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _jbChooseImage, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbChooseImage, 30, SpringLayout.NORTH, _jbChooseVid);

		//creates text field to store which file the user input for the
		_inputImage = new JTextField();
		_inputImage.setPreferredSize(new Dimension(248,20));
		_inputImage.setEditable(false);
		_inputImage.setBackground(new Color(245,245,245));
		panel.add(_inputImage);

		//move the text box containing user file choice button to its location
		_layout.putConstraint(SpringLayout.WEST, _inputImage, 15, SpringLayout.EAST, _jbChooseImage);
		_layout.putConstraint(SpringLayout.NORTH, _inputImage, 3, SpringLayout.NORTH, _jbChooseImage);

	}
	
	// Swingworker for adding a watermark onto a video
	private class watermarkSW extends SwingWorker<Integer,String>{
		
		protected void done(){
			if (_exitStatus == 0){
				_userInfo.setText("Watermark added!");
			}
			else {
				_userInfo.setText("Error Encountered");
			}


		}

		@Override
		protected Integer doInBackground() throws Exception {
			_waterMarkmp4Path = _watermarkInputFile.getAbsolutePath();
			_watermarkImagePath = _inputImageFile.getAbsolutePath();
			_outputName = _titleName.getText() + ".mp4";
			String command = "avconv -i " + _waterMarkmp4Path + " -i " + _watermarkImagePath + " -filter_complex " + _overlayAvconv1 +_avconvPosition + " -map " + "\"[outv]\"" + " -map" + " 0:a " + _outputName;
			System.out.println(command);
			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c",command);			
			Process process = builder.start();
			_userInfo.setText("Adding Watermark...");
			_exitStatus = process.waitFor();
			return null;
		}
	
	}
	
	// Swingworker that grabs a screenshot from the video input
	private class getScreenshotSW extends SwingWorker<Integer,String>{
		
		protected void done(){
			if (_ssExitStatus == 0){
				screenshotWatermarkSW sswSW = new screenshotWatermarkSW();
				sswSW.execute();
			}
			else {
				_userInfo.setText("Error Encountered");
			}
		}


		@Override
		protected Integer doInBackground() throws Exception {
			_waterMarkmp4Path = _watermarkInputFile.getAbsolutePath();
			String[] cmd = {"avconv","-i",_waterMarkmp4Path,"-vframes","1","-an","-s","800x444","-ss","30","WatermarkScreenshot.jpg"};
			ProcessBuilder builder = new ProcessBuilder(cmd);
			Process process = builder.start();
			_ssExitStatus = process.waitFor();
			_previewPicture = new File("WatermarkScreenshot.jpg");
			return null;
		}
		
	}
	
	// Swingworker that adds the watermark onto the screenshot and displays it as a preview
	private class screenshotWatermarkSW extends SwingWorker<Integer,String>{
		
		
		protected void done(){
			if (_ssExitStatus == 0){
				Desktop test = Desktop.getDesktop();
				File previewPicture = new File("PreviewWatermark.jpg");
				try {
					test.open(previewPicture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				_userInfo.setText("Showing Preview");
			}
			else {
				_userInfo.setText("Error Encountered");
			}
		}


		@Override
		protected Integer doInBackground() throws Exception {
			_newFile = _previewPicture.getAbsolutePath();
			_watermarkImagePath = _inputImageFile.getAbsolutePath();

			String command = "avconv -i " + _newFile + " -i " +_watermarkImagePath + " -filter_complex " +_overlayAvconv1+ _avconvPosition + " -map " + "\"[outv]\" " + "PreviewWatermark.jpg";
			System.out.println(command);
			ProcessBuilder builder2 = new ProcessBuilder("/bin/bash", "-c",command);
			Process process2 = builder2.start();
			_ssExitStatus = process2.waitFor();
			return null;
		}
		
	}

	// Adds GUI features into the watermark JPanel
	private void addPanelFeatures(final JPanel panel){
		//add text box requesting user to input an output name
		_titleText = new JTextField();
		_titleText.setText("Output file name :");
		_titleText.setPreferredSize(new Dimension(115,20));
		_titleText.setEditable(false);
		_titleText.setBorder(null);
		panel.add(_titleText);

		//move the title text to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleText, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _titleText, 30, SpringLayout.NORTH, _jbChooseImage);

		//add the user input box for the title of the output file
		_titleName = new JTextField();
		_titleName.setPreferredSize(new Dimension(266,20));
		panel.add(_titleName);
		//move the mp3 output file name to its location on the screen
		_layout.putConstraint(SpringLayout.WEST, _titleName, 7, SpringLayout.EAST, _titleText);
		_layout.putConstraint(SpringLayout.NORTH, _titleName, 30, SpringLayout.NORTH, _jbChooseImage);

		//creates text field to display the .mp4 to indicate to the use it will by added automatically
		_mp4Display = new JTextField();
		_mp4Display.setText(".mp4");
		_mp4Display.setBorder(null);
		_mp4Display.setEditable(false);
		panel.add(_mp4Display);
		//move the choose file button to its location
		_layout.putConstraint(SpringLayout.WEST, _mp4Display, 2, SpringLayout.EAST, _titleName);
		_layout.putConstraint(SpringLayout.NORTH, _mp4Display, 30, SpringLayout.NORTH, _jbChooseImage);

		//add textbox for error messages to be conveyed to the user
		_userInfo = new JTextField();
		_userInfo.setPreferredSize(new Dimension(422,20));
		_userInfo.setBackground(new Color(245,245,245));
		_userInfo.setEditable(false);

		//move the text box containing user information 
		_layout.putConstraint(SpringLayout.WEST, _userInfo, 15, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _userInfo, 25, SpringLayout.NORTH, _titleText);
		panel.add(_userInfo);

	}
	
	

}
