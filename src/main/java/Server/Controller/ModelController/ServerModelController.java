package Server.Controller.ModelController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import Server.Controller.DatabaseController.DBController;

/**
 * Class implements runnable and controls the server model and based on input
 * request from socket from client, instantiates the Inventory controller and
 * customer controller. Then sets the response message to be sent to client to
 * the socket again.
 * 
 * @author Neha Singh
 *
 */
public class ServerModelController implements Runnable {

	ObjectMapper objectMapper;
	private DBController dbController;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ServerCustomerController serverCustomerController;
	private ServerInventoryController serverToolController;

	/**
	 * Constructs the server inventory and customer controllers.
	 * 
	 * @param socket
	 * @param dbController
	 */
	public ServerModelController(Socket socket, DBController dbController) {

		objectMapper = new ObjectMapper();
		objectMapper.enableDefaultTyping();

		this.dbController = dbController;
		this.socket = socket;

		try {
			this.serverCustomerController = new ServerCustomerController(dbController, objectMapper);
			System.out.println("serverCustomerController instantiated");
		} catch (IOException e) {
			System.out.println("serverCustomerController instantiation failed");
			e.printStackTrace();
		}
		try {
			this.serverToolController = new ServerInventoryController(dbController, objectMapper);
			System.out.println("ServerToolController instantiated");

		} catch (IOException e) {
			System.out.println("ServerToolController instantiation failed");
			e.printStackTrace();

		}
	}

	public void run() {

		String message = null;
		while (true) {

			try {
				message = getClientMessage();
			} catch (IOException e) {

				break;
			}
			int taskId = getTaskId(message);

			if (taskId > 0 && taskId < 6) {
				System.out.println("Calling CustomerController");

				// call runCustomerController
				runCustomerController(message);

			} else if (taskId >= 6 && taskId < 12) {

				System.out.println("Calling InventoryController");
				runInventoryController(message);

			}
		}

		closeSockets();

	}

	/**
	 * Method send request to server customer controller and gets response to be
	 * sent to client
	 * 
	 * @param message
	 */
	public void runCustomerController(String message) {

		String messageToClient = serverCustomerController.readClientMessage(message);

		// sending message to client
		setClientMessage(messageToClient);

	}

	/**
	 * Method send request to server inventory controller and gets response to be
	 * sent to client
	 * 
	 * @param message
	 */
	public void runInventoryController(String message) {
		String messageToClient = serverToolController.readClientMessage(message);

		// sending message to client
		setClientMessage(messageToClient);

	}

	/**
	 * Method gets client request from socket
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getClientMessage() throws IOException {

		System.out.println("Getting taskid");
		String request = null;

		socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketOut = new PrintWriter(socket.getOutputStream(), true);

		request = socketIn.readLine();

		System.out.println("Request from client: " + request);
		return request;

	}

	/**
	 * Method sets client response to socket.
	 * 
	 * @param message
	 */
	public void setClientMessage(String message) {

		System.out.println("Response to client: " + message);
		socketOut.println(message);
		socketOut.flush();
	}

	/**
	 * Method gets task id by splitting the message from client
	 * 
	 * @param response
	 * @return
	 */
	public int getTaskId(String response) {
		int taskId = 0;

		if (response != null) {

			String[] responseArr = response.split(" ", 2);
			taskId = Integer.parseInt(responseArr[0]);

		}

		return taskId;
	}

	/**
	 * Method closes the sockets.
	 */
	public void closeSockets() {
		try {
			// Close all sockets and streams
			socketIn.close();
			socketOut.close();
			socket.close();

		} catch (IOException e) {
			System.out.println("Error closing sockets!");
		}
	}

}
