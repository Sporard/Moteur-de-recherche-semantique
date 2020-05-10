package graphsVisualisation;

import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * To show a dialog when the user clicks on the help menu item.
 */
public class HelpDialog extends JDialog implements Serializable {
    private static final long serialVersionUID = 4504363060864217659L;

    // JFrames
    @SuppressWarnings("unused")
    private VisualisationJFrame f;

    // JPanels
    private JPanel main_panel;

    // JLabels
    private JLabel work_in_progress_label;

    // GridLayouts
    private GridLayout main_layout;

    // Constants for the GridLayouts
    private final byte MAIN_LAYOUT_NB_COLUMNS = 1;
    private final byte MAIN_LAYOUT_NB_LINES = 1;

    // Constants for the JLabels
    private final String WORK_IN_PROGRESS_LABEL_CONTENT = "En cours de développement !";

    // Constants for the settings of the dialog
    private final short WIDTH = 300;
    private final short HEIGHT = 180;
    private final String HELP_DIALOG_NAME = "Aide";

    /**
     * Main Constructor that creates all the elements relative to the dialog.
     * @param f: The main frame
     */
    public HelpDialog(VisualisationJFrame f) {
    	// Instantiation of the elements of the dialog
    	this.f = f;
    	this.main_panel = new JPanel();
    	this.work_in_progress_label = new JLabel(WORK_IN_PROGRESS_LABEL_CONTENT);
    	this.main_layout = new GridLayout(MAIN_LAYOUT_NB_COLUMNS, MAIN_LAYOUT_NB_LINES);

    	// Adding elements to the main panel
    	main_panel.add(work_in_progress_label);

    	// Adding elements to the dialog
    	this.add(main_panel);

    	// Settings of the labels
    	work_in_progress_label.setVerticalAlignment(JLabel.CENTER);

    	// Settings of the interface
    	this.setLayout(main_layout);
    	this.setName(HELP_DIALOG_NAME);
    	this.setSize(WIDTH, HEIGHT);
    	this.setTitle(HELP_DIALOG_NAME);
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	this.setVisible(true);
    }
}
