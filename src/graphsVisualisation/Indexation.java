package graphsVisualisation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map.Entry;
import java.util.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


@SuppressWarnings("deprecation")
public class Indexation {
	private static final String FILES_TO_INDEX_DIRECTORY = "ressources/fileToIndex";
    private static final String INDEX_DIRECTORY = "ressources/indexDirectory";
    private static final String FIELD_PATH = "path";
    private static final String FIELD_CONTENTS = "contents";

  //************************************************************************************************************************************************************
    public static void createIndex() throws CorruptIndexException, LockObtainFailedException,IOException{
        Analyzer analyzer = new StandardAnalyzer();
        boolean recreatedIndexIfExists = true;
        IndexWriter indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreatedIndexIfExists);
        File dir = new File(FILES_TO_INDEX_DIRECTORY);
        File[] files = dir.listFiles();
        if (files != null) {
            for(File file : files){
                Document document = new Document();
                String path = file.getCanonicalPath();
                document.add(new Field(FIELD_PATH, path,Field.Store.YES, Field.Index.UN_TOKENIZED));
                Reader reader = new FileReader(file);
                document.add(new Field(FIELD_CONTENTS, reader));
                indexWriter.addDocument(document);
            }
        }
        indexWriter.optimize();
        indexWriter.close();
    }
  //************************************************************************************************************************************************************
    @SuppressWarnings("unchecked")
    public static ArrayList<DocumentObject> searchIndex(String searchString, HashMap<String, Concept> cpt,HashMap<String, Terme> trm,HashMap<String, ArrayList<String>> cpt_term) throws IOException, ParseException{
        Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY); 
        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher indexSearcher= new IndexSearcher(indexReader);
        ArrayList<ArrayList<String>> list_research = Indexation.researchOnto(searchString,cpt,trm,cpt_term);
        ArrayList<String> list_key = new ArrayList<String>();
        ArrayList<DocumentObject> list_doc = new ArrayList<DocumentObject>();
        String finalQuery = "";
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
        
       
        ArrayList<String> research_purged = new ArrayList<String>();
        
        for(int i = 0 ; i < list_research.size() ; i++) {
        	for(int j = 0 ; j < list_research.get(i).size() ; j++) {
        		if(!research_purged.contains(list_research.get(i).get(j))) {
        			research_purged.add(list_research.get(i).get(j));
        		}
        		if(Indexation.conceptKey(list_research.get(i).get(j), cpt) != (null) && !list_key.contains(Indexation.conceptKey(list_research.get(i).get(j), cpt))) {
        			list_key.add(Indexation.conceptKey(list_research.get(i).get(j), cpt));
        		}
        	}
        }
        for(int i = 0 ; i < research_purged.size() ; i++) {
        	finalQuery = finalQuery +  research_purged.get(i) + " ";
        }
        System.out.println(finalQuery);
        for(int i = 0 ; i < list_key.size() ; i++) {
        	Concept cptEnCours = cpt.get(list_key.get(i));
        	ArrayList<DocumentObject> list_doc_en_cours = new ArrayList<DocumentObject>(cptEnCours.getListe_doc());
        	for(int j = 0 ; j < list_doc_en_cours.size() ; j++) {
        		if(!list_doc.contains(list_doc_en_cours.get(j))) {
            		list_doc.add(list_doc_en_cours.get(j));
            	}
        	}
        	
        		
        }

        System.out.println(list_doc.toString());
        Query query;
        if(!finalQuery.equals("")) {
        	query = queryParser.parse(finalQuery);
        }else {
        	query = queryParser.parse(searchString);
        }
        
        Hits hits = indexSearcher.search(query);
        Iterator<Hit> it = hits.iterator();
       if(list_doc.isEmpty()) { 
    	   while(it.hasNext()){
        	Hit hit = it.next();
        	Document document = hit.getDocument();
        	String path = document.get(FIELD_PATH);

//        		for(int i = 0 ; i < list_doc.size() ; i++) {
//            		String path_en_cours = list_doc.get(i).getFilepath().toString();
//            		String path_ok =  list_doc.get(i).getFilepath().toString();
//            		System.out.println(path);
//            		System.out.println(path_en_cours);
//            		if(!path_en_cours.equals(path)) {
//            			DocumentObject newDoc = new DocumentObject(path_ok);
//            			list_doc.add(newDoc);
//            			System.out.println(list_doc.size());
//            		}
//        		}
//        	}else{
        		
        		//String[] name = path.split(".*\\");
        		DocumentObject newDoc = new DocumentObject(path,path);
        		//System.out.println(name[0]);
        		list_doc.add(newDoc);
        	}
        }
            
        System.out.println(list_doc.toString());
        return list_doc;
       
        
    }
    //************************************************************************************************************************************************************
    /*
     * 
     * This method find the key of a concept
     * @param the concept you are looking for the key
     * @param the list of the concepts
     * @return the key
     */
    
    public static String conceptKey(String research,HashMap<String, Concept> cpt ) {
    	for(Entry<String,Concept> lesCpts : cpt.entrySet()) {
			//La recherche globale est un concept
			if (lesCpts.getValue().getName().compareToIgnoreCase(research) == 0) {
				return lesCpts.getKey();
				
			}
    	}
    	return null;
    }
    //************************************************************************************************************************************************************
    /*
     * This method find the key of a term
     * @param the term you are looking for the key
     * @param the list of the terms
     * @return the key of the term
     */
    
    public static String termKey(String research,HashMap<String,Terme> trm) {
    	for(Entry<String, Terme> lesTermes : trm.entrySet()) {
			if (lesTermes.getValue().getName().compareToIgnoreCase(research) == 0) {
				return lesTermes.getKey();			
			}
    	}
    	return null;
    }
    //************************************************************************************************************************************************************
   
    /*  
     * This method find the strings corresponding to an "et"
     * @param the array of the research split with " et "
     *  
     */
    public static ArrayList<String>conjonction(String[] split, HashMap<String, Concept> cpt,HashMap<String, Terme> trm,HashMap<String, ArrayList<String>> cpt_term){
		
    	for(int i = 0 ; i < split.length ; i++) {
    		
    	}
    	
    	return null;
    	
    }
    //************************************************************************************************************************************************************
    /*
     * 
     * This method give the intersection of two list
     * @param the first list
     * @param the second list
     * @return the new list corresponding to the intersection of the two list
     * 
     * 
     */
    
   
	public static  ArrayList<ArrayList<String>> intersection(ArrayList<ArrayList<String>> l1,ArrayList<ArrayList<String>> l2){
    	l1.addAll(l2);
		return l1;
    }
    
    //************************************************************************************************************************************************************
    /*
     * @param the item you are searching for
     * @param the map of the concept
     * @param the map of the terms
     * @param the map of the concept with their terms
     * @return the list of the element found with the ontoterminology
     */
    public static  ArrayList<String> searchConcept(String key,HashMap<String, Concept> cpt,HashMap<String, Terme> trm,HashMap<String, ArrayList<String>> cpt_term) {
    	ArrayList<String> liste = new ArrayList();

    	liste.add(cpt.get(key).getName());

    	//On recherche parmis les isa du cpt s'il y en a
    	if(cpt.get(key).getIsa().length != 0  ) {
    		for(String termes : cpt_term.get(key)) {
    			if( !liste.contains(trm.get(termes).getName())) {
    				liste.add(0,trm.get(termes).getName());
    			}
    			
    			
    		}
    		for(String isa : cpt.get(key).getIsa()) {
    			searchConcept(isa,cpt,trm,cpt_term);
    		}
    	}else {
    		return liste;
    	}
		
		
    	
    	
		return liste;
    	
    }
    
    //************************************************************************************************************************************************************
    /*
     * @param the item you are looking for 
     * @param the map of the concept
     * @param the map of the terms
     * @param the map of the concept with their terms
     * @return the list of all the element corresponding thanks to the ontoterminology
     * 
     */
    
    public static  ArrayList<String> searchTerm(String research,HashMap<String, Concept> cpt,HashMap<String, Terme> trm,HashMap<String, ArrayList<String>> cpt_term){
    	ArrayList<String> liste = new ArrayList<String>();
    
    	for( String c : trm.get(research).getConcepts()) {
    		liste.addAll(searchConcept(c,cpt,trm,cpt_term));
    	}
    	
    	return liste;

    }
    
    //************************************************************************************************************************************************************
    /*
     * 
     * Research in the ontoterminology
     * @param research
     * @param cpt
     * @param trm
     * @param cpt_term
     * @return List of the list with the new term and concept
     */
    public static  ArrayList<ArrayList<String>> researchOnto(String research,HashMap<String, Concept> cpt,HashMap<String, Terme> trm,HashMap<String, ArrayList<String>> cpt_term) {
    	String[] res = research.trim().split("\\s+");
    	String[] et  = research.trim().split(" et ");
    	String[] ou  = research.trim().split(" ou ");
    	research = research.trim();
    	//System.out.println(research);
    	//La liste contenant les termes et les concepts de la recherche   	
		ArrayList<ArrayList<String>> liste_recherche = new ArrayList<ArrayList<String>>();
		//Recherche parmis les concepts
		String keyCResearch = Indexation.conceptKey(research, cpt);
		
		//La recherche globale est un concept
		if(!(keyCResearch== null)) {
//			System.out.println("clé cpt");
//			System.out.println(keyCResearch);
			if(!cpt_term.get(keyCResearch).isEmpty()) {
				ArrayList<String> liste_terme = new ArrayList<String>(cpt_term.get(keyCResearch));
				for(int k = 0 ; k < liste_terme.size() ; k++) {
					liste_terme.set(k, trm.get(liste_terme.get(k)).getName());		
					}
					liste_terme.add(cpt.get(keyCResearch).getName());
					if(!liste_recherche.contains(liste_terme)) {
						liste_recherche.add(liste_terme);
					}
						
				} 
			 
			
		}
		//on recherche dans la recherche split
		for (int i  = 0 ; i < res.length ; i ++) {
			String keyCSplit = Indexation.conceptKey(res[i], cpt);
			if(keyCSplit != null) {
				if(!liste_recherche.contains(Indexation.searchConcept(keyCSplit,cpt,trm, cpt_term))) {
    				liste_recherche.add(Indexation.searchConcept(keyCSplit,cpt,trm, cpt_term));
				}
			}
    		
    				
    	}

		//On recherche dans les termes
		String keyTResearch = Indexation.termKey(research, trm);
		
		//for(Entry<String, Terme> lesTermes : trm.entrySet()) {
		if(!(keyTResearch == null)) {
//			System.out.println("clé terme");
//			System.out.println(Indexation.termKey(keyTResearch, trm));
			ArrayList<String> liste_termes;
			for( String c : trm.get(keyTResearch).getConcepts() ){
				liste_termes = new ArrayList<String>(cpt_term.get(c));
				for(int k = 0; k < liste_termes.size(); k++) {
					liste_termes.set(k, trm.get(liste_termes.get(k)).getName());
				}
				if(!liste_recherche.contains(liste_termes)) {
					liste_recherche.add(liste_termes);
				}
					
				if(!liste_recherche.contains(searchConcept(c,cpt,trm,cpt_term))) {
					liste_recherche.add(searchConcept(c,cpt,trm,cpt_term));
				}
					
			}
				
			
		}
		//On regarde la recherche split
		for (int i  = 0 ; i < res.length ; i ++) {
			String keyTSplit = Indexation.termKey(res[i], trm);
			
			if(keyTSplit != null ) {
				if(!liste_recherche.contains(Indexation.searchTerm(keyTSplit,cpt,trm, cpt_term))) {
					liste_recherche.add(Indexation.searchTerm(keyTSplit,cpt,trm, cpt_term));
				}
			}
			
				
		}
		
		
		//Conjonction de terme / concept
		if(et.length >= 1 ) {
			for(int j = 0 ; j < et.length - 1 ; j++) {
				ArrayList<ArrayList<String>>intersec = Indexation.intersection(Indexation.researchOnto(et[j],cpt,trm,cpt_term),Indexation.researchOnto(et[j+1],cpt,trm,cpt_term));
				
				if(!liste_recherche.contains(intersec)) {
					liste_recherche.addAll(intersec);
				}

			
			}	
		}
		//Disjonction de terme / concept
		if(ou.length >= 1 ) {
			for(int j = 0 ; j < ou.length - 1 ; j ++) {
				if(!liste_recherche.contains(Indexation.researchOnto(ou[j], cpt, trm, cpt_term))) {
					liste_recherche.addAll(j,Indexation.researchOnto(ou[j], cpt, trm, cpt_term));
				}
				
			}
		}
		
    		
//    	System.out.println("ICI-------------------------");
//    	System.out.println(liste_recherche.toString());
    	return liste_recherche;
    }
    
    //************************************************************************************************************************************************************
    public static void pdfToText(String docName){
        File f = new File(FILES_TO_INDEX_DIRECTORY+ "/" + docName+".txt");
        if (f.exists()){
            System.out.println("Le fichier existe déjà!!!");
        }
        try{
            //extract text using library
            PDDocument doc = PDDocument.load(new File(FILES_TO_INDEX_DIRECTORY+ "/"+ docName + ".pdf"));
            String text = new PDFTextStripper().getText(doc);

            //write the content to text file
            PrintWriter pw = new PrintWriter(new FileWriter( FILES_TO_INDEX_DIRECTORY+ "/" + docName + ".txt"));
            for(int i = 0; i< 10; i++){
                pw.write(text);
            }
            pw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
   }

}
