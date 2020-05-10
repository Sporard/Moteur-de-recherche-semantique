package graphsVisualisation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//Classe repr�sentant les concepts des ontoterminologies
public class Concept implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Attributs
	private String id;
	private String name;
	private String urlImage;
	private String[] ownDifferences;
	private String[] inheritedDifferences;
	private String[] isa;
	private ArrayList<Difference> diff;
	private ArrayList<Concept> isAID;
	private ArrayList<DocumentObject> liste_doc;
//	private HashMap<Concept, ArrayList<Document>> cptDocs;
//	private File file_doc;

	//Constructeurs	
	public Concept() {
		this.setName("");
		this.diff = new ArrayList<Difference>();
		this.isAID = new ArrayList<Concept>();
		this.liste_doc = new ArrayList<DocumentObject>();
//		this.file_doc = new File("./ressources/concept_doc.txt");
//		this.cptDocs = Sauvegarde.readMap(file_doc) == null ? new HashMap<Concept, ArrayList<Document>>() : Sauvegarde.readMap(file_doc);
	}

	//Getters / Setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String[] getOwnDifferences() {
		return ownDifferences;
	}

	public void setOwnDifferences(String[] ownDifferences) {
		this.ownDifferences = ownDifferences;
	}

	public String[] getInheritedDifferences() {
		return inheritedDifferences;
	}

	public void setInheritedDifferences(String[] inheritedDifferences) {
		this.inheritedDifferences = inheritedDifferences;
	}

	public String[] getIsa() {
		return isa;
	}

	public void setIsa(String[] isa) {
		this.isa = isa;
	}

	public ArrayList<Difference> getDiff() {
		return diff;
	}

	public void setDiff(ArrayList<Difference> diff) {
		this.diff = diff;
	}

	public ArrayList<Concept> getIsAID() {
		return isAID;
	}

	public void setIsAID(ArrayList<Concept> isAID) {
		this.isAID = isAID;
	}
	
	//@Override
	public String toString() {
		return this.getName();
	}
	public String toStringComplet() {
		String res = new String();
		res =  res +"Name: " + this.getName() +"\n";
		res = res +"Url: " + this.getUrlImage() +"\n";
		res = res +"Caractéristiques essentielles: " ;
		for(int i = 0; i < getDiff().size(); i++) {
			res = res + ", id: " + diff.toString();
		}
		res = res + "\nIs A : " ;
		for(int j = 0 ; j < this.getIsAID().size(); j++) {
			res = res + this.getIsAID().get(j).toString() + ", ";
		}
		res = res +"\n" +" " + this.liste_doc.toString();
		return res;
	}

	public ArrayList<DocumentObject> getListe_doc() {
		return liste_doc;
	}

	public void setListe_doc(ArrayList<DocumentObject> liste_doc) {
		this.liste_doc = liste_doc;
	}
}
