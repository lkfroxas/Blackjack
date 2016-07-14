package view;

/**
 * Observer interface for {@link #Table <code>Table</code>}. Allows a class
 * implementing this interface, when added to the Table through
 * {@link #Table.addTableListener(TableListener tableListener)
 * <code>addTableListener(...)</code>} to respond to the player pressing
 * the Hit, Stay, and Reset buttons.
 * 
 * @author Lowell Roxas
 *
 */
public interface TableListener {
	public void hit();
	public void stay();
	public void newGame();
}