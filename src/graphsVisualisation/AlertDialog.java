package graphsVisualisation;

import java.awt.Dialog;
import java.io.File;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class AlertDialog extends Dialog implements Serializable{
	private static final long serialVersionUID = 2963292106536743590L;
	private final String TITLE_TEXT = "Alerte !";
    protected final String DOWNLOADED_DOCUMENTS_PATH = "downloaded docs";
    private final String DOWNLOAD_FILE_REPLACEMENT = "Le fichier existe déjà, voulez-vous le remplacer ?";
	
    private int dialog_button;

	public AlertDialog(UploadJFrame parent, String message) {
		super(parent);
		
		dialog_button = JOptionPane.YES_NO_OPTION;
		JOptionPane.showConfirmDialog(parent,
				message, TITLE_TEXT, dialog_button);
		
		if(message.equals(DOWNLOAD_FILE_REPLACEMENT)) {
			if(dialog_button == JOptionPane.YES_OPTION) {
				File last_chosen_file = parent.getLastChosenFile();
				//TODO if last chosen file is null !
				File file_to_delete = new File(DOWNLOADED_DOCUMENTS_PATH+File.separator+last_chosen_file.getName());
				file_to_delete.delete();
			}else {
				remove(dialog_button);
			}
		}
	}
	
	/**
	 * To get the choice of the user (yes or no)
	 * @return The integer corresponding to the user choice
	 */
	public int getChoice() {
		return dialog_button;
	}
}
