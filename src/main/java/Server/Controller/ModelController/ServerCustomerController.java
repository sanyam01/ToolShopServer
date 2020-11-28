package Server.Controller.ModelController;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Server.Controller.DatabaseController.DBController;
import Server.Model.Customer;
import Server.Model.CustomerList;

/**
 * Class controls the communication for customer management GUI and contains
 * methods to be called based on request from client end. Class return the
 * response to client in form of JSON strings.
 * 
 * @author Neha Singh
 *
 */
public class ServerCustomerController {

	DBController dbController;
	private Customer customer = null;
	private CustomerList customerList;

	ObjectMapper objectMapper;
	private String message;

	private ArrayList<Customer> cust;

	/**
	 * Constructs the dbcontroller and object mapper
	 * 
	 * @param dbC
	 * @param objectMapper
	 * @throws IOException
	 */
	public ServerCustomerController(DBController dbC, ObjectMapper objectMapper) throws IOException {

		this.message = null;
		this.dbController = dbC;
		this.objectMapper = objectMapper;

	}

	/**
	 * Method reads the client request, splits it to get task id and calls the
	 * particular task id in switchcase method, Returns the response message to
	 * client in form of JSON string
	 * 
	 * @param clientRequest
	 * @return
	 */
	public String readClientMessage(String clientRequest) {
		String[] responseArr = null;
		String switchBoardResponse = null;
		message = clientRequest;

		System.out.println("Request from client: " + message);

		if (message != null) {
			responseArr = message.split(" ", 2);
			switchBoardResponse = switchBoard(responseArr);

		}

		return switchBoardResponse;

	}

	/**
	 * Method checks if the customer exists in the database.
	 * 
	 * @return
	 */
	public boolean checkCustomerExists() {

		cust = dbController.getCustomerbyId(customer.getCustomerID());
		customerList = new CustomerList(cust);
		if (!cust.isEmpty()) {
			System.out.println("customer is present, updating");
			return true;
		}
		return false;
	}

	/**
	 * Method calls the update of customer details if customer exists
	 * 
	 * @return
	 */
	public boolean callUpdatecustomer() {

		// update
		boolean flag = dbController.updateCustomer(customer.getFirstName(), customer.getLastName(),
				customer.getAddress(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getCustomerType(),
				customer.getCustomerID());

		return flag;
	}

	/**
	 * Method calls insert method for customer if customer doesn't exist in the
	 * database
	 * 
	 * @return
	 */
	public boolean callInsertcustomer() {
		boolean flag = dbController.insertCustomer(customer.getFirstName(), customer.getLastName(),
				customer.getAddress(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getCustomerType(),
				customer.getCustomerID());

		return flag;
	}

	/**
	 * Method calls delete customer operation for delete button from GUI.
	 * 
	 * @return
	 */
	public String callDeletecustomer() {
		String temp = "";
		// delete
		boolean flag = dbController.deleteCustomer(customer.getCustomerID());
		if (flag) {

			temp = "Customer deleted successfully";
			System.out.println("Customer deleted successfully");

		} else {
			temp = "ERROR !! Customer delete failed";
			System.out.println(temp);
		}
		return temp;
	}

	/**
	 * Method contains tasks defined under switch cases for different requests from
	 * front end and calls specific operations and returns JSON string response.
	 * 
	 * @param responseArr
	 * @return
	 */
	public String switchBoard(String[] responseArr) {

		// 1 search based on client-id
		// 2 search based on lastname
		// 3 search based on client type
		// 4 save
		// 5 delete

		int choice = Integer.parseInt(responseArr[0]);
		System.out.println("choice is: " + choice);
		String jsonCustomerList = null;

		boolean flag;

		switch (choice) {

		case 1:

			System.out.println("Operation: Get customer by id");
			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonCustomerList = "ERROR!! Invalid input!!";

			} else {

				try {
					System.out.println(Integer.parseInt(responseArr[1].trim()));
				} catch (NumberFormatException e) {
					jsonCustomerList = "ERROR!! Invalid input, enter integer value";
					break;
				}

				cust = dbController.getCustomerbyId(Integer.parseInt(responseArr[1].trim()));
				if (!cust.isEmpty()) {
					customerList = new CustomerList(cust);

					try {
						jsonCustomerList = objectMapper.writeValueAsString(customerList);
						System.out.println("Sending to client: " + jsonCustomerList);

					} catch (JsonProcessingException e) {

						e.printStackTrace();
					}
				} else {
					jsonCustomerList = "ERROR !! Customer not found!!";

				}
			}

			break;

		case 2:
			// get customer based on lastname

			System.out.println("Operation: Get customer by lastname");
			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonCustomerList = "ERROR!! Invalid input!!";

			} else {
				cust = dbController.getCustomerbyLname(responseArr[1].trim());

				if (!cust.isEmpty()) {
					customerList = new CustomerList(cust);

					try {
						jsonCustomerList = objectMapper.writeValueAsString(customerList);
						System.out.println("Sending to client: " + jsonCustomerList);

					} catch (JsonProcessingException e) {

						e.printStackTrace();
					}

				} else {
					jsonCustomerList = "ERROR !! Customer not found!!";

				}
			}

			break;

		case 3:
			// get customer based on type

			System.out.println("Operation: Get customer by type");
			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonCustomerList = "ERROR!! Invalid input!!";

			} else {
				cust = dbController.getCustomerbyType(responseArr[1].trim());
				if (!cust.isEmpty()) {
					customerList = new CustomerList(cust);

					try {
						jsonCustomerList = objectMapper.writeValueAsString(customerList);
						System.out.println("Sending to client: " + jsonCustomerList);

					} catch (JsonProcessingException e) {

						e.printStackTrace();
					}

				} else {
					jsonCustomerList = "ERROR !! Customer not found!!";

				}
			}

			break;

		case 4:
			// save customer

			System.out.println("Operation: Save");

//			System.out.println("printing responseArr[1]");
			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonCustomerList = "ERROR!! Invalid input!!";

			} else {
				try {
					this.customer = objectMapper.readValue(responseArr[1], Customer.class);
				} catch (JsonParseException e) {

					e.printStackTrace();
				} catch (JsonMappingException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}

				// check if customer is new or already present

				flag = checkCustomerExists();
				if (flag) {
					// customer is present
					// update customer
					if (callUpdatecustomer()) {
						jsonCustomerList = "Customer updated successfully";

					} else {
						jsonCustomerList = "ERROR !! Customer update failed";
					}

				}

				else {
					// customer is not present
					System.out.println("customer not present, inserting");

					// insert customer
					if (callInsertcustomer()) {
						jsonCustomerList = "Customer inserted successfully";

					} else {
						jsonCustomerList = "ERROR !! Customer insert failed";
					}

				}
			}

			break;

		case 5:
			// delete customer

			System.out.println("Operation: Delete customer");

			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonCustomerList = "ERROR!! Invalid input!!";

			} else {
				try {
					this.customer = objectMapper.readValue(responseArr[1], Customer.class);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// check if customer is present
				flag = checkCustomerExists();
				if (flag) {
					// customer is present
					// delete customer
					jsonCustomerList = callDeletecustomer();

				} else {
					// customer is not present
					System.out.println("ERROR !! customer is not present to delete");
					// customer does not exist
					jsonCustomerList = "ERROR !! Delete failed, Customer does not exist.";

				}
			}

			break;

		default:
			System.out.println("Invalid choice!");

		}

		return jsonCustomerList;

	}

}
