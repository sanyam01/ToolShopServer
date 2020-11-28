package Server.Controller.DatabaseController;

import java.sql.*;
import java.util.ArrayList;
import Server.Model.Commercial;
import Server.Model.Customer;
import Server.Model.ElectricalItem;
import Server.Model.Items;
import Server.Model.NonElectricalItem;
import Server.Model.Residential;

/**
 * Class consists of prepared statements to retrieve data from database from
 * different tables of Items, Customers and suppliers.
 * 
 * @author Neha Singh
 *
 */
public class InventoryDBManager {

	// Attributes

	private Statement myStmt; // object of type statement from JDBC class that enables the creation "Query
								// statements"

	private ResultSet myres;// object of type ResultSet from the JDBC class that stores the result of the
							// query

	private PreparedStatement prepStatment;

	private Driver myDriver;

	public InventoryDBManager(Driver myDriver) {

		this.myDriver = myDriver;

	}

	/**
	 * Closes sql connections
	 */
	public void closeSqlConn() {

		try {

			myStmt.close();
			myres.close();
			prepStatment.close();
			myDriver.getMyConn().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prepared statement to get Item by given Id.
	 * 
	 * @param itemId
	 * @return
	 */
	public ArrayList<Items> getItemByIdPreparedStatement(int itemId) {

		String query = "SELECT  item.item_id,  item_name,  item_quantity,  item_price,  item_type,  supplier_id , electrical_item.power_type\r\n"
				+ "          FROM item \r\n"
				+ "          LEFT  JOIN electrical_item  ON item.item_id=electrical_item.item_id \r\n"
				+ "          where item.item_id = ?";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			prepStatment.setInt(1, itemId);
			myres = prepStatment.executeQuery();

			ArrayList<Items> items = new ArrayList<Items>();

			while (myres.next()) {

				if (myres.getString("power_type") != null) {

					items.add(new ElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id"), myres.getString("power_type")));

				}
				if (myres.getString("power_type") == null) {

					items.add(new NonElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id")));

				}

			}

			return items;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Prepared statement to get Item by given name.
	 * 
	 * @param itemName
	 * @return
	 */
	public ArrayList<Items> getItemByNamePreparedStatement(String itemName) {
		String query = "SELECT  item.item_id,  item_name,  item_quantity,  item_price,  item_type,  supplier_id , electrical_item.power_type\r\n"
				+ "          FROM item \r\n"
				+ "          LEFT  JOIN electrical_item  ON item.item_id=electrical_item.item_id \r\n"
				+ "          where item.item_name like ?";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
//			prepStatment.setString(1, itemName);
			prepStatment.setString(1, "%" + itemName + "%");
			myres = prepStatment.executeQuery();

			ArrayList<Items> items = new ArrayList<Items>();

			while (myres.next()) {

				if (myres.getString("power_type") != null) {

					items.add(new ElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id"), myres.getString("power_type")));

				}
				if (myres.getString("power_type") == null) {

					items.add(new NonElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id")));

//					
				}

			}

			return items;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Prepared statement to get order count.
	 * 
	 * @return
	 */
	public int getOrderCountPreparedStatement() {

		String query = "select * from orders";

		int rowCount = 0;
		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			myres = prepStatment.executeQuery();
			while (myres.next()) {

				rowCount++;

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Prepared statement to get Item quantity for given Id.
	 * 
	 * @param itemId
	 * @return
	 */
	public ArrayList<Items> getItemQtyPreparedStatement(int itemId) {

		String query = "SELECT  item.item_id,  item_name,  item_quantity,  item_price,  item_type,  supplier_id , electrical_item.power_type\r\n"
				+ "          FROM item \r\n"
				+ "          LEFT  JOIN electrical_item  ON item.item_id=electrical_item.item_id  ";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			prepStatment.setInt(1, itemId);
			myres = prepStatment.executeQuery();

			ArrayList<Items> itemArrayList = new ArrayList<Items>();

			while (myres.next()) {

			}

			return itemArrayList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Prepared statement to get Item list present in the database.
	 * 
	 * @return
	 */
	public ArrayList<Items> getItemListPreparedStatemen() {

		String query = "SELECT  item.item_id,  item_name,  item_quantity,  item_price,  item_type,  supplier_id , electrical_item.power_type\r\n"
				+ "          FROM item \r\n"
				+ "          LEFT  JOIN electrical_item  ON item.item_id=electrical_item.item_id  ";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			myres = prepStatment.executeQuery();

			ArrayList<Items> itemArrayList = new ArrayList<Items>();

			while (myres.next()) {

				if (myres.getString("power_type") != null) {
					itemArrayList.add(new ElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id"), myres.getString("power_type")));
				}
				if (myres.getString("power_type") == null) {
					itemArrayList.add(new NonElectricalItem(myres.getInt("item_id"), myres.getString("item_name"),
							myres.getInt("item_quantity"), myres.getFloat("item_price"), myres.getString("item_type"),
							myres.getInt("supplier_id")));
				}

			}

			return itemArrayList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Prepared statement to get Customer for given Id.
	 * 
	 * @param customerId
	 * @return
	 */
	public ArrayList<Customer> getCustomerPreparedStatementId(int customerId) {

//		System.out.println("getting customer based on customer id");

		String query = "Select * from customer where customer_id = ?";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			prepStatment.setInt(1, customerId);
			myres = prepStatment.executeQuery();

			ArrayList<Customer> customerArrayList = new ArrayList<Customer>();

			while (myres.next()) {

				if (myres.getString("customer_type").contentEquals("R")) {
					customerArrayList.add(new Residential(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}
				if (myres.getString("customer_type").contentEquals("C")) {
					customerArrayList.add(new Commercial(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}

			}

			return customerArrayList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Prepared statement to get customer for given type.
	 * 
	 * @param customerType
	 * @return
	 */
	public ArrayList<Customer> getCustomerPreparedStatementType(String customerType) {
//		System.out.println("getting customer based on customer type");

		String query = "Select * from customer where customer_type = ?";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			prepStatment.setString(1, customerType);
			myres = prepStatment.executeQuery();

			ArrayList<Customer> customerArrayList = new ArrayList<Customer>();

			while (myres.next()) {

				if (myres.getString("customer_type").contentEquals("R")) {
					customerArrayList.add(new Residential(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}
				if (myres.getString("customer_type").contentEquals("C")) {
					customerArrayList.add(new Commercial(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}

//				System.out.println("search customer result in IDB: " + customerArrayList);

			}

			return customerArrayList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Prepared statement to get get customer for given last name.
	 * 
	 * @param lname
	 * @return
	 */
	public ArrayList<Customer> getCustomerPreparedStatementLname(String lname) {
//		System.out.println("getting customer based on lastname ");

		String query = "Select * from customer where lname like ?";

		try {
			prepStatment = myDriver.getMyConn().prepareStatement(query);
			prepStatment.setString(1, "%" + lname + "%");
			myres = prepStatment.executeQuery();

			ArrayList<Customer> customerArrayList = new ArrayList<Customer>();

			while (myres.next()) {

				if (myres.getString("customer_type").contentEquals("R")) {
					customerArrayList.add(new Residential(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}
				if (myres.getString("customer_type").contentEquals("C")) {
					customerArrayList.add(new Commercial(myres.getInt("customer_id"), myres.getString("fname"),
							myres.getString("lname"), myres.getString("address"), myres.getString("postal_code"),
							myres.getString("phone_number"), myres.getString("customer_type")));
				}

//				System.out.println("search customer result in IDB: " + customerArrayList);

			}

			return customerArrayList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Prepared statement to update customer for given input values.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param postalCode
	 * @param phoneNumber
	 * @param type
	 * @param id
	 * @return
	 */
	public int updateCustomer(String firstName, String lastName, String address, String postalCode, String phoneNumber,
			String type, int id) {
		String updateClient = "UPDATE customer SET  fname=?,lname=?,address=?,postal_code=?,phone_number=?,customer_type=?"
				+ " WHERE customer_id=?";
		int rowCount = 0;

		try {
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(updateClient);
			pStat.setString(1, firstName);
			pStat.setString(2, lastName);
			pStat.setString(3, address);
			pStat.setString(4, postalCode);
			pStat.setString(5, phoneNumber);
			pStat.setString(6, type);
			pStat.setInt(7, id);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Delete client.
	 *
	 * @param id the id
	 */
	public int deleteCustomer(int id) {
		String deleteClient = "DELETE FROM customer WHERE customer_id=?";
		int rowCount = 0;
		try {
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(deleteClient);
			pStat.setInt(1, id);
			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;
	}

	/**
	 * Prepared statement to update item for given input id and quantity value.
	 * 
	 * @param itemId
	 * @param itemqty
	 * @return
	 */
	public int updateItemQtyPrepStatement(int itemId, int itemqty) {

		String query = "update item set item_quantity = ? where item_id = ?";

		int rowCount = 0;

		try {
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, itemqty);
			pStat.setInt(2, itemId);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}
}
