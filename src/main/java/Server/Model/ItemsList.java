package Server.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for the list of Items
 * 
 * @author Neha Singh
 *
 */
public class ItemsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list of customers
	 */
	private ArrayList<Items> itemsList;

	public ItemsList(ArrayList<Items> list) {
		this.setItemsList(list);
	}

	public ItemsList() {

	}

	public ArrayList<Items> getItemsList() {
		return itemsList;
	}

	public void setItemsList(ArrayList<Items> itemsList) {
		this.itemsList = itemsList;
	}

	public void addItems(Items item) {
		itemsList.add(item);

	}

}
