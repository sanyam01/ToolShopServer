package Server.Controller.DatabaseController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 * Class handles the database creation and loading of data into the tables from
 * the mentioned text files. Creates the corresponding tables and fills the
 * tables with data from text files.
 * 
 * @author Neha Singh
 *
 */
public class CreateDBTables {

	private Driver myDriver;

	private Statement myStmt; // object of type statement from JDBC class that enables the creation "Query
	// statements"

	private String databaseName = "inventory";

	public CreateDBTables(Driver myDriver) {

		this.myDriver = myDriver;

	}

	/**
	 * Create db.
	 */
	public void createDB() {

		String sql_temp = "drop database if exists " + databaseName;

		String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
		String sql_new = "use  " + databaseName;

		try {
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(sql);
			// pStat.setString(1,databaseName);
			pStat.executeUpdate(sql_temp);
			pStat.executeUpdate(sql);
			pStat.executeUpdate(sql_new);
		} catch (SQLException e) {
			System.out.println("DB already exists");
			// e.printStackTrace();
		}
	}

	/**
	 * Create Customer table.
	 */
	public void createCustomerTable() {

		String sql = "create table customer " + "(customer_id integer not null, " + "fname varchar(20) not null, "
				+ "lname varchar(20) not null, " + "address varchar(50), " + "postal_code varchar(7), "
				+ "phone_number varchar(12), " + "customer_type char(10), " + "primary key (customer_id)\r\n"
				+ "		) ";

		try {

			myStmt = myDriver.getMyConn().createStatement();
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Created Customer table in given database...");
	}

	/**
	 * Create supplier table.
	 */
	public void createSupplierTable() {

		/*
		 * DROP TABLE IF EXISTS supplier; CREATE TABLE supplier ( supplier_id integer
		 * not null, supplier_name varchar(25), supplier_type char(10), address
		 * varchar(25), company_name varchar(25), phone_number varchar(25), primary key
		 * (supplier_id) );
		 */
		String sql = "CREATE TABLE supplier ( " + " supplier_id       integer not null, "
				+ " supplier_name		varchar(25) unique, " + " supplier_type 	varchar(25), "
				+ " address      		varchar(50), " + " company_name 		varchar(25), "
				+ "phone_number 	varchar(25), " + " primary key (supplier_id) \r\n" + "  ) ";

		try {
			myStmt = myDriver.getMyConn().createStatement();

			myStmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Created Supplier table in given database...");

	}

	/**
	 * Create Item table
	 */
	public void createItemTable() {

		/*
		 * DROP TABLE IF EXISTS item; CREATE TABLE item ( item_id integer not null,
		 * item_name varchar(25), item_quantity integer, item_price decimal(10,2),
		 * item_type varchar(25), supplier_id integer, primary key (item_id), foreign
		 * key (supplier_id) references supplier(supplier_id) );
		 */

		String sql = "CREATE TABLE item ( item_id integer not null, item_name varchar(25) unique,\r\n"
				+ "		item_quantity integer, item_price decimal(10,2), item_type varchar(25),\r\n"
				+ "		  supplier_id integer, primary key (item_id), foreign key (supplier_id)\r\n"
				+ "		  references supplier(supplier_id) );";
		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Created Item table in given database...");
	}

	/**
	 * Create purchase table
	 */
	public void createPurchaseTable() {

		/*
		 * DROP TABLE IF EXISTS purchase; CREATE TABLE purchase ( item_id integer not
		 * null, customer_id integer not null, primary key (item_id,customer_id),
		 * foreign key (item_id) references item(item_id), foreign key (customer_id)
		 * references customer(customer_id) );
		 */
		String sql = "create table purchase " + "(item_id integer not null, " + "customer_id  integer not null, "

				+ "primary key (item_id,customer_id), " + " foreign key (item_id) references item(item_id), "
				+ " foreign key (customer_id) references customer(customer_id)\r\n" + "			) ";

		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("Created Purchase table in given database...");
	}

	/**
	 * Create order table
	 */
	public void createOrderTable() {

		/*
		 * DROP TABLE IF EXISTS orders; CREATE TABLE order ( order_id integer not null,
		 * order_date date, primary key (order_id)
		 * 
		 * );
		 */
		String sql = "create table orders " + "(order_id integer not null, " + "order_date  date, "

				+ "primary key (order_id) \r\n" + "			) ";

		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created Orders table in given database...");
	}

	/**
	 * Create orderline table
	 */
	public void createOrderLineTable() {

		/*
		 * DROP TABLE IF EXISTS orderline; CREATE TABLE orderline ( order_id integer not
		 * null, item_id integer not null, supplier_id integer , amount_ordered integer,
		 * primary key (order_id, item_id) foreign key (item_id) references
		 * item(item_id), foreign key (order_id) references order(order_id) foreign key
		 * (supplier_id) references supplier(supplier_id)
		 * 
		 * );
		 */
		String sql = "CREATE TABLE orderline ( order_id integer not null, item_id integer not null,\r\n"
				+ "		 supplier_id integer , amount_ordered integer, primary key (order_id, item_id),\r\n"
				+ "		 foreign key (item_id) references item(item_id), foreign key (order_id)\r\n"
				+ "		references orders(order_id), foreign key (supplier_id) references\r\n"
				+ "		 supplier(supplier_id)	);";

		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created Orderline table in given database...");
	}

	/**
	 * Create electrical_item table
	 */
	public void createElectricalItemTable() {

		/*
		 * DROP TABLE IF EXISTS electrical_item; CREATE TABLE electrical_item ( item_id
		 * integer not null, power_type varchar(10), primary key (item_id)
		 * 
		 * );
		 */
		String sql = "create table electrical_item " + "(item_id integer not null, " + " power_type varchar(10), "

				+ "primary key (item_id) \r\n" + "			) ";

		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created Electrical_item table in given database...");
	}

	/**
	 * Create international_supplier table
	 */
	public void createInternationalSuppTable() {
//		String sql_temp = "DROP TABLE IF EXISTS international_supplier";

		/*
		 * CREATE TABLE international_supplier ( supplier_id integer not null,
		 * import_tax decimal, primary key (supplier_id)
		 * 
		 * );
		 */
		String sql = "create table international_supplier " + "(supplier_id integer not null, "
				+ " import_tax decimal , "

				+ "primary key (supplier_id) \r\n" + "			) ";

		try {
			myStmt = myDriver.getMyConn().createStatement();
//			myStmt.executeUpdate(sql_temp);
			myStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created International_supplier table in given database...");
	}

	/**
	 * Inserts values in customer table.
	 * 
	 * @param id
	 * @param fName
	 * @param lName
	 * @param address
	 * @param postal_code
	 * @param phone_number
	 * @param customer_type
	 * @return
	 */
	public int insertCustomerPreparedStatment(int id, String fName, String lName, String address, String postal_code,
			String phone_number, String customer_type) {

		int rowCount = 0;
		try {
			String query = "INSERT INTO customer ( customer_id,  fName,  lName,  address,  postal_code,  phone_number, customer_type) values (?,?,?,?,?,?,?)";
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setString(2, fName);
			pStat.setString(3, lName);
			pStat.setString(4, address);
			pStat.setString(5, postal_code);
			pStat.setString(6, phone_number);
			pStat.setString(7, customer_type);
			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);

			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("Added data in customer table");
		return rowCount;

	}

	/**
	 * Inserts values in Item table.
	 * 
	 * @param id
	 * @param itemName
	 * @param itemQty
	 * @param itemPrice
	 * @param itemType
	 * @param suppId
	 * @return
	 */
	public int insertItemPreparedStatment(int id, String itemName, int itemQty, double itemPrice, String itemType,
			int suppId) {

		int rowCount = 0;
		try {
			String query = "INSERT INTO item ( item_id,  item_name,  item_quantity,  item_price,  item_type,  supplier_id) values (?,?,?,?,?,?)";
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setString(2, itemName);
			pStat.setInt(3, itemQty);
			pStat.setDouble(4, itemPrice);
			pStat.setString(5, itemType);
			pStat.setInt(6, suppId);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
//			System.out.println("Added data in item table");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Inserts values in Supplier table.
	 * 
	 * @param id
	 * @param suppName
	 * @param supplierType
	 * @param address
	 * @param companyName
	 * @param salesContact
	 * @return
	 */
	public int insertSupplierPreparedStatment(int id, String suppName, String supplierType, String address,
			String companyName, String salesContact) {

		int rowCount = 0;
		try {
			String query = "INSERT INTO supplier ( supplier_id,  supplier_name,  supplier_type,  address,  company_name,  phone_number) values (?,?,?,?,?,?)";
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setString(2, suppName);
			pStat.setString(3, supplierType);
			pStat.setString(4, address);
			pStat.setString(5, companyName);
			pStat.setString(6, salesContact);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
//			System.out.println("Added data in supplier table");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Insert values in International_supplier table.
	 * 
	 * @param id
	 * @param importTax
	 * @return
	 */
	public int insertIntSupplierPrepStatment(int id, double importTax) {

		int rowCount = 0;
		try {
			String query = "INSERT INTO international_supplier ( supplier_id,  import_tax) values (?,?)";
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setDouble(2, importTax);

			rowCount = pStat.executeUpdate();

			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Insert values in Electrical_item table.
	 * 
	 * @param id
	 * @param powerType
	 * @return
	 */
	public int insertElecItemPrepStatment(int id, String powerType) {

		int rowCount = 0;
		try {
			String query = "INSERT INTO electrical_item ( item_id,  power_type) values (?,?)";
			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, id);
			pStat.setString(2, powerType);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
//			System.out.println("Added data in electrical_item table");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Load data into customer table.
	 */
	public void fillCustomerTable() {
		try {
			Scanner sc = new Scanner(new FileReader("clients.txt"));
			while (sc.hasNext()) {
				String customerInfo[] = sc.nextLine().split(";");
				insertCustomerPreparedStatment(Integer.parseInt(customerInfo[0]), customerInfo[1], customerInfo[2],
						customerInfo[3], customerInfo[4], customerInfo[5], customerInfo[6]);
			}
			System.out.println("Added data in Customer table");
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File " + "clients.txt" + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data into Item table.
	 */
	public void fillItemTable() {
		try {
			Scanner sc = new Scanner(new FileReader("items.txt"));
			while (sc.hasNext()) {
				String items[] = sc.nextLine().split(";");
				insertItemPreparedStatment(Integer.parseInt(items[0]), items[1], Integer.parseInt(items[2]),
						Double.parseDouble(items[3]), items[4], Integer.parseInt(items[5]));
			}
			System.out.println("Added data in Item table");
			sc.close();

		} catch (FileNotFoundException e) {
			System.err.println("File " + "items.txt" + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data into Supplier table.
	 */
	public void fillSupplierTable() {

		try {
			Scanner sc = new Scanner(new FileReader("suppliers.txt"));
			while (sc.hasNext()) {
				String suppliers[] = sc.nextLine().split(";");
				insertSupplierPreparedStatment(Integer.parseInt(suppliers[0]), suppliers[1], suppliers[2], suppliers[3],
						suppliers[4], suppliers[5]);
			}
			sc.close();
			System.out.println("Added data in Supplier table");
		} catch (FileNotFoundException e) {
			System.err.println("File " + "suppliers.txt" + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data into International supplier table.
	 */
	public void fillIntSupplierTable() {
		try {
			Scanner sc = new Scanner(new FileReader("International_suppliers.txt"));
			while (sc.hasNext()) {
				String intSuppliers[] = sc.nextLine().split(";");
				insertIntSupplierPrepStatment(Integer.parseInt(intSuppliers[0]), Double.parseDouble(intSuppliers[1]));
			}
			sc.close();
			System.out.println("Added data in International_suppliers table");
		} catch (FileNotFoundException e) {
			System.err.println("File " + "International_suppliers.txt" + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data into Electrical_item table.
	 */
	public void fillElecItemTable() {
		try {
			Scanner sc = new Scanner(new FileReader("electrical_items.txt"));
			while (sc.hasNext()) {
				String elecItems[] = sc.nextLine().split(";");
				insertElecItemPrepStatment(Integer.parseInt(elecItems[0]), elecItems[1]);
			}
			sc.close();
			System.out.println("Added data in Electrical_items table");
		} catch (FileNotFoundException e) {
			System.err.println("File " + "electrical_items.txt" + " Not Found!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load data into Orderline table.
	 * 
	 * @param orderId
	 * @param toolId
	 * @param supplierId
	 * @param quantity
	 * @return
	 */
	public int insertOrderLinePrepStatment(int orderId, int toolId, int supplierId, int quantity) {

		int rowCount = 0;

		try {
			String query = "INSERT INTO orderline ( order_id,  item_id,  supplier_id,  amount_ordered) values (?,?,?,?)";

			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, orderId);
			pStat.setInt(2, toolId);
			pStat.setInt(3, supplierId);
			pStat.setInt(4, quantity);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
			System.out.println("Added data in orderline table");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

	/**
	 * Load data into Orders table.
	 * 
	 * @param orderId
	 * @param date
	 * @return
	 */
	public int insertOrderPrepStatment(int orderId, String date) {

		int rowCount = 0;

		try {
			String query = "INSERT INTO orders ( order_id,  order_date) values (?,?)";

			PreparedStatement pStat = myDriver.getMyConn().prepareStatement(query);
			pStat.setInt(1, orderId);
			pStat.setString(2, date);

			rowCount = pStat.executeUpdate();
//			System.out.println("row Count = " + rowCount);
			System.out.println("Added data in orders table");
			pStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;

	}

}