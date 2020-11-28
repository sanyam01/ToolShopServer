package Server.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Class for the orders generated for the items, contains list of orderlines
 * generated for items for a particular day.
 * 
 * @author Neha Singh and Sanyam
 *
 */
public class Order {

	/**
	 * orderID represents a unique 5 digit order id
	 */
	private int orderId;

	/**
	 * date represents the order for particular date
	 */
	private String date;

	/**
	 * orderLines represents all the orders generated for items on that day.
	 * 
	 * Each orderline corresponds to the order for a particular item
	 */
	private ArrayList<OrderLines> orderLines;

	/**
	 * Constructs an object of class Order
	 * 
	 * It generates a five digit random number and assigns the date to order
	 */
	public Order() {

		orderId = 10000 + new Random().nextInt(90000);

		// setting today's date
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		this.date = dateFormat.format(date);

		// add here orderLine, will be generated in item
		this.orderLines = new ArrayList<OrderLines>();
	}

	public int getOrderId() {
		return orderId;
	}

	public void addOrderLine(OrderLines line) {
		orderLines.add(line);
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<OrderLines> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(ArrayList<OrderLines> orderLines) {
		this.orderLines = orderLines;
	}

}