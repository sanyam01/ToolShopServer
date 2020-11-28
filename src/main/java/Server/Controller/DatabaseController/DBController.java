package Server.Controller.DatabaseController;

import java.util.ArrayList;
import Server.Model.Customer;
import Server.Model.Items;

/**
 * Class controls the database connection and data exchange between frontend and
 * backend.
 * 
 * @author Neha Singh
 *
 */
public class DBController {

	private InventoryDBManager dbManager;
	private CreateDBTables createDBTables;

	/**
	 * Constructs the inventory manager, instantiates the driver class to load
	 * database.
	 */
	public DBController() {

		Driver myDriver = new Driver();

		setDbManager(new InventoryDBManager(myDriver));
		createDBTables = new CreateDBTables(myDriver);
		createDB(); // create database if not exists
		createTables(); // create tables
		fillTables(); // fill tables

	}

	public InventoryDBManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(InventoryDBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * Get customer by id.
	 * 
	 * @param customerId
	 * @return
	 */
	public ArrayList<Customer> getCustomerbyId(int customerId) {
		return dbManager.getCustomerPreparedStatementId(customerId);
	}

	/**
	 * Get customers by customer type - R or C
	 * 
	 * @param customerType
	 * @return
	 */
	public ArrayList<Customer> getCustomerbyType(String customerType) {
		return dbManager.getCustomerPreparedStatementType(customerType);
	}

	/**
	 * Get customer by last name
	 * 
	 * @param lname
	 * @return
	 */
	public ArrayList<Customer> getCustomerbyLname(String lname) {
		return dbManager.getCustomerPreparedStatementLname(lname);
	}

	/**
	 * updates customer details with given arguments
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
	public boolean updateCustomer(String firstName, String lastName, String address, String postalCode,
			String phoneNumber, String type, int id) {

		boolean updateFlag = false;

		int rowcount = dbManager.updateCustomer(firstName, lastName, address, postalCode, phoneNumber, type, id);
		if (rowcount == 1) {
			// customer updated successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	/**
	 * Insert customer information into database
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
	public boolean insertCustomer(String firstName, String lastName, String address, String postalCode,
			String phoneNumber, String type, int id) {

		boolean updateFlag = false;

		int rowcount = createDBTables.insertCustomerPreparedStatment(id, firstName, lastName, address, postalCode,
				phoneNumber, type);
		if (rowcount == 1) {
			// customer inserted successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	/**
	 * Delete the given customer id from db.
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCustomer(int id) {

		boolean updateFlag = false;
		int rowcount = dbManager.deleteCustomer(id);
		if (rowcount == 1) {
			// customer deleted successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	/**
	 * Controls Creation of database
	 */
	public void createDB() {

		createDBTables.createDB();

	}

	/**
	 * Controls creation of tables
	 */
	public void createTables() {
		createDBTables.createCustomerTable();
		createDBTables.createSupplierTable();
		createDBTables.createItemTable();
		createDBTables.createPurchaseTable();
		createDBTables.createOrderTable();
		createDBTables.createOrderLineTable();
		createDBTables.createElectricalItemTable();
		createDBTables.createInternationalSuppTable();
	}

	/**
	 * controls filling database tables
	 */
	public void fillTables() {
		createDBTables.fillCustomerTable();

		createDBTables.fillSupplierTable();
		createDBTables.fillIntSupplierTable();
		createDBTables.fillItemTable();
		createDBTables.fillElecItemTable();
	}

	/**
	 * Gets list of items in database
	 * 
	 * @return
	 */
	public ArrayList<Items> getItemList() {
		return dbManager.getItemListPreparedStatemen();
	}

	/**
	 * Gets items by ID
	 * 
	 * @param itemId
	 * @return
	 */
	public ArrayList<Items> getItemById(int itemId) {
		return dbManager.getItemByIdPreparedStatement(itemId);
	}

	/**
	 * Gets item by name
	 * 
	 * @param itemName
	 * @return
	 */
	public ArrayList<Items> getItemByName(String itemName) {
		return dbManager.getItemByNamePreparedStatement(itemName);
	}

//	public ArrayList<Items> getItemQty(String itemName) {
//		return dbManager.getItemQtyPreparedStatement(itemName);
//	}

	/**
	 * Gets item quantity for given item id
	 * 
	 * @param itemId
	 * @return
	 */
	public ArrayList<Items> getItemQty(int itemId) {
		return dbManager.getItemQtyPreparedStatement(itemId);
	}

	/**
	 * Updates Items quantity of given id to given value
	 * 
	 * @param itemQty
	 * @param itemId
	 * @return
	 */
	public boolean updateItemQty(int itemQty, int itemId) {

		boolean updateFlag = false;

		int rowcount = dbManager.updateItemQtyPrepStatement(itemId, itemQty);
		if (rowcount == 1) {
			// customer updated successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	/**
	 * Adds orderline for given item in dabatabase
	 * 
	 * @param orderId
	 * @param toolId
	 * @param supplierId
	 * @param quantity
	 * @return
	 */
	public boolean addOrderLine(int orderId, int toolId, int supplierId, int quantity) {
		boolean updateFlag = false;
		int rowcount = createDBTables.insertOrderLinePrepStatment(orderId, toolId, supplierId, quantity);
		if (rowcount == 1) {
			// customer updated successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	public boolean addOrder(int orderId, String date) {
		boolean updateFlag = false;
		int rowcount = createDBTables.insertOrderPrepStatment(orderId, date);
		if (rowcount == 1) {
			// customer updated successfully
			updateFlag = true;
		}

		return updateFlag;
	}

	/**
	 * Gets counts of orders placed for a day
	 * 
	 * @return
	 */
	public int getOrderCount() {
		return dbManager.getOrderCountPreparedStatement();
	}

}
