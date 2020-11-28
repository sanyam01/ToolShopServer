package Server.Model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ElectricalItem.class, name = "electricalItem"),
		@Type(value = NonElectricalItem.class, name = "nonElectricalItem") })

/**
 * Abstract class of Items which is super class for electrical and
 * non-electrical type of items. This class is serializable.
 * 
 * @author Neha singh
 *
 */
public abstract class Items implements Serializable {

	/**
	 * itemID represents the ID of the item. Each item has unique ID
	 */
	private int itemID;

	/**
	 * itemType represents the type of the item i.e. Electrical and non-electrical
	 */
	private String itemType;

	/**
	 * itemName represents the name of the item
	 */
	private String itemName;

	/**
	 * itemQuantity tells the quantity of the item in the inventory
	 */
	private int itemQuantity;

	/**
	 * itemPrice tells the price of the item
	 */
	private float itemPrice;

	/**
	 * supplierID represents the ID of the supplier
	 */
	private int supplierID;

	private Suppliers sup;

	/**
	 * Constructs a constructor of Items and assigns all the values to its member
	 * variables
	 * 
	 * @param itemID       iD of an item
	 * @param itemName     name of an item
	 * @param itemQuantity quantity left in the stock
	 * @param itemPrice    price of an item
	 * @param supplierID   iD of the supplier that sells that item
	 */
	public Items(int itemID, String itemName, int itemQuantity, float itemPrice, String itemType, int supplierID) {

		this.setItemID(itemID);
		this.setItemName(itemName);
		this.setItemPrice(itemPrice);
		this.setItemQuantity(itemQuantity);
		this.setItemType(itemType);
		this.setSupplierID(supplierID);
//		this.setPowerType(null);
	}

	public Items() {

	}

	/**
	 * toString method overrides the method of the super class.
	 * 
	 * @return string comprising the tool name, id, price and supplier id of the
	 *         item.
	 */
	@Override
	public String toString() {
		String s = this.getItemName();
		s = "itemName: " + s + ", itemID: " + this.getItemID() + ", supplierID: " + this.getSupplierID()
				+ ", quantity: " + this.getItemQuantity() + ", price : " + this.getItemPrice();
		return s;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int decreaseQuantity(Order order, int itemQty) {

		int amount_ordered = 0;

		OrderLines ol = null;
		String s = "\nNo new OrderLine generated. Printing order: \n";
		s = s + "....................................................................\n\n";
		if (itemQty > 0) {
			this.setItemQuantity(itemQty - 1);

		}

		if (itemQty < 40) {
			int initiallyItem = itemQty;
			ol = new OrderLines(this, 50 - initiallyItem);

			order.addOrderLine(ol);
			this.setItemQuantity(50);
			amount_ordered = ol.getAmount();

//			s = "\nNew order line generated. Updated Order for the day is:\n\n";
//			s = s + "....................................................................\n";
//			s = s + "Order Id : " + order.getOrderId() + "\n" + "Order date : " + order.getDate() + "\n\n";
//			
//			

		}

//		else {
//			s = s + "Order Id : " + order.getOrderId() + "\n" + "Order date : " + order.getDate() + "\n\n";
//			
//		}
////		for (OrderLines k : order.getOrderLines()) {
////			s = s + "ItemName: " + k.getItem().getItemName() + ", Quantity ordered: " + k.getAmount()
////					+ ", Supplier name: " + k.getItem().getSup().getSupplierName() + "\n";
////			
////			
////			
////			
////		}
//
//		s = s + "\n....................................................................\n\n";

//		System.out.println("New qty ordered for item is: " + this.getItemID() + " " + ol.getAmount());

		return amount_ordered;

	}

	public Suppliers getSup() {
		return sup;
	}

	public void setSup(Suppliers sup) {
		this.sup = sup;
	}

	public void addSupplier(SupplierList list) {
		for (Suppliers s : list.getList()) {
			if (s.getSupplierID() == this.getSupplierID())
				this.sup = s;

		}
	}

}
