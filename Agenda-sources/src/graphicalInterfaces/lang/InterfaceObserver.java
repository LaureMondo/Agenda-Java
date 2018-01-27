package graphicalInterfaces.lang;

/**
 * The interface that allows the frame to listen to the changes of the lang
 * @author Laure
 *
 */
public interface InterfaceObserver {
	
	/**
	 * Notify all the observers of the lang changes and give them the new lang
	 * @param intern : the lang to apply. See {@link Internationalization} 
	 */
	public void notifyObserver(Internationalization intern);

}
