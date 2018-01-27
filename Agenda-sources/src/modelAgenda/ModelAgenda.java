package modelAgenda;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;

import graphicalInterfaces.lang.Internationalization;

/**
 * The model that manages all the modification actions on the property file.
 * @author Laure
 *
 */
public class ModelAgenda extends DefaultListModel<String> {
	
	private Properties contacts;
	private String propertiesFileLocation = "./myContacts.properties";

	/**
	 * Initializes the model of the agenda. The contacts are loaded in a {@link Properties} file.
	 */
	public ModelAgenda() {
		super();
		this.contacts = new Properties();
		loadContacts();
		
	}	
	
	/**
	 * Allows the model to save the status of the model and the contact list in the file
	 */
	public void saveProperties() {
		try(OutputStream out = new FileOutputStream(propertiesFileLocation)){
			contacts.store(out, "contacts");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a new contact with his informations to the list and the model
	 * @param contactName : the name of the contact
	 * @param contactInformation : the information of the contact
	 */
	public void addContact(String contactName, String contactInformation) {
		contacts.setProperty(contactName, contactInformation);
		this.addElement(contactName);	
	}
	
	/**
	 * Adds a new contact with a name only
	 * @param contactName : the name of the contact
	 */
	public void addContact(String contactName) {
		contacts.setProperty(contactName, "");
		this.addElement(contactName);
	}
	
	/**
	 * Deletes a contact using his name to find him, if he exists
	 * @param contactName : the name of the contact
	 */
	public void deleteContact(String contactName) {
		if(contacts.containsKey(contactName)) {			
			this.removeElement(contactName);
			contacts.remove(contactName);
		}
	}
	
	/**
	 * Updates a contact information
	 * @param contactName : the name of the contact
	 * @param contactInformation : the information of the contact
	 */
	public void changeInformations(String contactName, String contactInformation) {
		if(contacts.containsKey(contactName)) {			
			contacts.setProperty(contactName, contactInformation);
		}
	}
	
	/**
	 * Updates the contact name
	 * @param oldContactName : the old name of the contact to update
	 * @param contactName : the name of the contact
	 * @param contactInformation : the information of the contact
	 */
	public void changeContact(String oldContactName, String contactName, String contactInformation) {
		contacts.remove(oldContactName);
		contacts.setProperty(contactName, contactInformation);
		this.setElementAt(contactName, this.indexOf(oldContactName));
	}
	
	/**
	 * Loads the contacts in the property file
	 */
	private void loadContacts() {
		try(InputStream in = new FileInputStream(propertiesFileLocation)) {
			contacts.load(in);
			for (Object propriete : contacts.keySet()) {
				addElement(propriete.toString());
			}
		} catch(IOException el) {
			el.printStackTrace();
		}
	}
	
	/**
	 * Gets the information of a specified contact
	 * @param contactName : the name of the contact
	 * @return String : the information of the contact
	 */
	public String getValue(String contactName) {
		return contacts.getProperty(contactName);
	}
}
