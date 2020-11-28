package Server.Model;

import java.util.ArrayList;

/**
 * Class manages the operations to decrease item quantity and manage the
 * inventory.
 * 
 * @author Neha Singh
 *
 */
public class Inventory {

	/**
	 * ArrayList<Items> listItems represents all the tools in the shop
	 */
	private ArrayList<Items> listItems;

	/**
	 * Order theOrder represents the order of the items
	 */
	private Order theOrder;

	private boolean orderLineFlag;

	/**
	 * Constructs an object of the class Inventory. It also generates an order for
	 * the day
	 * 
	 * @param listItems an array list of items/tools
	 */
	public Inventory(ArrayList<Items> listItems) {

		this.setListItems(listItems);
		theOrder = new Order();
		this.orderLineFlag = false;

	}

	/**
	 * decreaseQuantity calls the method of class Items to decrease the quantity by
	 * one
	 *
	 * 
	 * @param name of item whose item is to be decremented by one
	 * @return the order for the day
	 */
	public String decreaseQuantity(String name) {

		String s = "Item not found";
		for (Items i : listItems) {
			if (i.getItemName().equalsIgnoreCase(name))
				System.out.println();
//				s = i.decreaseQuantity(this.getTheOrder());
		}

		return s;

	}

	/**
	 * decreaseQuantity calls the method of class Items to decrease the quantity by
	 * one
	 * 
	 * @param id
	 * @return
	 */
	public int decreaseQuantity(int id) {
		System.out.println("decreaing qty for item id: " + id);
//		listItems = dbController.getItemById(id);
		int orderedQty = 0;

		for (Items i : listItems) {
			if (i.getItemID() == id) {

				System.out.println("calling method decreaseQuantity in item with parameters: "
						+ this.getTheOrder().getOrderId() + " " + i.getItemQuantity());
				orderedQty = i.decreaseQuantity(this.getTheOrder(), i.getItemQuantity());
			}

		}

		return orderedQty;

	}

	public Order getTheOrder() {
		return theOrder;
	}

	public void setTheOrder(Order theOrder) {
		this.theOrder = theOrder;
	}

	public ArrayList<Items> getListItems() {
		return listItems;
	}

	public void setListItems(ArrayList<Items> listItems) {
		this.listItems = listItems;
	}

	public boolean isOrderLineFlag() {
		return orderLineFlag;
	}

	public void setOrderLineFlag(boolean orderLineFlag) {
		this.orderLineFlag = orderLineFlag;
	}

}
