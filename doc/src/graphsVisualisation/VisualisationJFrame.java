package graphsVisualisation;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * The class VisualisationJFrame is an extension of the class JFrame that
 * permits to show an interface to the user to help him browse the results of
 * the indexed documents of our semantic search engine.
 */
@SuppressWarnings("unused")
public class VisualisationJFrame extends JFrame implements ActionListener {
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

    // DefaultMutableTreeNode
    private DefaultMutableTreeNode root_node;

    // Font
    private Font title_font;

    // JList
    private JList<String> term_list;
    
    // DefaultList
    private DefaultListModel<String> term_list_model;
    
    // Dimensions
    private Dimension search_panel_dimension;
    private Dimension search_bar_dimension;
    private Dimension search_button_dimension;
    private Dimension results_panel_dimension;
    private Dimension concepts_panel_dimension;
    private Dimension terms_panel_dimension;
    private Dimension terms_view_dimension;
    private Dimension documents_panel_dimension;
    private Dimension title_label_dimension;
    private Dimension terms_label_dimension;
    private Dimension documents_label_dimension;
    private Dimension concepts_tree_dimension;

    // Borders
    private Border title_panel_border;
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

    // Constants for the names of the menus
    private final String MENU_FILE_NAME = "Fichier";
    private final String MENU_EDIT_NAME = "Edition";
    private final String MENU_OPTIONS_NAME = "Options";
    private final String MENU_HELP_NAME = "Aide";

    // Constants for the names of the items of the menus
    private final String QUIT_OPTION_NAME = "Quitter";
    private final String MENU_ITEM_UPLOAD = "Upload";
    private final String CLEAR_OPTION_NAME = "Nettoyer la recherche";
    private final String TITLE_ANIMATION_OPTION_NAME = "Animation du titre";
    private final String HELP_OPTION_NAME = "Documentation";
    private final String DEFAULT_OPTION_NAME = "???";

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

    private final short TERMS_VIEW_X = TERMS_PANEL_X+3;
    private final short TERMS_VIEW_Y = TERMS_PANEL_Y-TITLE_LABEL_Y-3;
    
    private final short DOCUMENTS_LABEL_X = DOCUMENTS_PANEL_X;
    private final short DOCUMENTS_LABEL_Y = 15;

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
    private final byte TITLE_SIZE = 25;

    // Constants for the labels in results_panel
    private final String ROOT_NODE_NAME = "Liste des concepts";
    private final String TERMS_LABEL_NAME = "Liste des termes";
    private final String DOCUMENTS_LABEL_NAME = "Documents";

    // Constants for the errors
    private final String TRAY_ICON_ADDING_ERROR = "L'icône de notification n'a pas pu être créée !";

    /**
     * VisualisationJFrame() is the main constructor of this class. This
     * constructor creates all the user interface and is the "entry point" of
     * this one.
     */
    public VisualisationJFrame() {
	// Initialize frame
	this.main_frame = this;

	// Initialize font
	this.title_font = new Font(TITLE_FONT, Font.PLAIN, TITLE_SIZE);

	// Initialize dimensions
	this.search_panel_dimension = new Dimension(SEARCH_PANEL_X, SEARCH_PANEL_Y);
	this.search_bar_dimension = new Dimension(SEARCH_BAR_X, SEARCH_BAR_Y);
	this.search_button_dimension = new Dimension(SEARCH_BUTTON_X, SEARCH_BUTTON_Y);
	this.results_panel_dimension = new Dimension(RESULTS_PANEL_X, RESULTS_PANEL_Y);
	this.concepts_panel_dimension = new Dimension(CONCEPTS_PANEL_X, CONCEPTS_PANEL_Y);
	this.terms_panel_dimension = new Dimension(TERMS_PANEL_X, TERMS_PANEL_Y);
	this.terms_view_dimension = new Dimension(TERMS_VIEW_X, TERMS_VIEW_Y);
	this.documents_panel_dimension = new Dimension(DOCUMENTS_PANEL_X, DOCUMENTS_PANEL_Y);
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
	this.menu_item_upload = new JMenuItem(MENU_ITEM_UPLOAD);
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
	this.addTerms();
	this.term_list = new JList<String>(term_list_model);
	this.terms_view = new JScrollPane(term_list);

	this.documents_panel = new JPanel();
	this.documents_label = new JLabel(DOCUMENTS_LABEL_NAME);

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
	this.title_label_effect = new TitleLabelApparitionEffect(main_frame); // To manage the apparition of the title label
	this.options_manager = new OptionsManager(main_frame);

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

	concepts_tree_view.setPreferredSize(concepts_tree_dimension);

	terms_panel.setPreferredSize(terms_panel_dimension);
	terms_panel.setBorder(terms_panel_border);
	
	terms_view.setPreferredSize(terms_view_dimension);
	
	documents_panel.setPreferredSize(documents_panel_dimension);

	// Settings of the labels in results panel
	terms_label.setHorizontalAlignment(SwingConstants.CENTER);
	terms_label.setPreferredSize(terms_label_dimension);
	terms_label.setBorder(list_terms_border);

	documents_label.setHorizontalAlignment(SwingConstants.CENTER);
	documents_label.setPreferredSize(documents_label_dimension);
	documents_label.setBorder(documents_border);

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
     * addTerms permits to add all the terms into the list model
     */
    private void addTerms() {
    	for(int i=0; i<30; i++) {
    		this.term_list_model.addElement("- Terme " + Integer.toString(i));
    	}
	}

	/**
     * addListeners() permits to add all the listeners for the interface
     */
    private void addListeners() {
    	//Adding a listener to open the upload interface
    	menu_item_upload.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			new UploadJFrame();
    		}
    	});
    	
    	// Adding a listener to quit the interface
    	menu_item_quit.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			// Disposing the frame and exiting the program
    			main_window_closer.quit();
    		}
    	});

    	//Adding a listener to get the last selected element in the list of terms
    	term_list.addListSelectionListener(new ListSelectionListener() {
    		@Override
    		public void valueChanged(ListSelectionEvent e) {
    			if(!e.getValueIsAdjusting()) {
    				//System.out.println(main_frame.term_list.getSelectedValue().toString());
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

    	// Adding a listener to display the documentation
    	menu_item_help.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent evt) {
    			//Opening the "Help" dialog
    			new HelpDialog(main_frame);
    		}
    	});

    	//Adding actions to the search bar
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
   
    			try{
    				Concept cpt = (Concept) node.getUserObject();
    			}catch(ClassCastException e) {
    				System.err.println("Erreur cast");
    			}
    			
    			ParserOnto parser = new ParserOnto("./ressources/clean_data.json");
    			HashMap<String, ArrayList<String>> cpt_trm = parser.cpt_trm();
    		}
    	});

    	//Adding a listener to manage the state of the checkbox in the class OptionsManager
    	menu_item_title_animation.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			boolean is_selected = main_frame.menu_item_title_animation.isSelected();
    			main_frame.options_manager.setTitleAnimationEnabled(is_selected);
    		}
    	});

    	this.addWindowListener(main_window_closer);
    }

    /**
     * addElementsToTree() permits to create the tree structure and to add it to
     * the root of the tree
     * @param top: The root of the tree structure
     */
    private void addElementsToTree(DefaultMutableTreeNode top) {
	// Le parser du json
	ParserOnto parser = new ParserOnto("ressources/clean_data.json");

	// La map des concept
	HashMap<String, Concept> lesCpts = parser.lesConcepts();
	// La map contenant les ids des concepts en clé et les objets swing en
	// value
	HashMap<String, DefaultMutableTreeNode> arborescence = new HashMap<String, DefaultMutableTreeNode>();

	// On parcourt la map des concepts pour initialisé celle des objets
	// swing
	for (Entry<String, Concept> cpt : lesCpts.entrySet()) {

	    arborescence.put(cpt.getKey(), new DefaultMutableTreeNode(cpt.getValue()));
	}

	// On reparcourt en suite cette map pour trouver les isa dans chaque
	// concept
	// Si le concept n'as pas de isa on peut directement l'ajouter comme
	// racine
	// de l'arborescence
	for (Entry<String, Concept> cpt : lesCpts.entrySet()) {
	    if (cpt.getValue().getIsa().length == 0) {
	    	top.add(arborescence.get(cpt.getKey()));
	    }
	    for (int i = 0; i < cpt.getValue().getIsa().length; i++) {
	    	arborescence.get(cpt.getValue().getIsa()[i]).add(arborescence.get(cpt.getValue().getId()));
	    }

	}

    }

    /**
     * addTrayIconAndMenu() permits to add an icon (and a menu with it) to the
     * system tray
     */
    private void addTrayIconAndMenu() {
    	// If there is a system tray, adding the menu and the tray icons
    	if (SystemTray.isSupported()) {
    		// Initialize elements of the system tray
    		system_tray = SystemTray.getSystemTray(); // Getting the system tray
    		main_tray_icon = new TrayIcon(main_icon.getImage());
    		main_popup = new PopupMenu();
    		tray_menu_item_quit = new MenuItem(QUIT_OPTION_NAME);

    		//Adding actions to the menu items
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
     * getMainFrame() is a getter to access to the main frame
     * @return VisualisationJFrame : An instance of the main frame
     */
    protected VisualisationJFrame getMainFrame() {
    	return main_frame;
    }

    /**
     * getMainTrayIcon() is a getter to access to the main tray icon
     * @return TrayIcon : This getter returns the main tray icon
     */
    protected TrayIcon getMainTrayIcon() {
    	return main_tray_icon;
    }

    /**
     * getSystemTray() is a getter to access to the system tray
     * @return SystemTray : This getter returns the system tray
     */
    protected SystemTray getSystemTray() {
    	return system_tray;
    }

    /**
     * getSearchBarText() is a getter to access to the content of the search_bar 
     * @return String : This getter returns the content of the search bar
     */
    protected String getSearchBarText() {
    	return this.search_bar.getText();
    }

    /**
     * clearSearchBarText() is a method to clear the content of the search bar
     */
    protected void clearSearchBarText() {
    	this.search_bar.setText("");
    }

    /**
     * getTitleLabel() is a getter to access to the title label above the search
     * bar
     * 
     * @return JLabel : This getter returns the title label
     */
    protected JLabel getTitleLabel() {
    	return title_label;
    }

    /**
     * getTitleLabelY() is a getter to access to the y position of the title
     * label
     * 
     * @return int : This getter returns the y position of the label
     */
    protected int getTitleLabelY() {
    	return title_label.getY();
    }

    /**
     * getTitleLabelX() is a getter to access to the x position of the title
     * label
     * 
     * @return int : This getter returns the x position of the label
     */
    protected int getTitleLabelX() {
    	return title_label.getX();
    }

    /**
     * getTitleLabelEffect is a getter to access to the class that manages the
     * effect of the title label
     * 
     * @return TitleLabelApparitionEffect : This getter returns the instance of
     *         the class that manages the effect of the title label
     */
    protected TitleLabelApparitionEffect getTitleLabelEffect() {
    	return title_label_effect;
    }

    /**
     * getOptionsManager is a getter to access to the class that manages the
     * options of the interface
     * 
     * @return OptionsManager : an instance of the class that manages the
     *         options of the interface
     */
    protected OptionsManager getOptionsManager() {
    	return options_manager;
    }

    /**
     * setTitleAnimationState is a setter that permits to change the state of
     * the checkbox for the animation of the title label
     * 
     * @param b: The state of the checkbox (boolean)
     */
    protected void setTitleAnimationState(boolean b) {
    	this.menu_item_title_animation.setSelected(b);
    }

    /**
     * actionPerformed() is a method implemented with ActionListener that
     * manages the shortcuts actions
     * 
     * @param e: The event catched
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("quit")) {
	    	main_window_closer.quit();
		}
    }
}
