package editingFunctions;

import java.awt.Dimension;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.MaskFormatter;

public class createSubtitles {
	
	SpringLayout _layout = new SpringLayout();
	private JTextField _subText;
	private JButton _jbAddSub;
	private JTextField _subTextInformer;
	private JTextField _startTimeInformer;
	private JTextField _endTimeInformer;
	private JFormattedTextField _startTime;
	private JFormattedTextField _timeInterval;


	// Method to insert the Subs tab into JTabbedFrame.
	public void insertSubsPageTab(final JTabbedPane pane){
		JPanel subpagePanel = new JPanel();
		pane.addTab("Create Subtitles", subpagePanel);
		setSubtitlesFeatures(subpagePanel);
	}
	
	private void setSubtitlesFeatures(JPanel panel){
		panel.setLayout(_layout);
		addTextBox(panel);
		addTimeBox(panel);
	}
	
	private void addTextBox(JPanel panel){
		_subText = new JTextField();
		_subText.setPreferredSize(new Dimension(235,20));
		_subText.setEditable(true);
		panel.add(_subText);
		
		_subTextInformer = new JTextField();
		_subTextInformer.setText("Subtitles Text:");
		_subTextInformer.setEditable(false);
		panel.add(_subTextInformer);
		
		_startTimeInformer = new JTextField();
		_startTimeInformer.setText("From: ");
		_startTimeInformer.setEditable(false);
		panel.add(_startTimeInformer);
		
		_endTimeInformer = new JTextField();
		_endTimeInformer.setText("Until: ");
		_endTimeInformer.setEditable(false);
		panel.add(_endTimeInformer);
		
		_layout.putConstraint(SpringLayout.WEST, _endTimeInformer, 140, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _endTimeInformer, 50, SpringLayout.NORTH,panel);
		
		_layout.putConstraint(SpringLayout.WEST, _startTimeInformer, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _startTimeInformer, 50, SpringLayout.NORTH,panel);
		
		_layout.putConstraint(SpringLayout.WEST, _subTextInformer, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _subTextInformer, 22, SpringLayout.NORTH, panel);
		
		_layout.putConstraint(SpringLayout.WEST, _subText, 115, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _subText, 22, SpringLayout.NORTH, panel);

		_jbAddSub = new JButton("Add Subtitle");
		

	}
	
	private void addTimeBox(JPanel panel){
		//creates place for the user to input start time
		_startTime = new JFormattedTextField(createFormatText());
		_startTime.setText("000000");
		_startTime.setPreferredSize(new Dimension(62,20));
		panel.add(_startTime);
		
		_layout.putConstraint(SpringLayout.WEST, _startTime, 70, SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _startTime, 50, SpringLayout.NORTH,panel);

		
		_timeInterval = new JFormattedTextField(createFormatText());
		_timeInterval.setText("000000");		
		_timeInterval.setPreferredSize(new Dimension(62,20));
		panel.add(_timeInterval);
		
		_layout.putConstraint(SpringLayout.WEST, _timeInterval, 190, SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _timeInterval, 50, SpringLayout.NORTH,panel);

	}
	
	private MaskFormatter createFormatText(){
		MaskFormatter mf1 = null;
		try {
			mf1 = new MaskFormatter("##:##:##");
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		return mf1;
	}
	
	
}
