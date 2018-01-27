package controllerAgenda.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import modelAgenda.ModelAgenda;

/**
 * The action for saving the current contact list
 * @author Laure
 *
 */
public class ActionSave extends AbstractAction{
	
	private ModelAgenda model;

	/**
	 * The action used by buttons (menu buttons) to save the current status of the contact list.
	 * @param text : the text of the button
	 * @param icon : the icon of the button
	 * @param desc : the description of what the button do
	 * @param model : the model to allows property file access. See {@link ModelAgenda}
	 */
	public ActionSave(String text, Icon icon, String desc, ModelAgenda model) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.saveProperties();
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