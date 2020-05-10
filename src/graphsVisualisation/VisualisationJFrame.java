package graphsVisualisation;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * To show an interface to the user to help him browse the results of
 * the indexed documents of our semantic search engine.
 */

@SuppressWarnings("unused")
public class VisualisationJFrame extends JFrame implements ActionListener, Serializable {


	private static final long serialVersionUID = 4882815445311467209L;

	// The main frame
	private VisualisationJFrame main_frame;

	// JPanels
	private JPanel title_panel;
	private JPanel search_panel;
	private JPanel results_panel;
	private JPanel concepts_panel;
	private JPanel terms_panel;
	private JPanel documents_panel;

	// JMenuBars
	private JMenuBar main_menubar;

	// JMenus
	private JMenu menu_file;
	private JMenu menu_edit;
	private JMenu menu_options;
	private JMenu menu_help;

	// JLabels
	private JLabel title_label;
	private JLabel terms_label;
	private JLabel documents_label;

	// JMenuItems
	private JMenuItem menu_item_quit;
	private JMenuItem menu_item_upload;
	private JMenuItem menu_item_clear;
	private JMenuItem menu_item_help;

	// JCheckBoxMenuItem
	private JCheckBoxMenuItem menu_item_title_animation;

	// MenuItems
	private MenuItem tray_menu_item_quit;

	// JTextFields
	private JTextField search_bar;

	// JButtons
	private JButton search_button;

	// JTree
	private JTree concepts_tree;

	// JScrollPane
	private JScrollPane concepts_tree_view;
	private JScrollPane terms_view;
	private JScrollPane documents_view;

	// DefaultMutableTreeNode
	private DefaultMutableTreeNode root_node;

	// Font
	private Font title_font;
	private Font list_elements_font;

	// JList
	private JList<String> term_list;
	private JList<DocumentObject> documents_list;

	// DefaultListModel
	private DefaultListModel<String> term_list_model;
	private DefaultListModel<DocumentObject> documents_list_model;

	// Dimensions
	private Dimension search_panel_dimension;
	private Dimension search_bar_dimension;
	private Dimension search_button_dimension;
	private Dimension results_panel_dimension;
	private Dimension concepts_panel_dimension;
	private Dimension terms_panel_dimension;
	private Dimension terms_view_dimension;
	private Dimension documents_panel_dimension;
	private Dimension documents_view_dimension;
	private Dimension title_label_dimension;
	private Dimension terms_label_dimension;
	private Dimension documents_label_dimension;
	private Dimension concepts_tree_dimension;

	// Borders
	private Border search_panel_border;
	private Border results_panel_border;
	private Border concepts_panel_border;
	private Border terms_panel_border;
	private Border list_terms_border;
	private Border documents_border;

	// Layouts
	private FlowLayout main_layout;
	private GridBagLayout results_panel_layout;

	// GridBadConstraints
	private GridBagConstraints results_panel_gbc;

	// Icons
	private ImageIcon main_icon;

	// PopupMenus
	private PopupMenu main_popup;

	// Trays
	private SystemTray system_tray;

	// TrayIcons
	private TrayIcon main_tray_icon;

	// Miscellaneous classes
	private SearchManager search_manager;
	private WindowCloser main_window_closer;
	private TitleLabelApparitionEffect title_label_effect;
	private OptionsManager options_manager;
	private DocumentRenderer doc_renderer;
	private DocumentObject selected_doc;
	private DocumentListFrame document_list_frame;

	// Constants for the names of the menus
	private final String MENU_FILE_NAME = "Fichier";
	private final String MENU_EDIT_NAME = "Edition";
	private final String MENU_OPTIONS_NAME = "Options";
	private final String MENU_HELP_NAME = "Aide";

	// Constants for the names of the items of the menus
	private final String QUIT_OPTION_NAME = "Quitter";
	private final String UPLOAD_OPTION_NAME = "Upload de documents";
	private final String CLEAR_OPTION_NAME = "Nettoyer la recherche";
	private final String TITLE_ANIMATION_OPTION_NAME = "Animation du titre";
	private final String HELP_OPTION_NAME = "Documentation";

	// Constants for the name of the elements of the main panel
	private final String SEARCH_BAR_CONTENT = "";
	private final String SEARCH_BUTTON_NAME = "Rechercher";

	// Constants for the settings of the interface
	private final String MAIN_ICON_PATH = "ressources/logo.png";
	private final String INTERFACE_NAME = "Moteur de recherche sémantique";

	// Constants for the font
	private final String TITLE_FONT = "Georgia";

	// Constants for the interface
	private final short WIDTH = 900;
	private final short HEIGHT = 650;

	private final Component WINDOW_LOCATION = null;
	private final boolean IS_RESIZABLE = false;
	private final boolean IS_VISIBLE = true;

	// Constants for the dimension of the panels and their sub-elements
	private final short SEARCH_PANEL_X = WIDTH - 18;
	private final short SEARCH_PANEL_Y = (short) (HEIGHT * 0.06);

	private final short SEARCH_BAR_X = (short) ((SEARCH_PANEL_X - 20) * 0.85);
	private final short SEARCH_BAR_Y = SEARCH_PANEL_Y - 18;

	private final short SEARCH_BUTTON_X = (short) ((SEARCH_PANEL_X - 20) * 0.15);
	private final short SEARCH_BUTTON_Y = SEARCH_PANEL_Y - 11;

	private final short RESULTS_PANEL_X = WIDTH - 18;
	private final short RESULTS_PANEL_Y = HEIGHT - 151;

	private final short CONCEPTS_PANEL_X = (RESULTS_PANEL_X - 2) / 2;
	private final short CONCEPTS_PANEL_Y = RESULTS_PANEL_Y - 2;

	private final short CONCEPTS_TREE_VIEW_X = CONCEPTS_PANEL_X + 1;
	private final short CONCEPTS_TREE_VIEW_Y = CONCEPTS_PANEL_Y - 8;

	private final short TERMS_PANEL_X = CONCEPTS_PANEL_X;
	private final short TERMS_PANEL_Y = (CONCEPTS_PANEL_Y - 2) / 2;

	private final short DOCUMENTS_PANEL_X = CONCEPTS_PANEL_X;
	private final short DOCUMENTS_PANEL_Y = (CONCEPTS_PANEL_Y - 2) / 2;

	private final short TITLE_LABEL_X = RESULTS_PANEL_X * 3 / 4;
	private final short TITLE_LABEL_Y = 28;

	private final short TERMS_LABEL_X = TERMS_PANEL_X;
	private final short TERMS_LABEL_Y = 15;

	private final short TERMS_VIEW_X = TERMS_PANEL_X + 3;
	private final short TERMS_VIEW_Y = TERMS_PANEL_Y - TITLE_LABEL_Y - 3;

	private final short DOCUMENTS_LABEL_X = DOCUMENTS_PANEL_X;
	private final short DOCUMENTS_LABEL_Y = 15;

	private final short DOCUMENTS_VIEW_X = DOCUMENTS_PANEL_X + 3;
	private final short DOCUMENTS_VIEW_Y = DOCUMENTS_PANEL_Y - TITLE_LABEL_Y + 3;

	// Constants for the dimensions of the borders of the panels
	private final byte CONCEPTS_PANEL_BORDER_TOP = 0;
	private final byte CONCEPTS_PANEL_BORDER_LEFT = 0;
	private final byte CONCEPTS_PANEL_BORDER_BOTTOM = 0;
	private final byte CONCEPTS_PANEL_BORDER_RIGHT = 1;

	private final byte TERMS_PANEL_BORDER_TOP = 0;
	private final byte TERMS_PANEL_BORDER_LEFT = 0;
	private final byte TERMS_PANEL_BORDER_BOTTOM = 1;
	private final byte TERMS_PANEL_BORDER_RIGHT = 0;

	private final byte LIST_TERMS_BORDER_TOP = 0;
	private final byte LIST_TERMS_BORDER_LEFT = 0;
	private final byte LIST_TERMS_BORDER_BOTTOM = 1;
	private final byte LIST_TERMS_BORDER_RIGHT = 0;

	private final byte DOCUMENTS_BORDER_TOP = 0;
	private final byte DOCUMENTS_BORDER_LEFT = 0;
	private final byte DOCUMENTS_BORDER_BOTTOM = 1;
	private final byte DOCUMENTS_BORDER_RIGHT = 0;

	// Constant for the "title label"
	private final String TITLE_NAME = "Moteur de Recherche Sémantique";
	private final byte TITLE_LABEL_SIZE = 25;

	// Constants for the font of the elements in lists/trees
	private final byte ELEMENTS_FONT_SIZE = 16;

	// Constants for the labels in results_panel
	private final String ROOT_NODE_NAME = "Liste des concepts"; 
	private final String TERMS_LABEL_NAME = "Liste des termes";
	private final String DOCUMENTS_LABEL_NAME = "Documents";
	private final String INDEXED_FILE_PATH_NAME = "downloaded docs";

	// Constants for the errors
	private final String TRAY_ICON_ADDING_ERROR = "L'icône de notification n'a pas pu être créée !";

	// Concepts and Terms
	private HashMap<String, Concept> cpt;
	private HashMap<String, Terme> term;
	private HashMap<String, ArrayList<String>> cpt_term;
	
	public HashMap<String, Concept> getCpt() {
		return cpt;
	}

	public void setCpt(HashMap<String, Concept> cpt) {
		this.cpt = cpt;
	}

	public HashMap<String, Terme> getTerm() {
		return term;
	}

	public void setTerm(HashMap<String, Terme> term) {
		this.term = term;
	}

	public HashMap<String, ArrayList<String>> getCpt_term() {
		return cpt_term;
	}

	public void setCpt_term(HashMap<String, ArrayList<String>> cpt_term) {
		this.cpt_term = cpt_term;
	}

	private String conceptId;
	
	/**
	 * Main constructor of this class. This constructor creates all the interface.
	 */
	public VisualisationJFrame(HashMap<String, Concept> cpt, HashMap<String, Terme> term, HashMap<String, ArrayList<String>> cpt_term) {
		// Concepts and Terms
		this.cpt = cpt;
		this.term = term;
		this.cpt_term = cpt_term;
		
		// Initialize frame
		this.main_frame = this;

		// Initialize font
		this.title_font = new Font(TITLE_FONT, Font.PLAIN, TITLE_LABEL_SIZE);
		this.list_elements_font = new Font(TITLE_FONT, Font.PLAIN, ELEMENTS_FONT_SIZE);

		// Initialize dimensions
		this.search_panel_dimension = new Dimension(SEARCH_PANEL_X, SEARCH_PANEL_Y);
		this.search_bar_dimension = new Dimension(SEARCH_BAR_X, SEARCH_BAR_Y);
		this.search_button_dimension = new Dimension(SEARCH_BUTTON_X, SEARCH_BUTTON_Y);
		this.results_panel_dimension = new Dimension(RESULTS_PANEL_X, RESULTS_PANEL_Y);
		this.concepts_panel_dimension = new Dimension(CONCEPTS_PANEL_X, CONCEPTS_PANEL_Y);
		this.terms_panel_dimension = new Dimension(TERMS_PANEL_X, TERMS_PANEL_Y);
		this.terms_view_dimension = new Dimension(TERMS_VIEW_X, TERMS_VIEW_Y);
		this.documents_panel_dimension = new Dimension(DOCUMENTS_PANEL_X, DOCUMENTS_PANEL_Y);
		this.documents_view_dimension = new Dimension(DOCUMENTS_VIEW_X, DOCUMENTS_VIEW_Y);
		this.title_label_dimension = new Dimension(TITLE_LABEL_X, TITLE_LABEL_Y);
		this.terms_label_dimension = new Dimension(TERMS_LABEL_X, TERMS_LABEL_Y);
		this.documents_label_dimension = new Dimension(DOCUMENTS_LABEL_X, DOCUMENTS_LABEL_Y);
		this.concepts_tree_dimension = new Dimension(CONCEPTS_TREE_VIEW_X, CONCEPTS_TREE_VIEW_Y);

		// Initialize menubar, menus, and menuitems
		this.main_menubar = new JMenuBar();

		this.menu_file = new JMenu(MENU_FILE_NAME);
		this.menu_edit = new JMenu(MENU_EDIT_NAME);
		this.menu_options = new JMenu(MENU_OPTIONS_NAME);
		this.menu_help = new JMenu(MENU_HELP_NAME);

		this.menu_item_quit = new JMenuItem(QUIT_OPTION_NAME);
		this.menu_item_upload = new JMenuItem(UPLOAD_OPTION_NAME);
		this.menu_item_clear = new JMenuItem(CLEAR_OPTION_NAME);
		this.menu_item_title_animation = new JCheckBoxMenuItem(TITLE_ANIMATION_OPTION_NAME);
		this.menu_item_help = new JMenuItem(HELP_OPTION_NAME);

		// Initialize elements of the title panel
		this.title_panel = new JPanel();
		this.title_label = new JLabel(TITLE_NAME);

		// Initialize elements in the search panel
		this.search_panel = new JPanel();
		this.search_bar = new JTextField(SEARCH_BAR_CONTENT);
		this.search_button = new JButton(SEARCH_BUTTON_NAME);

		// Initialize elements in the results panel
		this.results_panel = new JPanel();

		this.concepts_panel = new JPanel();

		this.root_node = new DefaultMutableTreeNode(ROOT_NODE_NAME);
		this.addElementsToTree(root_node);
		this.concepts_tree = new JTree(root_node);
		this.concepts_tree_view = new JScrollPane(concepts_tree);

		this.terms_panel = new JPanel();
		this.terms_label = new JLabel(TERMS_LABEL_NAME);
		this.term_list_model = new DefaultListModel<String>();
		this.term_list = new JList<String>(term_list_model);
		this.terms_view = new JScrollPane(term_list);

		this.documents_panel = new JPanel();
		this.documents_label = new JLabel(DOCUMENTS_LABEL_NAME);
		this.documents_list_model = new DefaultListModel<DocumentObject>();
		this.documents_list = new JList<DocumentObject>(documents_list_model);
		this.documents_view = new JScrollPane(documents_list);

		// Initialize layouts
		this.main_layout = new FlowLayout();
		this.results_panel_layout = new GridBagLayout();

		// Initialize GridBagConstraints
		this.results_panel_gbc = new GridBagConstraints();

		// Initialize borders
		this.results_panel_border = BorderFactory.createLineBorder(Color.GRAY);
		this.search_panel_border = BorderFactory.createLineBorder(Color.GRAY);
		this.concepts_panel_border = BorderFactory.createMatteBorder(CONCEPTS_PANEL_BORDER_TOP,
				CONCEPTS_PANEL_BORDER_LEFT, CONCEPTS_PANEL_BORDER_BOTTOM, CONCEPTS_PANEL_BORDER_RIGHT, Color.GRAY);
		this.terms_panel_border = BorderFactory.createMatteBorder(TERMS_PANEL_BORDER_TOP, TERMS_PANEL_BORDER_LEFT,
				TERMS_PANEL_BORDER_BOTTOM, TERMS_PANEL_BORDER_RIGHT, Color.GRAY);
		this.list_terms_border = BorderFactory.createMatteBorder(LIST_TERMS_BORDER_TOP, LIST_TERMS_BORDER_LEFT,
				LIST_TERMS_BORDER_BOTTOM, LIST_TERMS_BORDER_RIGHT, Color.GRAY);
		this.documents_border = BorderFactory.createMatteBorder(DOCUMENTS_BORDER_TOP, DOCUMENTS_BORDER_LEFT,
				DOCUMENTS_BORDER_BOTTOM, DOCUMENTS_BORDER_RIGHT, Color.GRAY);

		// Initialize icons
		this.main_icon = new ImageIcon(MAIN_ICON_PATH);

		// Initialize miscellaneous classes
		this.search_manager = new SearchManager(main_frame); // To manage the content of the search bar
		this.main_window_closer = new WindowCloser(main_frame); // To manage the closing of the jframe
		this.title_label_effect = new TitleLabelApparitionEffect(main_frame); // To manage the apparition of the title																		// label
		this.options_manager = new OptionsManager(main_frame);
		this.doc_renderer = new DocumentRenderer();
		
		// Settings of the title panel and their sub-elements
		title_label.setFont(title_font);
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		title_label.setPreferredSize(title_label_dimension);

		// Settings of the search panel
		search_panel.setBorder(search_panel_border);
		search_panel.setPreferredSize(search_panel_dimension);

		search_bar.setPreferredSize(search_bar_dimension);

		search_button.setPreferredSize(search_button_dimension);

		// Settings of the results panel and their sub-elements
		results_panel.setBorder(results_panel_border);
		results_panel.setLayout(results_panel_layout);
		results_panel.setPreferredSize(results_panel_dimension);

		concepts_panel.setPreferredSize(concepts_panel_dimension);
		concepts_panel.setBorder(concepts_panel_border);

		concepts_tree_view.setFont(list_elements_font);
		concepts_tree_view.setPreferredSize(concepts_tree_dimension);

		terms_panel.setPreferredSize(terms_panel_dimension);
		terms_panel.setBorder(terms_panel_border);

		terms_view.setPreferredSize(terms_view_dimension);

		term_list.setFont(list_elements_font);

		documents_panel.setPreferredSize(documents_panel_dimension);
		
		documents_list.setFont(list_elements_font);
		documents_list.setCellRenderer(doc_renderer);
		documents_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Settings of the labels in results panel
		terms_label.setHorizontalAlignment(SwingConstants.CENTER);
		terms_label.setPreferredSize(terms_label_dimension);
		terms_label.setBorder(list_terms_border);

		documents_label.setHorizontalAlignment(SwingConstants.CENTER);
		documents_label.setPreferredSize(documents_label_dimension);
		documents_label.setBorder(documents_border);

		documents_view.setPreferredSize(documents_view_dimension);

		// Adding items to the menus of the menubar
		menu_file.add(menu_item_quit);
		menu_edit.add(menu_item_upload);
		menu_edit.add(menu_item_clear);
		menu_options.add(menu_item_title_animation);
		menu_help.add(menu_item_help);

		// Adding menus to the menubar
		main_menubar.add(menu_file);
		main_menubar.add(menu_edit);
		main_menubar.add(menu_options);
		main_menubar.add(menu_help);

		// Adding elements to the concepts panel
		concepts_panel.add(concepts_tree_view);

		// Adding elements to the terms panel
		terms_panel.add(terms_label, results_panel_gbc);
		terms_panel.add(terms_view, results_panel_gbc);

		// Adding elements to the documents panel
		documents_panel.add(documents_label, results_panel_gbc);
		documents_panel.add(documents_view, results_panel_gbc);

		// Adding elements to the title panel
		title_panel.add(title_label);

		// Adding elements to the search panel
		search_panel.add(search_bar);
		search_panel.add(search_button);

		// Adding elements to the results panel
		results_panel_gbc.gridx = 0;
		results_panel_gbc.gridy = 0;
		results_panel_gbc.fill = GridBagConstraints.BOTH;
		results_panel_gbc.gridwidth = 1;
		results_panel_gbc.gridheight = 2;
		results_panel.add(concepts_panel, results_panel_gbc);

		results_panel_gbc.gridx = 1;
		results_panel_gbc.gridy = 0;
		results_panel_gbc.fill = GridBagConstraints.BOTH;
		results_panel_gbc.gridwidth = 1;
		results_panel_gbc.gridheight = 1;
		results_panel.add(terms_panel, results_panel_gbc);

		results_panel_gbc.gridx = 1;
		results_panel_gbc.gridy = 1;
		results_panel_gbc.fill = GridBagConstraints.BOTH;
		results_panel_gbc.gridwidth = 1;
		results_panel_gbc.gridheight = 1;
		results_panel.add(documents_panel, results_panel_gbc);

		// Adding elements to the interface
		this.add(title_panel);
		this.add(search_panel);
		this.add(results_panel);

		// Settings of the interface
   		this.addListeners(); // To add every listener of the interface
		this.setLayout(main_layout); // To set a layout to place the elements in the main_panel
		this.setJMenuBar(main_menubar); // To set the Menubar of the JFrame
		this.setLocationRelativeTo(WINDOW_LOCATION); // To center JFrame on the screen
		this.setTitle(INTERFACE_NAME); // To set a title to the JFrame
		this.setSize(WIDTH, HEIGHT); // To set the size of the JFrame
		this.setResizable(IS_RESIZABLE); // To make the JFrame impossible to resize
		this.setIconImage(main_icon.getImage()); // To set an icon at the top left of the interface
		this.addTrayIconAndMenu(); // To set an icon and menu on the system tray
		this.setVisible(IS_VISIBLE); // To make the JFrame visible on the screen
	}

	/**
	 * To add all the terms into the list model
	 * @param conceptID : the ID of the concept which have some terms
	 */
	private void addTerms(String conceptID) {
		term_list_model.clear();
        ArrayList<String> lesTermes = cpt_term.get(conceptID);       
        String term_to_add;
        
        for (int i = 0 ; i < lesTermes.size();i++) {
	            if (!term_list_model.contains(term.get(lesTermes.get(i)).getName())) {
	            	term_to_add = term.get(lesTermes.get(i)).getName();
	            	term_to_add = "- " + capitalize(term_to_add);
					this.term_list_model.addElement(term_to_add);
	            }
        }

		this.term_list_model = orderListModel(term_list_model);
	}

	/**
	 * Ordering by alphabetical order elements of the default list model 
	 * @param list_model: The list model to order
	 * @return the ordered the list model
	 */
	private DefaultListModel<String> orderListModel(DefaultListModel<String> list_model){
		//Sorting in alphabetical order
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < list_model.size(); i++) { 
			list.add(list_model.get(i));
		}
		Collections.sort(list);
		list_model.removeAllElements();
		for (String s : list) {
			list_model.addElement(s);
		}
		
		return list_model;
	}	

	/**
	 * Put the first letter of the string in uppercase
	 * @param str: The string to capitalize
	 * @return The capitalized string
	 */
	private String capitalize(String str) {
		if (!str.equals("")) {
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}
		
		return "";
	}
	
	/**
	 * To add all the documents linked to a term in the list of terms
	 */
	private void addDocuments(String conceptID) {
		documents_list_model.clear();
        ArrayList<DocumentObject> lesDocs = cpt.get(conceptID).getListe_doc();       
        
        for (int i = 0 ; i < lesDocs.size(); i++) { 
        	selected_doc = lesDocs.get(i);  	
            this.documents_list_model.addElement(selected_doc);
			documents_list.updateUI();
        }
	}

	/**
	 * To add all the listeners for the interface
	 */
	private void addListeners() {
		// Adding a listener to quit the interface
		menu_item_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Disposing the frame and exiting the program
				main_window_closer.quit();
			}
		});

		// Adding a listener to get the last selected element in the list of terms
		term_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					// System.out.println(main_frame.term_list.getSelectedValue().toString());
				}
			}
		});

		//Adding a listener to get the selected element in the document list
		documents_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					System.out.println(documents_list.getSelectedValue());
				}
			}
		});

		// Adding a listener to change the value of the state of the title animation
		menu_item_title_animation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				options_manager.setTitleAnimationEnabled(menu_item_title_animation.isSelected());
			}
		});

		menu_item_upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UploadJFrame(main_frame, cpt, term, cpt_term);
			}
		});

		// Adding a listener to display the documentation
		menu_item_help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				// Opening the "Help" dialog
				new HelpDialog(main_frame);
			}
		});

		// Adding actions to the search bar
		search_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				search_manager.actionPerformed(evt);
			}
		});

		// Adding a listener to clear the search bar
		menu_item_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_frame.clearSearchBarText();
			}
		});

		// Adding a listener to detect the concept clicked
		concepts_tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) concepts_tree.getLastSelectedPathComponent();
				if (node != null && !node.equals(root_node)) {
					Concept cpt = (Concept) node.getUserObject();
					main_frame.conceptId =  cpt.getId();
					addTerms(conceptId);
					addDocuments(conceptId);
				}
			}
		});

		// Adding a listener to manage the state of the checkbox in the class
		// OptionsManager
		menu_item_title_animation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean is_selected = main_frame.menu_item_title_animation.isSelected();
				main_frame.options_manager.setTitleAnimationEnabled(is_selected);
			}
		});

		//Adding a listener to open the selected document with a double left click
		documents_list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {	
				File file_to_open = null;

				if(evt.getClickCount() == 2) {
					//Get the selected element if the user double left-clicked
					@SuppressWarnings("unchecked")
					JList<DocumentObject> list = (JList<DocumentObject>) evt.getSource();
					int index = list.locationToIndex(evt.getPoint());
					DocumentObject selected_doc = list.getModel().getElementAt(index);
										
					ArrayList<DocumentObject> documents_of_concept = cpt.get(conceptId).getListe_doc();
					file_to_open = new File(selected_doc.getFilepath().toString());
//					for (int i = 0; i<documents_of_concept.size(); i++) {

//							String path_to_open = documents_of_concept.get(i).getFilepath().toString();
//							file_to_open = new File(path_to_open);
//					}
						try {
							if (file_to_open != null) {
							Desktop.getDesktop().open(file_to_open);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					
				}
			}
		});
		
		//Adding a listener to change the cursor when the user is pointing to the documents list
		documents_list.addMouseMotionListener(new MouseMotionListener() {
		    @Override
		    public void mouseMoved(MouseEvent e) {
		        final int x = e.getX();
		        final int y = e.getY();
		        // only display a hand if the cursor is over the items
		        final Rectangle cellBounds = documents_list.getCellBounds(0, documents_list.getModel().getSize() - 1);
		        if (cellBounds != null && cellBounds.contains(x, y)) {
		            documents_list.setCursor(new Cursor(Cursor.HAND_CURSOR));
		        } else {
		        	documents_list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        }
		    }

		    @Override
		    public void mouseDragged(MouseEvent e) {
		    }
		});
		
		this.addWindowListener(main_window_closer);
	}

	/**
	 * To create the tree structure and to add it to the
	 * root of the tree
	 * @param top: The root of the tree structure
	 */
	private void addElementsToTree(DefaultMutableTreeNode top) {
		
		// La map contenant les ids des concepts en clé et les objets swing en
		// value
		HashMap<String, DefaultMutableTreeNode> arborescence = new HashMap<String, DefaultMutableTreeNode>();

		// On parcourt la map des concepts pour initialisé celle des objets
		// swing
		for (Entry<String, Concept> cpts : cpt.entrySet()) {

			arborescence.put(cpts.getKey(), new DefaultMutableTreeNode(cpts.getValue()));
		}

		// On reparcourt en suite cette map pour trouver les isa dans chaque
		// concept
		// Si le concept n'as pas de isa on peut directement l'ajouter comme
		// racine
		// de l'arborescence
		for (Entry<String, Concept> cpts : cpt.entrySet()) {
			if (cpts.getValue().getIsa().length == 0) {
				top.add(arborescence.get(cpts.getKey()));
			}
			for (int i = 0; i < cpts.getValue().getIsa().length; i++) {
				arborescence.get(cpts.getValue().getIsa()[i]).add(arborescence.get(cpts.getValue().getId()));
			}
		}
	}

	/**
	 * To add an icon (and a menu with it) to the system tray
	 */
	private void addTrayIconAndMenu() {
		// If there is a system tray, adding the menu and the tray icons
		if (SystemTray.isSupported()) {
			// Initialize elements of the system tray
			system_tray = SystemTray.getSystemTray(); // Getting the system tray
			main_tray_icon = new TrayIcon(main_icon.getImage());
			main_popup = new PopupMenu();
			tray_menu_item_quit = new MenuItem(QUIT_OPTION_NAME);

			// Adding actions to the menu items
			tray_menu_item_quit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					// Disposing the frame and exiting the program
					main_window_closer.quit();
				}
			});

			// Adding items to the menu
			main_popup.add(tray_menu_item_quit);

			// Setting the popup menu of the tray icon
			main_tray_icon.setPopupMenu(main_popup);
			main_tray_icon.setImageAutoSize(true);

			// Adding the tray icon to the system tray
			try {
				system_tray.add(main_tray_icon);
			} catch (AWTException e) {
				System.err.println(TRAY_ICON_ADDING_ERROR);
			}
		}
	}

	/**
	 * To access to the main frame
	 * @return The main frame
	 */
	protected VisualisationJFrame getMainFrame() {
		return main_frame;
	}

	/**
	 * To access to the main tray icon 
	 * @return The main tray icon
	 */
	protected TrayIcon getMainTrayIcon() {
		return main_tray_icon;
	}

	/**
	 * To access to the system tray
	 * @return The system tray
	 */
	protected SystemTray getSystemTray() {
		return system_tray;
	}

	/**
	 * To access to the content of the search_bar
	 * @return The content of the search bar
	 */
	protected String getSearchBarText() {
		return this.search_bar.getText();
	}

	/**
	 * To clear the content of the search bar
	 */
	protected void clearSearchBarText() {
		this.search_bar.setText("");
	}

	/**
	 * To access to the title label above the search bar
	 * @return An instance of the title label
	 */
	protected JLabel getTitleLabel() {
		return title_label;
	}

	/**
	 * To access to the y position of the title label
	 * @return The y position of the label
	 */
	protected int getTitleLabelY() {
		return title_label.getY();
	}

	/**
	 * To access to the x position of the title label
	 * @return The x position of the label
	 */
	protected int getTitleLabelX() {
		return title_label.getX();
	}

	/**
	 * To access to the class that manages the effect of the title label
	 * @return TitleLabelApparitionEffect : The instance of the class 
	 * that manages the effect of the title label
	 */
	protected TitleLabelApparitionEffect getTitleLabelEffect() {
		return title_label_effect;
	}


	/**
	 * To access to the class that manages the options of the interface
	 * @return OptionsManager : an instance of the class that manages 
	 * the options of the interface
	 */
	protected OptionsManager getOptionsManager() {
		return options_manager;
	}

	/**
	 * To change the state of the
	 * checkbox for the animation of the title label
	 * @param b: The state of the checkbox
	 */
	protected void setTitleAnimationState(boolean b) {
		this.menu_item_title_animation.setSelected(b);
	}
	
	/**
	 * To get the document list frame
	 * @return the document list frame
	 */
	public DocumentListFrame getDocumentListFrame() {
		return document_list_frame;
	}

	/**
	 * To manages the shortcuts actions
	 * @param e: The event catched
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("quit")) {
			main_window_closer.quit();

		}
	}
}
