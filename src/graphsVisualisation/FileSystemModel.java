package graphsVisualisation;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileSystemModel implements TreeModel, Serializable {
	private File root;
	@SuppressWarnings("rawtypes")
	private Vector listeners = new Vector();
	 
	public FileSystemModel(File rootDirectory) {
		root = rootDirectory;
	}
	 
	public Object getRoot() {
		return root;
	}
	 
	public Object getChild(Object parent, int index) {
		File directory = (File) parent;
	    String[] children = directory.list();
	    return new File(directory, children[index]);
	}
	 
	public int getChildCount(Object parent) {
	    File file = (File) parent;
	    if (file.isDirectory()) {
	    	String[] fileList = file.list();
	    	if (fileList != null) {
	    		return file.list().length;
	    	}
	    }
	    return 0;
	}
	 
	public boolean isLeaf(Object node) {
	    File file = (File) node;
	    return file.isFile();
	}
	 
	public int getIndexOfChild(Object parent, Object child) {
	    File directory = (File) parent;
	    File file = (File) child;
	    String[] children = directory.list();
	    for (int i = 0; i < children.length; i++) {
	    	if (file.getName().equals(children[i])) {
	    		return i;
	    	}
	    }
	    return -1;
	}
	 
	public void valueForPathChanged(TreePath path, Object value) {
	    File oldFile = (File) path.getLastPathComponent();
	    String fileParentPath = oldFile.getParent();
	    String newFileName = (String) value;
	    File targetFile = new File(fileParentPath, newFileName);
	    oldFile.renameTo(targetFile);
	    File parent = new File(fileParentPath);
	    int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
	    Object[] changedChildren = { targetFile };
	    fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren); 
	}
	 
	private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
	    TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
	    @SuppressWarnings("rawtypes")
		Iterator iterator = listeners.iterator();
	    TreeModelListener listener = null;
	    while (iterator.hasNext()) {
	    	listener = (TreeModelListener) iterator.next();
	    	listener.treeNodesChanged(event);
	    }
	}
	 
	@SuppressWarnings("unchecked")
	public void addTreeModelListener(TreeModelListener listener) {
	    listeners.add(listener);
	}
	 
	public void removeTreeModelListener(TreeModelListener listener) {
	    listeners.remove(listener);
	}
}