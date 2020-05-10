package graphsVisualisation;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ConceptRenderer extends Component implements ListCellRenderer<Concept>, Serializable{
	private static final long serialVersionUID = 8855633390817976855L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Concept> list, Concept value, int index, boolean isSelected, boolean cellHasFocus) {
		return (JLabel) this.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	}
}
