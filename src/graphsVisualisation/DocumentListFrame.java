package graphsVisualisation;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 * An interface to show all documents related to a search
 */
public class DocumentListFrame extends JFrame {
	private static final long serialVersionUID = -126296334709183185L;

	//Constants for the interface
	private final static short WIDTH = 400;
	private final static short HEIGHT = 650;
	private final boolean IS_VISIBLE = true;
	private final boolean IS_RESIZABLE = false;
	private final String MAIN_TITLE = "Documents";

	//Constants for the dimensions
	private static final short TITLE_PANEL_X = WIDTH;
	private static final short TITLE_PANEL_Y = 3*HEIGHT/20;

	private static final short DOCUMENTS_PANEL_X = WIDTH-20;
	private static final short DOCUMENTS_PANEL_Y = HEIGHT-TITLE_PANEL_Y;
	
	private static final short TITLE_LABEL_X = TITLE_PANEL_X-50;
	private static final short TITLE_LABEL_Y = TITLE_PANEL_Y-50;
	
	private static final short DOCUMENTS_VIEW_X = DOCUMENTS_PANEL_X-30;
	private static final short DOCUMENTS_VIEW_Y = DOCUMENTS_PANEL_Y-38;
	
	//Constants for the borders
	private final String DOCUMENTS_PANEL_BORDER_NAME = "Documents";
	
	//Constants for the icons
	private final String MAIN_ICON_PATH = "ressources" + File.separator + "logo.png";
		
	//Constants for the labels
	private final String TITLE_LABEL_NAME = "Documents";
    private final String TITLE_FONT = "Georgia";
    private final byte TITLE_SIZE = 25;
    
    //Constants for the documents to show
    private final String DOCUMENTS_PATH = "ressources" + File.separator + "fileToIndex";
    
    //Constants for the menu
    private final String REMOVE_MENU_ITEM_NAME = "Supprimer";
    
	//Fonts
	private Font title_font;
	
	//Layouts
	private FlowLayout main_layout;
	
	//Icons
	private ImageIcon main_icon;
	
	//JPanels
	private JPanel title_panel;
	private JPanel documents_panel;
	
	//JLabels
	private JLabel title_label;
	
	//Dimensions
	private Dimension title_panel_dimension;
	private Dimension documents_panel_dimension;
	private Dimension title_label_dimension;
	private Dimension documents_view_dimension;
	
	//Borders
	private Border documents_panel_border;

	//DefaultListModels
	private DefaultListModel<DocumentObject> documents_list_model;

	//JLists
	private JList<DocumentObject> documents_list;

	//JScrollPanes
	private JScrollPane documents_view;
	
	//JPopupMenus
	private JPopupMenu list_menu;
	
	//JMenuItems
	private JMenuItem remove_menu_item;
	
	//Files
	private File file_to_delete;
	
	/**
	 * Main constructor
	 * @param documents_list_to_add 
	 */
	public DocumentListFrame(ArrayList<DocumentObject> documents_list_to_add) {
		//Initialiation		
		this.main_icon = new ImageIcon(MAIN_ICON_PATH);
		
		this.title_panel = new JPanel();
		this.documents_panel = new JPanel();
		
		this.title_label = new JLabel(TITLE_LABEL_NAME);
		
		this.title_font = new Font(TITLE_FONT, Font.PLAIN, TITLE_SIZE);
		
		this.title_panel_dimension = new Dimension(TITLE_PANEL_X, TITLE_PANEL_Y);
		this.documents_panel_dimension = new Dimension(DOCUMENTS_PANEL_X, DOCUMENTS_PANEL_Y);
		this.title_label_dimension = new Dimension(TITLE_LABEL_X, TITLE_LABEL_Y);
		this.documents_view_dimension = new Dimension(DOCUMENTS_VIEW_X, DOCUMENTS_VIEW_Y);
		
		this.documents_panel_border = BorderFactory.createTitledBorder(DOCUMENTS_PANEL_BORDER_NAME);
		
		this.main_layout = new FlowLayout();
		
		this.documents_list_model = new DefaultListModel<DocumentObject>();
		this.getDocuments(documents_list_to_add);
		this.documents_list = new JList<DocumentObject>(documents_list_model);
		this.documents_view = new JScrollPane(documents_list);
		
		//Settings for the panels
		title_panel.setPreferredSize(title_panel_dimension);
		
		title_panel.setPreferredSize(title_label_dimension);
		title_label.setFont(title_font);
		
		documents_panel.setPreferredSize(documents_panel_dimension);
		documents_panel.setBorder(documents_panel_border);
		
		documents_view.setPreferredSize(documents_view_dimension);
		
		documents_list.setCellRenderer(new DocumentRenderer());
		documents_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Adding elements to the panels
		title_panel.add(title_label);
		
		documents_panel.add(documents_view);
		
		//Adding elements to the frame
		this.add(title_panel);
		this.add(documents_panel);
		
		//Settings of the interface
		this.createMenu();
		this.addListeners();
		this.setLayout(main_layout);
		this.main_icon = new ImageIcon(MAIN_ICON_PATH);
		this.setIconImage(main_icon.getImage()); // To set an icon at the top left of the interface
		this.setTitle(MAIN_TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(IS_VISIBLE);
		this.setResizable(IS_RESIZABLE);
	}

	/**
	 * To add all the listeners on the interface
	 */
	private void addListeners() {
		//Adding a listener to open the selected document with a double left click
		documents_list.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {	
				if(evt.getClickCount() == 2) {
					//Get the selected element if the user double left-clicked
					@SuppressWarnings("unchecked")
					JList<DocumentObject> list = (JList<DocumentObject>) evt.getSource();
					int index = list.locationToIndex(evt.getPoint());
					DocumentObject selected_file = list.getModel().getElementAt(index);
							
					//Opening the selected with the default application
					try {
						File file_to_open = new File(selected_file.getFilepath().toString());
						Desktop.getDesktop().open(file_to_open);
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
		
		//Adding a listener to remove a document by right click on an
		//element of the documents list
		documents_list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    if (SwingUtilities.isRightMouseButton(e)) {
			    	//Get the selected element with the right click
			    	@SuppressWarnings("unchecked")
					JList<File> list = (JList<File>) e.getSource();
					int index = list.locationToIndex(e.getPoint());
					file_to_delete = list.getModel().getElementAt(index);
			        
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
					//Reloading the list if the file was deleted
					documents_list_model.removeElement(file_to_delete);
					documents_list.updateUI();
				}
			}
		});
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
	 * To get all the documents from a concept ID
	 */
	protected void getDocuments(ArrayList<DocumentObject> documents_list_to_add) {
		documents_list_model.clear();
        for(int i=0; i<documents_list_to_add.size(); i++) {
        	documents_list_model.addElement(documents_list_to_add.get(i));
        }
        
        //documents_list.updateUI();
	}
	
	/**
	 * To get the model of the document list
	 * @return The document list model
	 */
	public DefaultListModel<DocumentObject> getDocumentsListModel() {
		return documents_list_model;
	}
}
