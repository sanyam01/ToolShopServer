package Server.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for list of customers present in the database.
 * @author Neha Singh
 *
 */
public class CustomerList implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list of customers
	 */
	private ArrayList<Customer> customerList;
	
	public CustomerList(ArrayList<Customer> cust) {
		this.customerList = cust;

		
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}
	
	
	public void addCustomers(Customer customer) {
		customerList.add(customer);

	}
	
	
}
