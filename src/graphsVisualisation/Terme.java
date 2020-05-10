package graphsVisualisation;

import java.io.Serializable;
import java.util.ArrayList;

//Classe représentant les termes des ontoterminologies
public class Terme implements Serializable{
	
	//Attributs
	private String name;
	private String status;
	private String definition;
	private String source;
	private String langue;
	private ArrayList<String> notes;
	private String context;
	private String[] concepts;
	private ArrayList<String> derivate;
	private ArrayList<Concept> association;
	
	
	//Constructeurs
	public Terme() {
		this.setName("void");
		this.setAssociation(new ArrayList<Concept>());
		this.setDerivate(new ArrayList<String>());
	}
	
	//Getters / Setters
	

	//@override
	
	public String getLangue() {
		return langue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String[] getConcepts() {
		return concepts;
	}

	public void setConcepts(String[] concepts) {
		this.concepts = concepts;
	}

	public ArrayList<String> getDerivate() {
		return derivate;
	}

	public void setDerivate(ArrayList<String> derivate) {
		this.derivate = derivate;
	}

	public ArrayList<Concept> getAssociation() {
		return association;
	}

	public void setAssociation(ArrayList<Concept> association) {
		this.association = association;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String toString() {
		String res = new String();
		 res = "Nom: " +this.getName() +"\n" +
		"Statut: " + this.getStatus() +"\n" + 
		"Definition: " + this.getDefinition() +"\n" + 
		"Source: " + this.getSource() + "\n" +
		"Langue: " + this.getLangue() + "\n" ;
		if(this.getContext() != null) {
			 res =  res + "Context: " + this.getContext() +"\n";
		}
		if(this.getNotes() != null) {
			res = res + "Notes: " + this.getNotes().toString() + "\n" ;
		}
		res = res + "Concepts: " ;
		for(int i = 0 ; i < this.getAssociation().size(); i++) {
			res = res + this.getAssociation().get(i).getName() +", ";
		}
		
		return  res ;
	}

	
}
