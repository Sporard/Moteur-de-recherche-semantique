package graphsVisualisation;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.filechooser.FileSystemView;

public class DocumentRenderer extends DefaultListCellRenderer{
	private static final long serialVersionUID = 5783503977758000407L;
	
	public DocumentRenderer() {}
	
	@SuppressWarnings("rawtypes")
	@Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
		
        Component c = super.getListCellRendererComponent(
            list,value,index,isSelected,cellHasFocus);
        JLabel l = (JLabel)c;
        DocumentObject doc = (DocumentObject)value;   
        l.setText(doc.getName());
        
        File file_for_icon = new File(doc.getFilepath().toAbsolutePath().toString());
        if(file_for_icon.exists()) {
            l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(file_for_icon));
        }
        
        return l;
    }

}
