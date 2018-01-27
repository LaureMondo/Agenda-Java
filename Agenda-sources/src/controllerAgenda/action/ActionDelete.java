package controllerAgenda.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import graphicalInterfaces.lang.Internationalization;
import modelAgenda.ModelAgenda;

/**
 * The action of deleting a contact
 * @author Laure
 *
 */
public class ActionDelete extends AbstractAction{
	
	private ModelAgenda model;
	private JList listContact;
	private ResourceBundle lang;
	private JComponent frame;
	private String selectedKey;

	/**
	 * The action used by buttons (menu buttons) to delete a selected contact.
	 * This action can have text, description and an icon.
	 * @param text : the text of the button
	 * @param icon : the icon of the button
	 * @param desc : the description of what the button do
	 * @param selectedKey : the selected contact to delete
	 * @param model : the model to allows property file access. See  {@link ModelAgenda}
	 * @param list : the contact list
	 * @param frame : the frame that called the action
	 */
	public ActionDelete(String text, Icon icon, String desc, String selectedKey, ModelAgenda model, JList list, JComponent frame) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		this.model = model;
		this.selectedKey = selectedKey;
		this.listContact = list;
		this.lang = Internationalization.getInstance().getFile();
		this.frame = frame;
	}
	
	/**
	 * Build the dialog box used to confirm a contact deletion
	 */
	private void buildDialog() {
		int deleteAction = JOptionPane.showConfirmDialog(frame, lang.getString("questionDelete"), 
				lang.getString("titleDelete"), JOptionPane.YES_NO_OPTION);  
		if (deleteAction == 0) {
			model.deleteContact(selectedKey);
    	   listContact.setSelectedIndex(0);
    	   selectedKey = (String) listContact.getSelectedValue();
       }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		buildDialog();		
	}
	
	/**
	 * Sets the name of the action
	 * @param text : the text to set on the action
	 */
	public void setText(String text) {
		this.putValue(NAME, text);
	}
	
	/**
	 * Sets the short description of the action
	 * @param desc : the description to set to the action
	 */
	public void setDescription(String desc) {
		this.putValue(SHORT_DESCRIPTION, desc);
	}
	
}
