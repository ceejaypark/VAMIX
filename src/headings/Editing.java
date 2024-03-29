package headings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import editingFunctions.CreateSubtitles;
import editingFunctions.EditCredits;
import editingFunctions.EditTitle;
import editingFunctions.EditWatermark;
import editingFunctions.OverlayAudio;
import editingFunctions.ReplaceAudio;
import editingFunctions.StripAudio;





/** Class that sets out the JTabbedPane in the EDITING header in the main GUI. 
 *  Adds all of the tabs each representing a functionality of the VAMIX program
 *  
 *  @author - Chanjun Park
 */
public class Editing {

	private void addButtonToPane(JPanel panel, JButton button){
		panel.add(button);
		panel.validate();
	}
	// Method to add the GUI components to the main GUI
	public void addGUIComponents(final JFrame frame,final JTabbedPane pane2, final JTabbedPane pane3, final JTabbedPane pane){

		//set up button and panel at the top of the gui for editing
		JButton jbEdit = new JButton("EDITING");
		JPanel lightGreenPanel = new JPanel();
		lightGreenPanel.setOpaque(true);
		lightGreenPanel.setBackground(new Color(141,178,92));
		lightGreenPanel.setPreferredSize(new Dimension(250, 40));
		addButtonToPane(lightGreenPanel,jbEdit);
		frame.getContentPane().add(lightGreenPanel, BorderLayout.CENTER);
		pane.removeAll();
		pane.setPreferredSize(new Dimension(200,270));

		//create instances of each editing class 
		OverlayAudio oa = new OverlayAudio();
		StripAudio sa = new StripAudio();
		ReplaceAudio ra = new ReplaceAudio();

		sa.insertRemoveAudio(pane);
		oa.insertOverlayAudio(pane);
		ra.insertReplaceAudio(pane);

		// Insert the tabs onto the JTabbedPane
		EditTitle et = new EditTitle();
		et.insertTitlePageTab(pane);

		EditCredits ec = new EditCredits();
		ec.insertCreditPageTab(pane);
		
		EditWatermark ew = new EditWatermark();
		ew.insertWaterMarkTab(pane);
		
		CreateSubtitles cs = new CreateSubtitles();
		cs.insertSubsPageTab(pane);




		// Listener for the Editing Button.
		jbEdit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				// Detects and identifies if a JTabbedPane is open or not and opens and closes the Editing JTabbedPane 
				// accordingly
				if (pane2.isVisible()){
					frame.getContentPane().remove(pane2);
					pane2.setVisible(false);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
					pane.setVisible(true);
				}
				else if (pane3.isVisible()){
					frame.getContentPane().remove(pane3);
					pane3.setVisible(false);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
					pane.setVisible(true);
				}
				else if (pane.isVisible()){
					pane.setVisible(false);
					frame.getContentPane().remove(pane);
				}
				else if (pane.isVisible() == false){
					pane.setVisible(true);
					frame.getContentPane().add(pane, BorderLayout.SOUTH);
				}


				frame.pack();
				frame.repaint();
				frame.validate();

			}


		});

	}
	//method to add a tab to the jtabbed pane
	public void addTab(JTabbedPane tabbedPane, String label) {
		JButton button = new JButton(label);
		tabbedPane.addTab(label, button);
	}


}