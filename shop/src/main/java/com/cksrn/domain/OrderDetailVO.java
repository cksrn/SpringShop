package com.cksrn.domain;
/**
 * 
 * create table tbl_order_details(
orderDetailsNum number not null,
orderId varchar2(50) not null,
gdsNum number not null,
cartStock number not null,
primary key(orderDetailsNum)
);
 *
 */
public class OrderDetailVO {

	private int orderDetailsNum, gdsNum, cartStock;
	private String orderId;
	public int getOrderDetailsNum() {
		return orderDetailsNum;
	}
	public void setOrderDetailsNum(int orderDetailsNum) {
		this.orderDetailsNum = orderDetailsNum;
	}
	public int getGdsNum() {
		return gdsNum;
	}
	public void setGdsNum(int gdsNum) {
		this.gdsNum = gdsNum;
	}
	public int getCartStock() {
		return cartStock;
	}
	public void setCartStock(int cartStock) {
		this.cartStock = cartStock;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
