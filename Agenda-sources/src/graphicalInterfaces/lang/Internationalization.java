package graphicalInterfaces.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The singleton which manages language changes, and tells the graphical interface to update its texts.
 * Having a singleton allows only one instance of this class, no need to create new instance on each changes.
 * @author Laure
 *
 */
public class Internationalization {
	
	public static Internationalization instance = null;
	
	private String language, country;
	private Locale currentLocale;
	public ResourceBundle langFile;
	
	private Locale frenchLocale = new Locale("fr", "FR");
	private Locale englishLocale = new Locale("en", "EN");
	private Locale corseLocale = new Locale("co", "FR");
	
	private List<InterfaceObserver> listeners;
	
	/**
	 * Sets the default lang of the application and allows the frame to listen to lang changes
	 */
	private Internationalization() {
		this.language = System.getProperty("user.language");
		this.country = System.getProperty("user.country");
		this.currentLocale = new Locale(language, country);
		
		this.langFile = ResourceBundle.getBundle("MessageBundle", currentLocale);
		
		this.listeners = new ArrayList<InterfaceObserver>();
		
	}
	
	/**
	 * The only instance of the lang class {@link Internationalization}.
	 * @return instance : which allows only one instance of the lang class in the application
	 */
	public static Internationalization getInstance(){
		if(instance == null) {
			instance = new Internationalization();
		}
		return instance;
	}
	
	/**
	 * @return String language : the selected language for the application
	 */
	public String getLanguage() {
		return this.language;
	}
	
	/**
	 * @return langFile : the selected resource bundle for the lang application
	 */
	public ResourceBundle getFile() {
		return this.langFile;
	}
	
	/**
	 * Sets the application language and tells the observers to update their languages
	 * @param local : the language
	 */
	public void setFileLang(Locale local) {
		this.langFile = ResourceBundle.getBundle("MessageBundle", local);
		this.notifyAllObserver();
	}
	
	/**
	 * Tells all the observers to update their status because the lang had changed.
	 */
	public void notifyAllObserver() {
		for (InterfaceObserver interfaceObserver : listeners) {
			interfaceObserver.notifyObserver(this);
		}
	}

	/**
	 * Add a new observer to the list
	 * @param obs : the observer to add. See {@link InterfaceObserver}
	 */
	public void addObserver(InterfaceObserver obs) {
		// Est observé par l'interface, au moindre changement, il y aura mise à jour
		this.listeners.add(obs);
		obs.notifyObserver(this);
	}
	
}
