package com.cksrn.domain;

/**
create table tbl_cart(
cartNum number not null,
userId varchar2(50) not null,
gdsNum number not null,
cartStock number not null,
addDate date default sysdate,
primary key(cartNum, userId)
);
 */

import java.util.Date;

public class CartVO {

	private int cartNum, gdsNum, cartStock;
	private String userId;
	private Date addDate;
	
	public int getCartNum() {
		return cartNum;
	}
	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
}
