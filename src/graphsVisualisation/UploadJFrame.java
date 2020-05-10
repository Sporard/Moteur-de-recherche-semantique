package graphsVisualisation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.tree.TreeSelectionModel;

/**
 * An interface to help the user choosing the documents that he has to upload
 */
@SuppressWarnings("unused")
public class UploadJFrame extends JFrame implements Serializable{
	private static final long serialVersionUID = -7498139402373436629L;
	
	//Constants for the jframe
	private final String FRAME_NAME = "Upload de documents";
	private final boolean IS_VISIBLE = true;
	private final boolean IS_RESIZABLE = false;
	private final short WIDTH = 900;
	private final short HEIGHT = 650;	
	
	//Constants for title panel
	private final String TITLE_LABEL_NAME = "Upload de documents";
    private final byte TITLE_SIZE = 25;
    
    //Constants for the labels
    private final String FILE_CHOSEN_PREFIX = "Fichier choisi : ";
    private final String NO_FILE_CHOSEN = "Aucun fichier choisi !";
    private final byte FILE_CHOSEN_MAX_LENGTH = 50;
    
    //Constants for the buttons
    private final String DOWNLOAD_BUTTON_TEXT = "Download !";
    private final String UPLOAD_BUTTON_TEXT = "Upload !";
    private final String CHOOSE_BUTTON_TEXT = "Choisir fichier !";

    //Constants for the paths
    private final String MAIN_ICON_PATH = "ressources/logo.png";
    protected final String DOWNLOADED_DOCUMENTS_PATH = "./ressources/fileToIndex";
	private final String UPLOADED_DOCUMENTS_PATH = "uploaded docs";
    
	//Constants for the font
    private final String TITLE_FONT = "Georgia";
    
    //Constants for the dimension
    private final short TITLE_PANEL_X = WIDTH-2;
    private final short TITLE_PANEL_Y = HEIGHT/10;
    
    private final short DOCUMENTS_PANEL_X = TITLE_PANEL_X-15;
    private final short DOCUMENTS_PANEL_Y = 7*HEIGHT/10;
    
    private final short OPTIONS_PANEL_X = DOCUMENTS_PANEL_X;
    private final short OPTIONS_PANEL_Y = HEIGHT/20;
    
    private final short FILE_CHOSEN_PANEL_X = DOCUMENTS_PANEL_Y;
    private final short FILE_CHOSEN_PANEL_Y = HEIGHT/20;
    
    private final short FILE_CHOSEN_X = OPTIONS_PANEL_X-100;
    private final short FILE_CHOSEN_Y = (OPTIONS_PANEL_Y-10)/2;
    
    private final short TITLE_LABEL_X = TITLE_PANEL_X*3/4;
    private final short TITLE_LABEL_Y = TITLE_PANEL_Y-2;
    
    private final short DOCUMENTS_VIEW_X = DOCUMENTS_PANEL_X;
    private final short DOCUMENTS_VIEW_Y = DOCUMENTS_PANEL_Y-11;
    
    private final short UPLOADED_DOCUMENTS_VIEW_X = (DOCUMENTS_VIEW_X-100)/2;
    private final short UPLOADED_DOCUMENTS_VIEW_Y = DOCUMENTS_VIEW_Y;
    
    private final short DOWNLOADED_DOCUMENTS_VIEW_X = (DOCUMENTS_VIEW_X-100)/2;
    private final short DOWNLOADED_DOCUMENTS_VIEW_Y = DOCUMENTS_VIEW_Y;
    
    //Constants for the message for the Dialogs
    private final String NO_FILE_SELECTED = "Aucun fichier n'a été choisi !";
    private final String IS_NOT_A_FILE = "L'élément sélectionné n'est pas un fichier !";
    private final String DOWNLOAD_SUCCESS = "Le fichier a correctement été téléchargé !";
    private final String UPLOAD_SUCCESS = "Le fichier a correctement été uploadé !";
    private final String DOWNLOAD_FAILURE = "Le fichier n'a pas été téléchargé !";
    private final String UPLOAD_FAILURE = "Le fichier n'a pas été uploadé !";
    private final String NO_WRITING_PERMISSION = "Impossible d'écrire dans le répertoire ciblé !";
    
    //Constants for the file chooser
    private final boolean CAN_SELECT_MULTIPLE_DOCUMENTS = false;
    
    //Constants for the text of the borders
    private final String DOWNLOADED_DOCUMENTS_BORDER_TEXT = "Fichiers téléchargés";
    private final String UPLOADED_DOCUMENTS_BORDER_TEXT = "Fichiers uploadés";
    
    //Constants for the menu
    private final String REMOVE_MENU_ITEM_NAME = "Supprimer";
    
    //Main interface
	private VisualisationJFrame main_frame;
    
    //Upload interface
    private UploadJFrame upload_frame;

    //JButtons
    private JButton choose_button;
    private JButton upload_button;
    private JButton download_button;

    
	//JPanels
	private JPanel title_panel;
	private JPanel documents_panel;
	private JPanel options_panel;
	private JPanel file_chosen_panel;
	
	//JScrollPanes
	private JScrollPane uploaded_documents_view;
	private JScrollPane downloaded_documents_view;
	
	//JTree
	private JTree uploaded_documents_list;
	private JTree downloaded_documents_list;
	
	//JLabels
	private JLabel title_label;
	private JLabel file_chosen_label;
	
	//Fonts
	private Font title_font;
	
	//Borders
	private Border downloaded_documents_view_border;
	private Border uploaded_documents_view_border;
	
	//Dimension
	private Dimension title_label_dimension;
	private Dimension downloaded_documents_view_dimension;
	private Dimension uploaded_documents_view_dimension;
	private Dimension title_panel_dimension;
	private Dimension documents_panel_dimension;
	private Dimension options_panel_dimension;
	private Dimension file_chosen_panel_dimension;
	private Dimension file_chosen_label_dimension;
	
	//Icons
	private ImageIcon main_icon;
	
	//Layouts
	private FlowLayout main_layout;
	
	//FileSystemModel
	private FileSystemModel upload_file_system_model;
	private FileSystemModel download_file_system_model;
	
	//File
	private File last_chosen_file;
	private File file_to_delete;
	
	//Dialogs
	private SuccessDialog success_dialog;
	private ErrorDialog error_dialog;
	
	//JPopupMenus
	private JPopupMenu list_menu;
	
	//JMenuItems
	private JMenuItem remove_menu_item;
	
	// Concepts and Terms
	private HashMap<String, Concept> cpt;
	private HashMap<String, Terme> term;
	private HashMap<String, ArrayList<String>> cpt_term;
	
	/** 
	 * Main constructor
	 *@param main_frame: The main interface
	 */
	public UploadJFrame(VisualisationJFrame main_frame, HashMap<String, Concept> cpt, HashMap<String, Terme> term, HashMap<String, ArrayList<String>> cpt_term) {
		//Initialization
		this.main_frame = main_frame;
		
		// Concepts and Terms
		this.cpt = cpt;
		this.term = term;
		this.cpt_term = cpt_term;
		
		this.upload_frame = this;
		
		this.title_panel = new JPanel();

		this.documents_panel = new JPanel(); 

		this.file_chosen_panel = new JPanel();
		this.options_panel = new JPanel();
		
		//this.uploaded_documents_node = new DefaultMutableTreeNode(UPLOAD_NODE_NAME);
		this.upload_file_system_model = new FileSystemModel(new File(UPLOADED_DOCUMENTS_PATH));
		this.uploaded_documents_list = new JTree(upload_file_system_model);
		this.uploaded_documents_view = new JScrollPane(uploaded_documents_list);
		
		//this.downloaded_documents_node = new DefaultMutableTreeNode(DOWNLOAD_NODE_NAME);
		this.download_file_system_model = new FileSystemModel(new File(DOWNLOADED_DOCUMENTS_PATH));
		this.downloaded_documents_list = new JTree(download_file_system_model);
		this.downloaded_documents_view = new JScrollPane(downloaded_documents_list);
		
		this.choose_button = new JButton(CHOOSE_BUTTON_TEXT);
		this.download_button = new JButton(DOWNLOAD_BUTTON_TEXT);
		this.upload_button = new JButton(UPLOAD_BUTTON_TEXT);
		
		this.title_label = new JLabel(TITLE_LABEL_NAME);
		this.file_chosen_label = new JLabel(FILE_CHOSEN_PREFIX + NO_FILE_CHOSEN);
		
		this.title_font = new Font(TITLE_FONT, Font.PLAIN, TITLE_SIZE);
		
		this.title_label_dimension = new Dimension(TITLE_LABEL_X, TITLE_LABEL_Y);
		this.title_panel_dimension = new Dimension(TITLE_PANEL_X, TITLE_PANEL_Y);
		this.documents_panel_dimension = new Dimension(DOCUMENTS_PANEL_X, DOCUMENTS_PANEL_Y);
		this.options_panel_dimension = new Dimension(OPTIONS_PANEL_X, OPTIONS_PANEL_Y);

		this.file_chosen_panel_dimension = new Dimension(FILE_CHOSEN_PANEL_X, FILE_CHOSEN_PANEL_Y);
		this.file_chosen_label_dimension = new Dimension(FILE_CHOSEN_X, FILE_CHOSEN_Y);
		this.uploaded_documents_view_dimension = new Dimension(UPLOADED_DOCUMENTS_VIEW_X, UPLOADED_DOCUMENTS_VIEW_Y);
		this.downloaded_documents_view_dimension = new Dimension(DOWNLOADED_DOCUMENTS_VIEW_X, DOWNLOADED_DOCUMENTS_VIEW_Y);
		
		this.main_icon = new ImageIcon(MAIN_ICON_PATH);
		
		this.main_layout = new FlowLayout();

		//Initialize borders
		downloaded_documents_view_border = BorderFactory.createTitledBorder(DOWNLOADED_DOCUMENTS_BORDER_TEXT);
		uploaded_documents_view_border = BorderFactory.createTitledBorder(UPLOADED_DOCUMENTS_BORDER_TEXT);
		
		//Settings of the lists
		uploaded_documents_list.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		downloaded_documents_list.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		uploaded_documents_view.setPreferredSize(uploaded_documents_view_dimension);
		uploaded_documents_view.setBorder(uploaded_documents_view_border);
		
		downloaded_documents_view.setPreferredSize(downloaded_documents_view_dimension);
		downloaded_documents_view.setBorder(downloaded_documents_view_border);
		
		//Settings of the title label
		title_panel.setPreferredSize(title_panel_dimension);
		
		//Settings of the documents scrollpane
		documents_panel.setPreferredSize(documents_panel_dimension);
		
		//Settings of the file chosen panel
		file_chosen_panel.setPreferredSize(file_chosen_panel_dimension);
		file_chosen_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Settings of the title panel
		title_panel.setPreferredSize(title_panel_dimension);
		
		title_label.setFont(title_font);
		title_label.setPreferredSize(title_label_dimension);
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Adding elements to the title panel
		title_panel.add(title_label);
		
		//Settings of the documents panel
		documents_panel.setPreferredSize(documents_panel_dimension);
		
		//Settings of the file chosen panel
		file_chosen_panel.setPreferredSize(file_chosen_panel_dimension);
		file_chosen_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Settings of the options panel
		options_panel.setPreferredSize(options_panel_dimension);				

		file_chosen_label.setPreferredSize(file_chosen_label_dimension);
		
		//Adding elements to the title panel
		title_panel.add(title_label);
		
		//Adding elements to the documents panel
		documents_panel.add(uploaded_documents_view);
		documents_panel.add(downloaded_documents_view);	
		
		//Adding elements to the options panel
		file_chosen_panel.add(file_chosen_label);
		
		//Adding element to the file_chosen_panel
		file_chosen_panel.add(file_chosen_label);
		
		//Adding elements to the options panel
		options_panel.add(choose_button);
		options_panel.add(upload_button);
		options_panel.add(download_button);
		
		//Adding elements to the interface
		this.add(title_panel);
		this.add(documents_panel);
		this.add(file_chosen_panel);
		this.add(options_panel);
		this.add(file_chosen_panel);
		
		//Settings of the interface
		this.setLayout(main_layout);
		this.createMenu();
		this.addListeners();
		this.setResizable(IS_RESIZABLE);
		this.setSize(WIDTH, HEIGHT);
		this.setIconImage(main_icon.getImage());
		this.setTitle(FRAME_NAME);
		this.setVisible(IS_VISIBLE);
	}

	/**
	 * To delete a document
	 * @param file_to_delete: the file to delete
	 */
	private boolean deleteDocument(File file_to_delete) {
		if(file_to_delete != null) {
			if(file_to_delete.exists() && file_to_delete.isFile()) {
				return file_to_delete.delete();
			}
		}
		return false;
	}
	
	/**
	 * To create the menu that is displayed when the user right-clicked
	 * on an element of the upload/download list
	 */
	private void createMenu() {
		list_menu = new JPopupMenu();
		remove_menu_item = new JMenuItem(REMOVE_MENU_ITEM_NAME);
		
		list_menu.add(remove_menu_item);
	}
	
	/**
     * To add all the listeners for the interface
     */
	private void addListeners() {
		//Adding a listener for the choose button
		choose_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Choosing file ...");
				
				//Selection of the file to upload
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(CAN_SELECT_MULTIPLE_DOCUMENTS);
				fc.showOpenDialog(choose_button);
				
				//Processing on the selected file

				last_chosen_file = fc.getSelectedFile();
				if(last_chosen_file != null && last_chosen_file.exists()) {
					String selected_file_path = last_chosen_file.getAbsolutePath();
					String printable_path = selected_file_path;
					
					//Get a shorter version of the path if it's too long for the label !
					if(selected_file_path.length() > FILE_CHOSEN_MAX_LENGTH) {
						printable_path = selected_file_path.substring(selected_file_path.length()-FILE_CHOSEN_MAX_LENGTH, selected_file_path.length());
					}
					file_chosen_label.setText(FILE_CHOSEN_PREFIX + "(...) " + printable_path);
				}
			}			
		});

		
		//Adding a listener for the download button
		download_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File download_directory = new File(DOWNLOADED_DOCUMENTS_PATH);
				
				//Creates the repository if it doesn't exist
				if(!download_directory.exists()) {
					download_directory.mkdir();
				}
				
				//Set the chosen file by getting the last clicked element on the upload list
				try{
					last_chosen_file = (File) uploaded_documents_list.getLastSelectedPathComponent();
				}catch(Exception e2) {
					error_dialog = new ErrorDialog(upload_frame, NO_FILE_SELECTED);
				}
				
				//TODO search if the file already exists in the download directory
				
				//Test the validity of the file chosen
				if(last_chosen_file == null) {
					error_dialog = new ErrorDialog(upload_frame, NO_FILE_SELECTED);						
				}else {	
					File download_dir = new File(DOWNLOADED_DOCUMENTS_PATH);
					
					//If the last chosen file is a file
					if(last_chosen_file.isFile()) {
						//If we can write on the download directory
						if(download_dir.canWrite()) {
							try {
								Path dest = new File(DOWNLOADED_DOCUMENTS_PATH + File.separator + last_chosen_file.getName()).toPath();
								Files.copy(last_chosen_file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
								//success_dialog = new SuccessDialog(upload_frame, DOWNLOAD_SUCCESS);
								new DocumentFrame(upload_frame, cpt, term, cpt_term, last_chosen_file);
								//Refresh the interface
								downloaded_documents_list.updateUI();
							} catch (IOException e1) {
								error_dialog = new ErrorDialog(upload_frame, DOWNLOAD_FAILURE);
							}
						}else {
							//If we can't write on the download directory
							error_dialog = new ErrorDialog(upload_frame, NO_WRITING_PERMISSION);
						}
					}else {
						//If the last chosen file is a directory
						error_dialog = new ErrorDialog(upload_frame, IS_NOT_A_FILE);
					}
				}
			}
		});

		
		//Adding a listener for the upload button
		upload_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(last_chosen_file != null) {
					File upload_directory = new File(UPLOADED_DOCUMENTS_PATH);
					
					//Creates the repository if it doesn't exist
					if(!upload_directory.exists()) {
						upload_directory.mkdir();
					}
					
					//If we can write on the upload directory
					if(upload_directory.canWrite()) {
						Path dest = new File(UPLOADED_DOCUMENTS_PATH + File.separator + last_chosen_file.getName()).toPath();
						try {
							Files.copy(last_chosen_file.toPath(),  dest, StandardCopyOption.REPLACE_EXISTING);
							//new DocumentFrame(upload_frame, cpt, term, cpt_term, upload_directory);
							success_dialog = new SuccessDialog(upload_frame, UPLOAD_SUCCESS);
						}catch(Exception e1) {
							error_dialog = new ErrorDialog(upload_frame, UPLOAD_FAILURE);
						}
						uploaded_documents_list.updateUI();
						
					}else {
						//If we can't write on the upload directory
						error_dialog = new ErrorDialog(upload_frame, NO_WRITING_PERMISSION);
					}
				}else {
					error_dialog = new ErrorDialog(upload_frame, NO_FILE_SELECTED);
				}
			}			
		});
		
		//Adding a listener to remove a document by right click on an
		//element of the download list
		downloaded_documents_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (SwingUtilities.isRightMouseButton(e)) {
			    	//Get the selected element with the right click
			    	int row = downloaded_documents_list.getClosestRowForLocation(e.getX(), e.getY());
			    	downloaded_documents_list.setSelectionRow(row);
			        file_to_delete = (File) downloaded_documents_list.getLastSelectedPathComponent();
			    	//System.out.println(file_to_delete.getName());
			        
			    	//To show the menu
			    	list_menu.show(e.getComponent(), e.getX(), e.getY());
			    }
			}
		});
		
		//Adding a listener to remove a document by right click on an
		//element of the upload list
		uploaded_documents_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
			    	//Get the selected element with the right click
			    	int row = uploaded_documents_list.getClosestRowForLocation(e.getX(), e.getY());
			    	uploaded_documents_list.setSelectionRow(row);
			        file_to_delete = (File) uploaded_documents_list.getLastSelectedPathComponent();
			    	System.out.println(file_to_delete.getName());
			        
			    	//To show the menu
			    	list_menu.show(e.getComponent(), e.getX(), e.getY());
			    }
			}
		});
		
		//Adding a listener to react when the user click to delete the file
		remove_menu_item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean was_deleted = deleteDocument(file_to_delete);
				if(was_deleted) {
					//Reloading both lists if the file was deleted
					downloaded_documents_list.updateUI();
					uploaded_documents_list.updateUI();
				}
			}
		});
	}
	
	/**
	 * To get the last chosen file on both lists (upload & download lists)
	 * @return the TreeFile, null if nothing was selected
	 */
	public File getLastChosenFile() {
		return last_chosen_file;
	}
}