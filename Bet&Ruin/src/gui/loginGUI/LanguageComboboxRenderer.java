package gui.loginGUI;

import java.awt.Component;



import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class LanguageComboboxRenderer extends JLabel implements ListCellRenderer<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6854343484250486727L;

	public LanguageComboboxRenderer() {
	 		
	}
	
	@Override
	public Component getListCellRendererComponent(JList<?> arg0, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		switch(value.toString().toLowerCase())
		{
		case "english" :
		case "inglés":
		case "ingelesa":
			setIcon(new ImageIcon("resources/images/flags/english.png"));
			break;
		case "spanish":
		case "español":
		case "gaztelera":
			setIcon(new ImageIcon("resources/images/flags/spanish.png"));
			break;
		case "basque":
		case "euskera":
			setIcon(new ImageIcon("resources/images/flags/basque.png"));
			break;
		default:
			break;
		}
		setText(" "+value.toString());
		return this;
	}

}
