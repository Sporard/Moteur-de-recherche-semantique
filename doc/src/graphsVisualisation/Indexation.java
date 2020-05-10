package graphsVisualisation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;

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

   @SuppressWarnings("unchecked")
public static void searchIndex(String searchString) throws IOException, ParseException{
       System.out.println("Rechercher : '" + searchString + "'");
       Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
       IndexReader indexReader = IndexReader.open(directory);
       IndexSearcher indexSearcher= new IndexSearcher(indexReader);

       Analyzer analyzer = new StandardAnalyzer();
       QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
       Query query = queryParser.parse(searchString);
       Hits hits = indexSearcher.search(query);
       System.out.println("Number of hits: " + hits.length());
       Iterator<Hit> it = hits.iterator();
       while(it.hasNext()){
           Hit hit = it.next();
           Document document = hit.getDocument();
           String path = document.get(FIELD_PATH);
           System.out.println("Hit : " + path);
       }


   }

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
