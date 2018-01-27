package controllerAgenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import modelAgenda.ModelAgenda;

/**
 * This class permits updating a new contact
 * @author Laure
 *
 */
public class ControllerAgendaUpdate implements ActionListener {
	
	private JTextField contactInfoField;
	private ModelAgenda model;
	private JList list;

	/**
	 * Build agenda controller which allows the user to update a selected contact and to see it in the list when modified
	 * @param contactInfoField : the field which contains the contact information to update
	 * @param list : the contact list
	 * @param model : the model to allows property file access. See {@link ModelAgenda}
	 */
	public ControllerAgendaUpdate(JTextField contactInfoField, JList list, ModelAgenda model) {
		this.contactInfoField = contactInfoField;
		this.model = model;
		this.list = list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String updateContact = contactInfoField.getText();
		model.changeInformations(list.getSelectedValue().toString(), updateContact);
	}

	

}
