package Server.Controller.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Server.Controller.DatabaseController.DBController;
import Server.Controller.ModelController.ServerModelController;

/**
 * Class is the main class that implements server functionalities. Class
 * contains the socket connections to be made to communicate with client. Also
 * implements pool of threads to enable multiple client connections.
 * 
 * @author Neha Singh
 *
 */
public class Server {

	private Socket socket;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public Server() {

		try {
			// Server socket accepts the port as a parameter
			serverSocket = new ServerSocket(9090);

			pool = Executors.newFixedThreadPool(20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method instantiates the database and accepts the connections from client
	 */
	public void runServer() {
		System.out.println("Server is running...");

		DBController dbController = new DBController();
		System.out.println("DBController instantiated");

		while (true) {

			try {
				socket = serverSocket.accept(); // for multiple clients
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			ServerModelController serverModelController = new ServerModelController(socket, dbController);

			pool.execute(serverModelController);

		}

	}

	public static void main(String[] args) {

		Server myServer = new Server();
		myServer.runServer();

	}

}
