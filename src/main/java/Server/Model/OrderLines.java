package Server.Model;

/**
 * Class for the orderlines generated for the items if the quantity goes below
 * 40.
 * 
 * @author Neha and Sanyan
 *
 */
public class OrderLines {

	/**
	 * item represents the items object for which order line is generated
	 */
	private Items item;

	/**
	 * amount tells the number of items ordered
	 */
	private int amount;

	/**
	 * Constructs an object of OrderLines.
	 * 
	 * @param item   represents the item object for which order line is generated
	 * @param amount tells the number of items ordered
	 */
	public OrderLines(Items item, int amount) {

		this.setItem(item);
		this.setAmount(amount);

	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
