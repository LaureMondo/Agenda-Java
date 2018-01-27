package graphicalInterfaces;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controllerAgenda.ControllerAgendaAdd;
import controllerAgenda.ControllerAgendaUpdate;
import controllerAgenda.action.ActionSave;
import graphicalInterfaces.lang.InterfaceObserver;
import graphicalInterfaces.lang.Internationalization;
import modelAgenda.ModelAgenda;

/**
 * Graphical interface of the agenda
 * @author Laure
 *
 */
public class AdresseBook extends JFrame implements ListSelectionListener, InterfaceObserver{
	
	private JScrollPane listScrollPane;
	private JList listContact;
    private JTextField newContactField;
    private JTextField contactInfoField;
    
    private ModelAgenda listContactModel;
    
    private JMenuBar menuBar;
	private JToolBar toolBar;
	private ActionSave saveAction;
	private JMenu menuFirstEntry;
	private JMenu menuSecondEntry;
	private JMenuItem updateMenuItem;
	private JMenuItem deleteMenuItem;
	private JMenuItem saveMenuItem;
	
	private JLabel newContactLabel;
	private JLabel updateContactInfoLabel;
	private JButton buttonSav;
	private JButton addButton;
	private JButton validateButton;
	
	private String selectedKey = "";
	private String selectedValue = "";
	
	private ResourceBundle lang;
	private Internationalization langInternational;
	
	private JRadioButtonMenuItem changeLangCorse;
	private JRadioButtonMenuItem changeLangFrench;
	private JRadioButtonMenuItem changeLangEnglish;
	
	/**
	 * Constructor : initialize the model and the language to use
	 */
	public AdresseBook() {
		listContactModel = new ModelAgenda();
		langInternational = Internationalization.getInstance();
		lang = langInternational.getFile();	
	}
	
	
	// ---------------------------------------------------------
	
	/**
	 * Defines the dialog box before quitting the application.
	 * If 'YES' is clicked the contacts are saved.
	 * In both case the application stops at the end of this dialog.
	 */
	private void saveOnExitWindow() {
		WindowListener saveOnExitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, lang.getString("questionSaveBefore"), 
		             lang.getString("titleSaveBefore"), JOptionPane.YES_NO_OPTION, 
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	listContactModel.saveProperties();
		        	System.exit(0);
		        }
		    }
		};
		this.addWindowListener(saveOnExitListener);
	}

	/**
	 * Build the main panel of the application
	 */
	private void buildPane() {
		createListContact();
		
		listScrollPane = new JScrollPane(listContact);
		
		newContactField = new JTextField(10);
		addButton = new JButton(lang.getString("buttonAdd"));

		newContactLabel = new JLabel(lang.getString("labelAdd"));
		
		contactInfoField = new JTextField(30);
		validateButton = new JButton(lang.getString("buttonValidate"));
		updateContactInfoLabel = new JLabel(lang.getString("labelUpdateContactInfo"));
		
        ControllerAgendaAdd addListener = new ControllerAgendaAdd(addButton, newContactField, contactInfoField, listContact, listContactModel);
        
        ControllerAgendaUpdate updateListener = new ControllerAgendaUpdate(contactInfoField, listContact, listContactModel);
        
        // ajouter un nouveau contact
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
       
        newContactField.addCaretListener(addListener);
        
        if(selectedKey == null) {
        	contactInfoField.setText("");
        } else {        	
        	contactInfoField.setText(listContactModel.getValue(listContact.getSelectedValue().toString()));
        }
        // Afficher et modifier la valeur du numero d'un contact existant
        contactInfoField.addActionListener(updateListener);
        validateButton.addActionListener(updateListener);
        
        JPanel contactInfoPanel = new JPanel();
        contactInfoPanel.add(updateContactInfoLabel);
        contactInfoPanel.add(contactInfoField);
        contactInfoPanel.add(validateButton);
        
        JPanel newContactPanel = new JPanel();
        newContactPanel.setLayout(new BoxLayout(newContactPanel, BoxLayout.LINE_AXIS));
        newContactPanel.add(newContactLabel);
        newContactPanel.add(addButton);
        newContactPanel.add(newContactField);
        
        JPanel centerBoxPanel = new JPanel(); 
        centerBoxPanel.setLayout(new BorderLayout());
        centerBoxPanel.add(listScrollPane, BorderLayout.PAGE_START);
        centerBoxPanel.add(contactInfoPanel, BorderLayout.CENTER);

        add(centerBoxPanel, BorderLayout.CENTER);
        add(newContactPanel, BorderLayout.PAGE_END);
        
		
	}

	/**
	 * Initialisation of the list contacts
	 */
	private void createListContact() {
		listContact = new JList(listContactModel);
		listContact.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listContact.setSelectedIndex(0);
		listContact.addListSelectionListener(this);
		
		listContact.setSelectedIndex(0);
		selectedKey = (String) listContact.getSelectedValue();
	}
	
	/**
	 * Build the 'file' menu entry with its internal menus : lang radio switch and save button
	 */
	private void buildFirstMenuEntryFile() {
		menuFirstEntry = new JMenu(lang.getString("file"));
		menuFirstEntry.setMnemonic(KeyEvent.VK_F);
		
		buildSaveButtonMenu();
		menuFirstEntry.addSeparator();
		buildLangChangerButtonMenu();
		
		menuBar.add(menuFirstEntry);
		
	}


	/**
	 * Build the menu item to change the language of the application
	 */
	private void buildLangChangerButtonMenu() {
		
		Locale frenchLocale = new Locale("fr", "FR");
		Locale englishLocale = new Locale("en", "EN");
		Locale corseLocale = new Locale("co", "FR");
		
		changeLangEnglish = new JRadioButtonMenuItem(lang.getString("englishLang"));
		changeLangEnglish.addActionListener(e -> {
				this.langInternational.setFileLang(englishLocale);
			});
		
		changeLangFrench = new JRadioButtonMenuItem(lang.getString("frenchLang"));
		changeLangFrench.addActionListener(e -> {
			this.langInternational.setFileLang(frenchLocale);
			});
		
		changeLangCorse = new JRadioButtonMenuItem(lang.getString("corsicaLang"));
		changeLangCorse.addActionListener(e -> {
			this.langInternational.setFileLang(corseLocale);
			});
		
		if(langInternational.getLanguage().equals("en")) {
			changeLangEnglish.setSelected(true);
		} else if(langInternational.getLanguage().equals("fr")){
			changeLangFrench.setSelected(true);
		} else if(langInternational.getLanguage().equals("co")){
			changeLangCorse.setSelected(true);
		}
		
		ButtonGroup radioGroupLang = new ButtonGroup();
		radioGroupLang.add(changeLangEnglish);
		radioGroupLang.add(changeLangFrench);
		radioGroupLang.add(changeLangCorse);
		
		menuFirstEntry.add(changeLangEnglish);
		menuFirstEntry.add(changeLangFrench);
		menuFirstEntry.add(changeLangCorse);
	}


	/**
	 * Build the menu item to save the status of the contact list
	 */
	private void buildSaveButtonMenu() {
		saveAction = new ActionSave(lang.getString("save"), new ImageIcon(Object.class.getResource("/resources/icons/icons8-sauvegarder.png")), lang.getString("tipSave"), listContactModel);
		
		saveMenuItem = new JMenuItem(saveAction);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		menuFirstEntry.add(saveMenuItem);
	}
	
	/**
	 * Build the dialog box used to update a selected contact
	 */
	private void updateAction() {
		String updateAction = (String) JOptionPane.showInputDialog(
				this, lang.getString("questionUpdateContact"), lang.getString("titleUpdateContact"), 
				JOptionPane.PLAIN_MESSAGE, null, null, selectedKey);
	       if (updateAction != null) {
	    	   listContactModel.changeContact(selectedKey, updateAction, selectedValue);
	       }
	}
	
	/**
	 * Build the dialog box used to delete a selected contact
	 */
	private void deleteAction() {
		int deleteAction = JOptionPane.showConfirmDialog(this, lang.getString("questionDelete"), lang.getString("titleDelete"), JOptionPane.YES_NO_OPTION);  
		if (deleteAction == 0) {
    	   listContactModel.deleteContact(selectedKey);
    	   listContact.setSelectedIndex(0);
    	   if(listContact.getSelectedIndex() == -1) {
    		   selectedKey = "";
    		   selectedValue = "";
    	   } else {    	 	   
    		   selectedKey = (String) listContact.getSelectedValue();
    	   }
       }
	}
	
	/**
	 * Build the menu entry for 'Edit' with the buttons that will allow the user to edit the contact list
	 */
	private void buildSecondMenuEntryEdit() {		
		menuSecondEntry = new JMenu(lang.getString("edit"));
		menuSecondEntry.setMnemonic(KeyEvent.VK_E);

        buildUpdateContactButtonMenu();
		
		buildDeleteContactButtonMenu();
		
		menuBar.add(menuSecondEntry);
	}

	/**
	 * Build the button with the delete action
	 */
	private void buildDeleteContactButtonMenu() {
		deleteMenuItem = new JMenuItem(lang.getString("delete"));
		deleteMenuItem.addActionListener(e->{
        		deleteAction();
        	;}
		);
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		
		menuSecondEntry.add(deleteMenuItem);
	}

	/**
	 * Build the button with the update a contact action
	 */
	private void buildUpdateContactButtonMenu() {
		updateMenuItem = new JMenuItem(lang.getString("update"));
        updateMenuItem.addActionListener(e->{
        		updateAction();
        	;}
        );
		updateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		
		menuSecondEntry.add(updateMenuItem);
	}
	
	/**
	 * Build the menu bar
	 */
	private void buildMenu() {
		menuBar = new JMenuBar();
		
		buildFirstMenuEntryFile();
		buildSecondMenuEntryEdit();
		
		setJMenuBar(menuBar);
		
		buildToolBar();
	}

	/**
	 * Build the toolbar with the save action
	 */
	private void buildToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		buttonSav = new JButton(saveAction);
		toolBar.add(buttonSav);
		add(toolBar, BorderLayout.PAGE_START);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(listContact.getSelectedIndex() == -1) {
			listContact.setSelectedIndex(0);
			selectedKey = (String) listContact.getSelectedValue();
		} else {			
			selectedKey = (String) listContact.getSelectedValue();
			selectedValue = (String) listContactModel.getValue(listContact.getSelectedValue().toString());		
		}
		contactInfoField.setText(selectedValue);
	}

	@Override
	public void notifyObserver(Internationalization intern) {
		this.lang = intern.getFile();
		
		this.addButton.setText(lang.getString("buttonAdd"));
		this.validateButton.setText(lang.getString("buttonValidate"));
		
		this.newContactLabel.setText(lang.getString("labelAdd"));
		this.updateContactInfoLabel.setText(lang.getString("labelUpdateContactInfo"));
		
		this.menuSecondEntry.setText(lang.getString("edit"));
		this.updateMenuItem.setText(lang.getString("update"));
		this.deleteMenuItem.setText(lang.getString("delete"));
		this.menuFirstEntry.setText(lang.getString("file"));
		
		this.saveAction.setText(lang.getString("save"));
		this.saveAction.setDescription(lang.getString("tipSave"));
		
		this.changeLangEnglish.setText(lang.getString("englishLang"));
		this.changeLangFrench.setText(lang.getString("frenchLang"));
		this.changeLangCorse.setText(lang.getString("corsicaLang"));
		
	}
	
	// Main
	public static void main(String[] args) {
		AdresseBook agenda = new AdresseBook();
		
		agenda.setSize(600, 500);
		agenda.setLocationRelativeTo(null);
		agenda.setTitle("Agenda");
		
		agenda.setDefaultCloseOperation(EXIT_ON_CLOSE);
		agenda.saveOnExitWindow();
		
		agenda.buildMenu();
		agenda.buildPane();
		
		Internationalization interna = Internationalization.getInstance();
		interna.addObserver(agenda);
		
		agenda.setVisible(true);
	}


}
