package graphsVisualisation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;



/**
 * Manages the interactions between the search bar, the search button
 * and the results of the research that are printed on the interface.
 */
public class SearchManager implements ActionListener, Serializable {
	//The main JFrame
	private VisualisationJFrame main_frame;
	
	//The content of the research of the user
	private String search_bar_content;
	
	// Concepts and Terms
	private HashMap<String, Concept> cpt;
	private HashMap<String, Terme> term;
	private HashMap<String, ArrayList<String>> cpt_term;
	
	//DocumentListFrame
	private DocumentListFrame document_list_frame;
	
	/**
	 * Main constructor
	 * @param f: a JFrame instance (the main interface)
	 */
	public SearchManager(VisualisationJFrame f) {
		this.main_frame = f;
	}

	/**
	 * Manages the interactions between the use of the search button and the 
	 * "printing" of the results of the search on the main interface.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
//		int i = 0;
//		boolean trouve = false;
		search_bar_content = main_frame.getSearchBarText();

		try {
			ArrayList<DocumentObject> documents_list = Indexation.searchIndex(search_bar_content,main_frame.getCpt(),main_frame.getTerm(),main_frame.getCpt_term());

			new DocumentListFrame(documents_list);
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println(search_bar_content);
	}
}

