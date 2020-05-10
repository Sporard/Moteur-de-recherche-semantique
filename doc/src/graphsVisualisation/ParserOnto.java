package graphsVisualisation;

import com.google.gson.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
public class ParserOnto {
	
	// Attributs
	//Le chemin d'accès au Json
	private String path; 
	private JsonParser parser;
	private Object obj;
	private JsonObject jsonObj;
	private Gson myGson;

	//Constructeur
	public ParserOnto() {
		this.path = "";
		this.setMyGson(new Gson());
	}
	
	public ParserOnto(String path) {
		this.setPath(path);
		this.setMyGson(new Gson());
		try {
			initialize();
		}catch(IOException e) {
			System.err.println("Error initialize constructor");
		}
	}
	
	
	//Getters / setters
	public String getPath() { return this.path; }
	
	public void setPath(String path) { this.path = path; }
	
	public Object getObj() { return this.obj; }
	
	public void setObj(Object obj) { this.obj = obj ; }
	
	public JsonObject getJsonObj() { return this.jsonObj; } 
	
	public void setJsonObj (JsonObject jsonObj) { this.jsonObj = jsonObj;}
	
	public Gson getMyGson() {return myGson;}

	public void setMyGson(Gson myGson) {this.myGson = myGson;}
	
	//Initialisation
	public void initialize() throws IOException {
		this.parser = new JsonParser();
		try {
			this.obj = parser.parse(new FileReader(this.getPath()));
			this.jsonObj = (JsonObject) obj;
		}catch(IOException e) {
			System.err.print("Error initialize");
		}
	}
//*************************************************************************************************************************

	//@Override
	public String toString() {
		String res = new String();
		res = res + "Path : " + this.getPath() +" \n";
		res = res + "My Gson : "+ this.getMyGson() + "\n";
		return res;
	}
	
	
	//Méthode de parsing
//*************************************************************************************************************************

	//Parsing des concepts dans un dictionnaire avec les clés correspondant au id des concepts et la value l'objet java correspondant
	protected HashMap<String, Concept> lesConcepts(){
		//Le dictionnaire qui contiendra les concepts
		HashMap<String, Concept> dicoConcept = new HashMap<String, Concept>();
		
		//Le dictionnaire qui contiendra les différences
		HashMap<String, Difference> dicoDifference = new HashMap<String, Difference>();
		//L'objet Json contenant la data de tout les concepts du fichier
		JsonObject concept = this.getJsonObj().getAsJsonObject("concepts");
		
		//L'objet Json contenant la data de toutes les différences du fichier
		JsonObject differences = this.getJsonObj().getAsJsonObject("differences");
		
		//On créer un dictionnaire pour chaque différence pour aller plus vite a l'accession
		for(Entry<String, JsonElement> diff : differences.entrySet()) {
			Difference diffEnCours = new Difference();
			diffEnCours = this.getMyGson().fromJson(diff.getValue(), Difference.class);
			diffEnCours.setId(diff.getKey());		
			dicoDifference.put(diff.getKey(), diffEnCours);
		}
		
		//On générer une map contenant les clés et les valeurs des clés de l'objet des concepts 
		for(Entry<String, JsonElement> cpt : concept.entrySet()) {
			
				
			//Le concept qui sera ajouté a la liste
			Concept cptEnCours = new Concept();
			cptEnCours = this.getMyGson().fromJson(cpt.getValue(), Concept.class);
			cptEnCours.setId(cpt.getKey());
				
			//On ajoute les instances des différences correspondantes dans la liste
			for(String ownDiff : cptEnCours.getOwnDifferences()) {
				cptEnCours.getDiff().add(dicoDifference.get(ownDiff));
			}
			for(String inhDiff : cptEnCours.getInheritedDifferences()) {
				cptEnCours.getDiff().add(dicoDifference.get(inhDiff));
			}
			dicoConcept.put(cpt.getKey(), cptEnCours);
		}
		return dicoConcept;
		
	}
		
	
//*************************************************************************************************************************
	
	//Parsing des terme dans un dictionnaire avec comme clé les id des termes et en value l'objet correspondant
	private HashMap<String, Terme> lesTermes(HashMap<String, Concept> dicoCPT){
		//Le dictionnaire final
		HashMap<String, Terme> dicoTerme = new HashMap<String, Terme>();
		//Récupération de la langue
		JsonObject langue = this.getJsonObj().getAsJsonObject("languages");
		
		//Ajout de chaque terme dans le dictionnaire
		for(Entry<String, JsonElement> lg : langue.entrySet()) {
			
			
			for(Entry<String, JsonElement> trm : langue.getAsJsonObject(lg.getKey()).entrySet()) {
				Terme termeEnCours = new Terme();
				termeEnCours = this.getMyGson().fromJson(trm.getValue(), Terme.class);
				
				for(String cpt : termeEnCours.getConcepts()) {
					termeEnCours.getAssociation().add(dicoCPT.get(cpt));
				}
				termeEnCours.setLangue(lg.getKey());
				dicoTerme.put(trm.getKey(), termeEnCours);
			}
		}
		
		return dicoTerme;
	}
	
//*************************************************************************************************************************
	//Création de la liaison entre concept et terme
	protected HashMap<String,ArrayList<String> > cpt_trm(){
		HashMap<String, ArrayList<String>>cpt_term = new HashMap<String,ArrayList<String>>();
		HashMap<String, Concept>cpt = this.lesConcepts();
		HashMap<String, Terme>trm = this.lesTermes(cpt);
		for(Entry<String, Concept> concept : cpt.entrySet()) {
			cpt_term.put(concept.getKey(), new ArrayList<String>());
		}
		for(Entry<String, Terme> terme : trm.entrySet()) {
			for( String idCPT : terme.getValue().getConcepts()) {
				cpt_term.get(idCPT).add(terme.getKey());
			}
		}
		for(Entry<String, ArrayList<String>> graphe_concept_term : cpt_term.entrySet() ) {
			Concept conceptEnCours = cpt.get(graphe_concept_term.getKey());
			for(String isaCPT : conceptEnCours.getIsa()) {
				cpt_term.get(conceptEnCours.getId()).addAll(cpt_term.get(isaCPT));
			}
		}
		
		return cpt_term;
	}
}
