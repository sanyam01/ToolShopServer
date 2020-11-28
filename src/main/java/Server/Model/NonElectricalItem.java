package Server.Model;

/**
 * Class extends superclass Items, this is subclass of type non-electrical
 * items.
 * 
 * @author Neha Singh
 *
 */
public class NonElectricalItem extends Items {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonElectricalItem(int itemID, String itemName, int itemQuantity, float itemPrice, String itemType,
			int supplierID) {
		super(itemID, itemName, itemQuantity, itemPrice, itemType, supplierID);
		// TODO Auto-generated constructor stub
	}

	public NonElectricalItem() {

	}

}
