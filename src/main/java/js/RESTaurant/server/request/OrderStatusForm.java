package js.RESTaurant.server.request;

import js.RESTaurant.server.enums.OrderStatus;

public class OrderStatusForm {
	private Long orderID;
	private OrderStatus orderStatus;
	
	
	public OrderStatusForm() {
	}
	
	public OrderStatusForm(Long orderID, OrderStatus orderStatus) {
		this.orderID = orderID;
		this.orderStatus = orderStatus;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

}
