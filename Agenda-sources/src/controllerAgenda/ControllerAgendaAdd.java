package controllerAgenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

import modelAgenda.ModelAgenda;

/**
 * This class permits adding a new contact
 * @author Laure
 *
 */
public class ControllerAgendaAdd implements ActionListener, CaretListener {
	
	private JButton addButton;
	private JTextField textField;
	private ModelAgenda model;
	private JList list;
	private JTextField informationField;
	
	/**
	 * Build agenda controller which allows the user to add new contact and to see it appears in the list
	 * @param addButton : the button that emits the action to add the contact
	 * @param textField : the field that contains the new contact's name
	 * @param infoField : the field that contains the information of the new contact
	 * @param list : the contact list
	 * @param model : the model to allows property file access. See {@link ModelAgenda}
	 */
	public ControllerAgendaAdd(JButton addButton, JTextField textField, JTextField infoField, JList list, ModelAgenda model) {
		this.addButton = addButton;
		this.textField = textField;
		this.informationField = infoField;
		this.list = list;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String newContact = textField.getText();
		model.addContact(newContact);
		textField.setText("");
		list.setSelectedValue(newContact, true);
		informationField.requestFocus();
	}
	
	/**
	 * Enable or disable the add button according to the field caret status
	 * @param e : the caret event emitted when the text field is modified
	 * @return boolean : the status change of the add contact button
	 */
	private boolean enableButton(CaretEvent e) {
		if(((JTextComponent) e.getSource()).getDocument().getLength() > 0) {
			if (!addButton.isEnabled()) {
				addButton.setEnabled(true);
				return true;
			}		
		} else {			
			addButton.setEnabled(false);
		}
    	return false;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		enableButton(e);
	}

}
