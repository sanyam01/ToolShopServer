package Server.Controller.ModelController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Server.Controller.DatabaseController.DBController;
import Server.Model.Items;
import Server.Model.ItemsList;
import Server.Model.OrderLines;
import Server.Model.Inventory;

/**
 * Class controls the communication for inventory management GUI and contains
 * methods to be called based on request from client end. Class return the
 * response to client in form of JSON strings.
 * 
 * @author Neha Singh
 *
 */
public class ServerInventoryController {

	DBController dbController;
	private Inventory inventory;
	ObjectMapper objectMapper;
	private String message;

	public ServerInventoryController(DBController dbC, ObjectMapper objectMapper) throws IOException {

		this.message = null;
		this.dbController = dbC;
		this.objectMapper = objectMapper;
		this.inventory = new Inventory(dbController.getItemList());

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
	 * Method calls update item quantity operation for given item id
	 * 
	 * @param itemId
	 * @return
	 */
	public boolean callUpdateItemQuantity(int itemId) {
		boolean flag = false;
		for (Items i : inventory.getListItems()) {

			if (i.getItemID() == itemId) {

				System.out.println("updating item quantity for item: " + itemId + " " + i.getItemQuantity());

				flag = dbController.updateItemQty(i.getItemQuantity(), itemId);
				if (!flag) {
					flag = false;
					System.out.println("Item update failed");

				}

			}

		}
		return flag;

	}

	/**
	 * Method checks if orderline is already generated for given item id
	 * 
	 * @param itemId
	 * @return
	 */
	public boolean checkOrderLineGeneration(int itemId) {
		boolean flag = false;

		int orderLineSizeOld = inventory.getTheOrder().getOrderLines().size();
		inventory.decreaseQuantity(itemId);

		int orderLineSizeNew = inventory.getTheOrder().getOrderLines().size();

		if (orderLineSizeNew > orderLineSizeOld) {

			flag = true;

		}
		return flag;
	}

	/**
	 * Method checks if order is already placed for current date
	 * 
	 * @return
	 */
	public boolean checkOrder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd ");
		String stringOrderDate = inventory.getTheOrder().getDate().trim();
		Date todayDate = new Date();
		String stringTodayDate = sdf.format(todayDate).trim();

		System.out.println("\nstringTodayDate: " + stringTodayDate);
		System.out.println("\nstringOrderDate: " + stringOrderDate);
		int orderCount = dbController.getOrderCount();
		System.out.println("orderCount: " + orderCount);

		if (stringTodayDate.equals(stringOrderDate)) {

			if (orderCount == 0) {
				this.dbController.addOrder(inventory.getTheOrder().getOrderId(), inventory.getTheOrder().getDate());
			}

			System.out.println("in check order, both dates are same\n");

			return true;

		} else {
			System.out.println("in check order, both dates are different\n");
		}
		return false;

	}

	/**
	 * Method generates order if not already placed
	 * 
	 * @return
	 */
	public boolean generateOrder() {

		boolean flag = false;
		// load data in order table
		System.out.println("loading data in order table for values: " + inventory.getTheOrder().getOrderId() + " "
				+ inventory.getTheOrder().getDate() + "\n");

		if (!checkOrder()) {

			System.out.println("in generateOrder");
			flag = this.dbController.addOrder(inventory.getTheOrder().getOrderId(), inventory.getTheOrder().getDate());

		} else {
			System.out.println("Order is already generated for today!!: " + inventory.getTheOrder().getOrderId());
			flag = true;
		}

		return flag;

	}

	/**
	 * Method generated orderline and adds data to the database
	 * 
	 * @param orderLine
	 * @return
	 */
	public int generateOrderLine(OrderLines orderLine) {

		System.out.println("new order line generated for: ");

		System.out.println(inventory.getTheOrder().getOrderId() + " " + orderLine.getItem().getItemID() + " "
				+ orderLine.getItem().getSupplierID() + " " + orderLine.getItem().getItemQuantity());

		// orderline table insert
		dbController.addOrderLine(inventory.getTheOrder().getOrderId(), orderLine.getItem().getItemID(),
				orderLine.getItem().getSupplierID(), orderLine.getAmount());

		System.out.println("new qty after generating orderline: " + orderLine.getItem().getItemQuantity());
		System.out.println("amount generated in orldeline: " + orderLine.getAmount());

		return orderLine.getItem().getItemQuantity();
	}

	/**
	 * Method contains tasks defined under switch cases for different requests from
	 * front end and calls specific operations and returns JSON string response.
	 * 
	 * @param responseArr
	 * @return
	 */
	private String switchBoard(String[] responseArr) {

		int choice = Integer.parseInt(responseArr[0]);
		System.out.println("choice is: " + choice);
		String jsonItemList = null;
		ArrayList<Items> items;


		switch (choice) {

		case 6:
			// list all tools
			System.out.println("Operation: list all tools");
			System.out.println(responseArr[1]);

			items = dbController.getItemList();
			if (!items.isEmpty()) {

				ItemsList serializedFleet = new ItemsList();
				serializedFleet.setItemsList(items);

				try {
					jsonItemList = objectMapper.writeValueAsString(serializedFleet);
				} catch (JsonProcessingException e1) {

					e1.printStackTrace();
				}

			} else {
				jsonItemList = "ERROR!! Items list is empty!!";
			}

			break;

		case 7:
			// Search for tool by toolId
			System.out.println("Operation: Search for tool by toolId");
			System.out.println(responseArr[1]);

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonItemList = "ERROR!! Invalid input!!";

			} else {

				try {
					System.out.println(Integer.parseInt(responseArr[1].trim()));
				} catch (NumberFormatException e) {
					jsonItemList = "ERROR!! Invalid input, enter integer value";
					break;
				}

				items = dbController.getItemById(Integer.parseInt(responseArr[1].trim()));

				if (!items.isEmpty()) {

					ItemsList serializedFleet = new ItemsList();
					serializedFleet.setItemsList(items);

					try {
						jsonItemList = objectMapper.writeValueAsString(serializedFleet);
					} catch (JsonProcessingException e1) {

						e1.printStackTrace();
					}

				} else {
					jsonItemList = "ERROR!! Items Id not found!!";
				}

			}

			break;

		case 8:
			// Search for tool by toolName
			System.out.println("Operation: Search for tool by toolName");

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonItemList = "ERROR!! Invalid input!!";

			} else {

				items = dbController.getItemByName(responseArr[1].trim());

				if (!items.isEmpty()) {

					ItemsList serializedFleet = new ItemsList();
					serializedFleet.setItemsList(items);

					try {
						jsonItemList = objectMapper.writeValueAsString(serializedFleet);
					} catch (JsonProcessingException e1) {

						e1.printStackTrace();
					}

				} else {
					jsonItemList = "ERROR!! Item Name not found!!";
				}

			}

			break;

		case 9:

			System.out.println("Operation: Decrease item quantity");
			System.out.println(responseArr[1]); // will get id here

			if (responseArr[1].isEmpty() || responseArr[1].isBlank()) {
				System.out.println("\nInvalid input\n");
				jsonItemList = "ERROR!! Invalid input!!";

			} else {

				int new_quantity = 0;

				if (!responseArr[1].isEmpty()) {

					if (!generateOrder()) {
						jsonItemList = "ERROR !! Order generation failed";
						break;
					}

					if (checkOrderLineGeneration(Integer.parseInt(responseArr[1].trim()))) {
						System.out.println("orderline is generated");

						OrderLines orderLine = inventory.getTheOrder().getOrderLines()
								.get(inventory.getTheOrder().getOrderLines().size() - 1);

						new_quantity = generateOrderLine(orderLine);
						if (new_quantity == 0) {
							jsonItemList = "ERROR !! Orderline generation failed";
							break;
						}
					}

					if (callUpdateItemQuantity(Integer.parseInt(responseArr[1]))) {
						jsonItemList = Integer.toString(new_quantity);
					} else {
						jsonItemList = "ERROR !! Update tool quantity failed";
					}

				}

				else {
					jsonItemList = "ERROR !! Request to decrease item failed";
				}

			}

			break;

		case 10:

			System.out.println("Operation: Print order");
			try {
				jsonItemList = objectMapper.writeValueAsString(inventory.getTheOrder());
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			break;

		default:
			System.out.println("Invalid choice!");
		}

		return jsonItemList;
	}

}
