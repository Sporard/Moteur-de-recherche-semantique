package graphsVisualisation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * To manage every option of the interface, for example the animation of
 * the title when the interface launches.
 */
public class OptionsManager implements Serializable{
	//The main frame
	private VisualisationJFrame main_frame;
	
	//Options
	private boolean title_animation_enabled;
	
	//To read options from a file
	private File options_file;
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private BufferedWriter bw;

	//Constants
	private final boolean APPEND_TO_FILE = true;
	private final boolean OVERWRITE_FILE = false;
	private final String OPTIONS_FILE_PATH = "ressources/options.txt";
	private final String FILE_READING_ERROR = "Impossible de lire le fichier pour les options !";
	private final String FILE_WRITING_ERROR = "Impossible d'écrire dans le fichier pour les options !";

	/**
	 * OptionsManager() is the main constructor of this class.
	 * @param f: The main interface
	 */
	public OptionsManager(VisualisationJFrame f) {
		this.main_frame = f;
		this.options_file = new File(OPTIONS_FILE_PATH);
		
		try {
			if(options_file.exists()){
				this.fr = new FileReader(options_file);
				this.fw = new FileWriter(options_file, APPEND_TO_FILE);
			}else {
				options_file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.br = new BufferedReader(fr);
		this.bw = new BufferedWriter(fw);
				
		this.getOptionsFromFile();
		this.manageOptions();
	}
	
	/**
	 * manageOptions() permits to manage the options in function of the options specified in the
	 * attributes of the instance of this class
	 */
	private void manageOptions() {
		if(title_animation_enabled) {
			main_frame.getTitleLabelEffect().start();
		}
			
	}

	/**
	 * To read all the options from the file 'options_file'
	 */
	private void getOptionsFromFile() {		
		if(options_file.canRead()) {
			try {
				String line;
				int nb_line = 1;
			
				while((line=br.readLine()) != null) {
				
					//Defining the options
					switch(nb_line) {
						//For the animation of the title label
						case 1:
							boolean b = line.trim().equals("true");
							if(b) {
								this.title_animation_enabled = true;
								this.main_frame.setTitleAnimationState(true);
							}else {
								this.title_animation_enabled = false;
								this.main_frame.setTitleAnimationState(false);
							}
						
							break;	
					}
				
					//For the next iteration of the loop
					line = br.readLine();
					nb_line++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println(FILE_READING_ERROR);
		}
	}
	
	/**
	 * To save the options of the interface in a file 
	 */
	protected void saveOptions() {	
		//Creates the file if it doesn't exist
		if(options_file.canWrite()) {		
			try {
				//Overwriting the file
				this.fw = new FileWriter(options_file, OVERWRITE_FILE);
			
				//For the animation of the title label
				if(title_animation_enabled) {
					bw.write("true");
					bw.newLine();
					bw.flush();
				}else {
					bw.write("false");
					bw.newLine();
					bw.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.err.println(FILE_WRITING_ERROR);
		}
	}
	
	/**
	 * A getter to access to the state of the option for the animation title
	 * @return boolean : The state of the option
	 */
	protected boolean isTitleAnimationEnabled() {
		return title_animation_enabled;
	}

	/**
	 * A setter to set the state of the option for the animation title
	 * @param is_enabled : The state of the option
	 */
	protected void setTitleAnimationEnabled(boolean is_enabled) {
		this.title_animation_enabled = is_enabled;
	}
}
