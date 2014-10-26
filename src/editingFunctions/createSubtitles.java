package editingFunctions;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class createSubtitles {
	
	SpringLayout _layout = new SpringLayout();
	private JTextField _subText;

	// Method to insert the Title tab into JTabbedFrame.
	public void insertTitlePageTab(final JTabbedPane pane){
		JPanel titlePagePanel = new JPanel();
		pane.addTab("Create Title Page", titlePagePanel);
		setSubtitlesFeatures(titlePagePanel);
	}
	
	private void setSubtitlesFeatures(JPanel panel){
		panel.setLayout(_layout);
	}
	
	private void addTextBox(JPanel panel){
		_subText = new JTextField();
		_subText.setPreferredSize(new Dimension(150,20));
		_subText.setEditable(true);
	}
}
