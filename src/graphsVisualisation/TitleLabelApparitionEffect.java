package graphsVisualisation;

import java.io.Serializable;

import javax.swing.JLabel;

/**
 * To make a fade-in apparition for the element "title_label" in the VisualisationJFrame class.
 */
public class TitleLabelApparitionEffect extends Thread implements Serializable{
    // Elements of the interface
    private VisualisationJFrame main_frame;
    private JLabel title_label;

    // Constants for the the loop
    private final byte NB_OPERATIONS = 100;
    private final short TIME_BETWEEN_OPERATION = 60;
    private final short FIRST_TIME_TO_WAIT = 0;

    // Attributes of the elements of the interface
    private int jlabel_min_x;
    private int jlabel_max_x;
    private int width_to_run;

    // Initial position of the label
    private int initial_label_x;
    private int initial_label_y;

    // Current position of the label
    private int title_label_x;

    /**
     * TitleLabelApparitionEffect() is the main constructor of this class. It
     * creates a loop that changes transparency of the title label element in
     * the interface.
     * 
     * @param f
     *            : The main interface.
     */
    public TitleLabelApparitionEffect(VisualisationJFrame f) {
	// Initializations
	this.main_frame = f;

	// Initialization of the label
	if (this.main_frame != null) {
	    this.title_label = f.getTitleLabel();

	    if (title_label != null) {
		this.title_label_x = main_frame.getTitleLabelX();

		// Useful for repositionning the label at the end of the
		// animation
		initial_label_y = main_frame.getTitleLabelY();
		initial_label_x = main_frame.getTitleLabelX();

		// Bidouille
		jlabel_min_x = main_frame.getTitleLabelX() - title_label.getPreferredSize().width * 3;
		jlabel_max_x = main_frame.getTitleLabelX() + title_label.getPreferredSize().width * 3;
		width_to_run = jlabel_max_x - jlabel_min_x;
	    }
	}
    }

    /**
     * Main method for starting the animation thread.
     */
    public void run() {
		if (this.main_frame != null && this.title_label != null) {
		    try {
				Thread.sleep(FIRST_TIME_TO_WAIT);
		    } catch (InterruptedException e1) {
				e1.printStackTrace();
		    }

		    // For every steps, changing the x position value and updating this value
		    for (byte i = 1; i <= NB_OPERATIONS; i++) {
				try {
			    	title_label.setLocation(title_label_x, initial_label_y);
			    	title_label_x = jlabel_max_x - (width_to_run / NB_OPERATIONS * i);

			    	Thread.sleep(TIME_BETWEEN_OPERATION);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
		    }

		    title_label.setLocation(initial_label_x, initial_label_y);
		} else {
		    System.out.println("Erreur de lancement de l'animation pour l'element title_label");
		}
    }
}
