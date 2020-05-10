package graphsVisualisation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphsMain implements Serializable{
	private static final long serialVersionUID = 6810747824461674577L;

	/**
	 * The entry point of the program
	 * @param args: Arguments of the main method
	 */
	public static void main(String[] args) {
		try {
			Indexation.createIndex();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ParserOnto parser = new ParserOnto("./ressources//clean_data.json");
		File filename = new File("./ressources/concept_doc.txt");
		HashMap<String, Concept> cpt = new HashMap<String, Concept>();


		if(!filename.exists()) {
			try {
				cpt = parser.lesConcepts();;
				filename.createNewFile();
				Sauvegarde.writeMap(cpt, filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { 
			cpt = Sauvegarde.readMap(filename);
		}
		HashMap<String, Terme> term = parser.lesTermes(cpt);
		HashMap<String, ArrayList<String>> cpt_term = parser.cpt_trm();
		new VisualisationJFrame(cpt, term, cpt_term);
		

	}
}
