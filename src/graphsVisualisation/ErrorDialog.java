package graphsVisualisation;

import java.awt.Dialog;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * To print an error message when an action on the interface is incorrect
 */
public class ErrorDialog extends Dialog implements Serializable{
	private static final long serialVersionUID = -7391329468980731762L;
	private final String DIALOG_TITLE = "Erreur !";
	
	/**
	 * Main constructor
	 * @param parent: The parent component of the dialog
	 * @param message: The message to print on the dialog
	 */
	public ErrorDialog(JFrame parent, String message) {
		super(parent);
		
		JOptionPane.showMessageDialog(this, message, DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
	}
}
