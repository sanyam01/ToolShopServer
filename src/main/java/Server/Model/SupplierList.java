package Server.Model;

import java.util.ArrayList;

/**
 * Class for list of suppliers.
 * 
 * @author Neha Singh
 *
 */
public class SupplierList {
	/**
	 * list represents the list of the suppliers
	 */
	private ArrayList<Suppliers> list;

	public SupplierList(ArrayList<Suppliers> list) {
		this.list = list;
	}

	/**
	 * addSupplier(Suppliers supplier) adds the supplier to the list of suppliers
	 * 
	 * @param supplier represents the supplier
	 */
	public void addSupplier(Suppliers supplier) {
		list.add(supplier);

	}

	/**
	 * addItems calls the method of class Suppliers to add the list of the items to
	 * each supplier it sells
	 * 
	 * @param itemList is a list of all the items
	 */
	public void addItems(ArrayList<Items> itemList) {
		for (Suppliers s : this.list)
			s.addItems(itemList);

	}

	public ArrayList<Suppliers> getList() {
		return list;
	}

	public void setList(ArrayList<Suppliers> list) {
		this.list = list;
	}

}