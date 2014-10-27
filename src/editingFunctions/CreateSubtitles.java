package editingFunctions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.MaskFormatter;


/**
 * Class that sets out the layout and functionality of the Create Subtitles feature. This
 * feature allows a user to create a .srt file (subtitles files) which they can later use
 * in the playback function. 
 * 
 * @author Chanjun Park
 *
 */
public class CreateSubtitles {

	SpringLayout _layout = new SpringLayout();
	private JTextField _subText;
	private JButton _jbAddSub;
	private JButton _jbCreateSub;
	private JButton _jbClear;
	private JTextField _subTextInformer;
	private JTextField _startTimeInformer;
	private JTextField _endTimeInformer;
	private JTextField _fontInformer;
	private JTextField _subInformer;
	private JTextField _srtTitle;
	private JTextField _srtTitleInformer;
	private JTextField _srt;
	private JFormattedTextField _startTime;
	private JFormattedTextField _timeInterval;
	private JComboBox _fontOptions;
	private String _fontOptionName;
	private JScrollPane _subtitlePane;
	private JList _subtitleList;
	private int _subcounter = 1;
	private DefaultListModel _listModel;

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
		addFontOptions(panel);
		addScrollPanel(panel);
		_fontOptionName = "Normal";
	}

	private void addTextBox(JPanel panel){

		_subText = new JTextField();
		_subText.setPreferredSize(new Dimension(235,20));
		_subText.setEditable(true);
		panel.add(_subText);

		_subTextInformer = new JTextField();
		_subTextInformer.setText("Subtitles Text:");
		_subTextInformer.setBorder(null);
		_subTextInformer.setEditable(false);
		panel.add(_subTextInformer);

		_startTimeInformer = new JTextField();
		_startTimeInformer.setText("From: ");
		_startTimeInformer.setEditable(false);
		_startTimeInformer.setBorder(null);
		panel.add(_startTimeInformer);

		_endTimeInformer = new JTextField();
		_endTimeInformer.setText("Until: ");
		_endTimeInformer.setEditable(false);
		_endTimeInformer.setBorder(null);
		panel.add(_endTimeInformer);

		_subInformer = new JTextField();
		_subInformer.setEditable(false);
		_subInformer.setPreferredSize(new Dimension(200,20));
		panel.add(_subInformer);
		
		_srtTitleInformer = new JTextField();
		_srtTitleInformer.setText("Name of .srt file: ");
		_srtTitleInformer.setBorder(null);
		_srtTitleInformer.setEditable(false);
		panel.add(_srtTitleInformer);
		
		_srtTitle = new JTextField();
		_srtTitle.setPreferredSize(new Dimension(120,20));
		_srtTitle.setEditable(true);
		panel.add(_srtTitle);
		
		_srt = new JTextField();
		_srt.setText(".srt");
		_srt.setEditable(false);
		_srt.setBorder(null);
		panel.add(_srt);
		
		_layout.putConstraint(SpringLayout.WEST, _srt,250,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _srt,110,SpringLayout.NORTH,panel);

		_layout.putConstraint(SpringLayout.WEST, _srtTitleInformer,20,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _srtTitleInformer,110,SpringLayout.NORTH,panel);

		_layout.putConstraint(SpringLayout.WEST, _srtTitle,129,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _srtTitle,108,SpringLayout.NORTH,panel);
		
		_layout.putConstraint(SpringLayout.WEST, _subInformer,20,SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _subInformer,130,SpringLayout.NORTH,panel);

		_layout.putConstraint(SpringLayout.WEST, _endTimeInformer, 140, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _endTimeInformer, 50, SpringLayout.NORTH,panel);

		_layout.putConstraint(SpringLayout.WEST, _startTimeInformer, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _startTimeInformer, 50, SpringLayout.NORTH,panel);

		_layout.putConstraint(SpringLayout.WEST, _subTextInformer, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _subTextInformer, 22, SpringLayout.NORTH, panel);

		_layout.putConstraint(SpringLayout.WEST, _subText, 115, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _subText, 22, SpringLayout.NORTH, panel);


		_jbClear = new JButton("Clear");
		panel.add(_jbClear);

		_jbClear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				_listModel.removeAllElements();
				_subcounter = 1;
			}

		});

		_layout.putConstraint(SpringLayout.WEST, _jbClear, 160, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbClear, 160, SpringLayout.NORTH, panel);


		_jbAddSub = new JButton("Add Subtitle");
		panel.add(_jbAddSub);

		_jbAddSub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (_subText.getText().length() == 0){
					_subInformer.setText("Please Specify Text");
				}
				else if (_startTime.getText().equals(_timeInterval.getText())){
					_subInformer.setText("Please give valid times");
				}
				else if (_subText.getText().length() > 0){
					_listModel.addElement(_subcounter + " " + _subText.getText() + " " + _startTime.getText() + " --> " + _timeInterval.getText() + " " + _fontOptionName);
					_subcounter++;
					_subInformer.setText("");
				}




			}

		});

		_layout.putConstraint(SpringLayout.WEST, _jbAddSub, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbAddSub, 160, SpringLayout.NORTH, panel);


		_jbCreateSub = new JButton("Create Subtitle File");
		panel.add(_jbCreateSub);

		_jbCreateSub.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				srtFileCreater(_subtitleList);
			}

		});

		_layout.putConstraint(SpringLayout.WEST,  _jbCreateSub, 20,  SpringLayout.WEST,  panel);
		_layout.putConstraint(SpringLayout.NORTH, _jbCreateSub, 192,  SpringLayout.NORTH, panel);
	}

	private void addTimeBox(JPanel panel){
		//creates place for the user to input start time
		_startTime = new JFormattedTextField(createFormatText());
		_startTime.setText("000000");
		_startTime.setPreferredSize(new Dimension(62,20));
		panel.add(_startTime);

		_startTime.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		_layout.putConstraint(SpringLayout.WEST, _startTime, 65, SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _startTime, 50, SpringLayout.NORTH,panel);


		_timeInterval = new JFormattedTextField(createFormatText());
		_timeInterval.setText("000000");		
		_timeInterval.setPreferredSize(new Dimension(62,20));
		panel.add(_timeInterval);

		_timeInterval.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fEvt) {
				JTextField tField = (JTextField)fEvt.getSource();
				tField.selectAll();
			}
		});

		_layout.putConstraint(SpringLayout.WEST, _timeInterval, 185, SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _timeInterval, 50, SpringLayout.NORTH,panel);

	}

	private void addScrollPanel(JPanel panel){
		_listModel = new DefaultListModel();
		_subtitleList = new JList(_listModel);
		_subtitlePane = new JScrollPane(_subtitleList);

		_subtitlePane.setPreferredSize(new Dimension(380,200));
		panel.add(_subtitlePane);

		_layout.putConstraint(SpringLayout.WEST, _subtitlePane, 355, SpringLayout.WEST,panel);
		_layout.putConstraint(SpringLayout.NORTH, _subtitlePane, 15, SpringLayout.NORTH,panel);

	}

	private void addFontOptions(JPanel panel){
		String[] fontOptions = {"Normal","Bold","Italics"};
		_fontOptions = new JComboBox(fontOptions);
		panel.add(_fontOptions);
		_fontInformer = new JTextField();
		_fontInformer.setText("Font Options: ");
		_fontInformer.setEditable(false);
		_fontInformer.setBorder(null);
		panel.add(_fontInformer);
		_fontOptions.setSelectedIndex(0);
		_fontOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				_fontOptionName = (String)cb.getSelectedItem();
			}

		});

		_layout.putConstraint(SpringLayout.WEST, _fontInformer, 20, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontInformer, 83, SpringLayout.NORTH, panel);

		_layout.putConstraint(SpringLayout.WEST, _fontOptions, 108, SpringLayout.WEST, panel);
		_layout.putConstraint(SpringLayout.NORTH, _fontOptions, 78, SpringLayout.NORTH, panel);
	}
	
	
	private void srtFileCreater(JList list){
		try {
			PrintWriter writer = new PrintWriter(_srtTitle.getText() + ".srt", "UTF-8");
			for (int i = 0; i < list.getModel().getSize(); i++){
				Object item = list.getModel().getElementAt(i);
				String temp = (String)item;
				String[] split = temp.split(" ");
				writer.println(split[0]);
				writer.println(split[2] + " " + split[3] + " " + split[4]);
				if (split[5].equals("Normal")){
					writer.println(split[1]);
				}
				else if (split[5].equals("Italics")){
					writer.println("<i>"+split[1]+"</i>");
				}
				else if (split[5].equals("Bold")){
					writer.println("<b>"+split[1]+"</b>");
				}
				writer.println();
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
