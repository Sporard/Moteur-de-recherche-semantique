
package graphsVisualisation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

 @SuppressWarnings("unchecked")
public class Sauvegarde implements Serializable{
	private static final long serialVersionUID = 1L;
	public static void write(ArrayList<DocumentObject> list, File filename){

	    try{
	    	filename.delete();
	    	filename.createNewFile();
	    	
		    File fileOne  = filename;
		    FileOutputStream fos = new FileOutputStream(fileOne, false);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        
	        oos.writeObject(list);
	        oos.flush();
	        oos.close();
	        fos.close();
	    }catch(Exception e){ e.printStackTrace();}
	    
	}
	
	public static void writeMap(HashMap<String, Concept> cptDocs, File filename){

	    try{
	    	filename.delete();
	    	filename.createNewFile();
	    	
		    File fileOne  = filename;
		    FileOutputStream fos = new FileOutputStream(fileOne, false);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        
	        oos.writeObject(cptDocs);
	        oos.flush();
	        oos.close();
	        fos.close();
	    }catch(Exception e){ e.printStackTrace();}
	    
	}
	
	
	public static ArrayList<DocumentObject> read(File filename){
		if(!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}else {
			try{
				File toRead = filename;
				FileInputStream fis = new FileInputStream(toRead);
				ObjectInputStream ois = new ObjectInputStream(fis);

				ArrayList<DocumentObject> listTemp = new ArrayList<DocumentObject>();
				try {
					listTemp = (ArrayList<DocumentObject>) ois.readObject();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				ois.close();
				fis.close();
				//print All data in MAP
//				for(int i = 0; i< listTemp.size();i++){
//					System.out.println(listTemp.get(i).getName()+" \n"+ listTemp.get(i).getFilepath() + " \n" + listTemp.get(i).getCpt().toString()+ "\n");
//				}
				return listTemp;
			}catch(Exception e){
				e.printStackTrace();
				return null;}
		}
	}
	
	public static HashMap<String, Concept> readMap(File filename){
		if(!filename.exists()) {
			try {
				filename.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}else {
			try{
				File toRead = filename;
				FileInputStream fis = new FileInputStream(toRead);
				ObjectInputStream ois = new ObjectInputStream(fis);

				HashMap<String, Concept> mapTemp = new HashMap<String, Concept>();
				try {
					mapTemp = (HashMap<String, Concept>) ois.readObject();
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				ois.close();
				fis.close();
				//print All data in MAP
//				for(int i = 0; i< listTemp.size();i++){
//					System.out.println(listTemp.get(i).getName()+" \n"+ listTemp.get(i).getFilepath() + " \n" + listTemp.get(i).getCpt().toString()+ "\n");
//				}
				return mapTemp;
			}catch(Exception e){
				e.printStackTrace();
				return null;}
		}
	}
	

}
