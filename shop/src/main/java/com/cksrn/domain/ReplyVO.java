package com.cksrn.domain;

import java.util.Date;

/**
 * create table tbl_comment( gdsNum number not null, userId varchar2(50) not
 * null, comNum number not null, comCon varchar2(2000) not null, comDate date
 * default sysdate, primary key(gdsNum, comNum) );
 * 
 */
public class ReplyVO {

	private int gdsNum, comNum;
	private String userId, comCon;
	private Date comDate;
	
	public int getGdsNum() {
		return gdsNum;
	}
	public void setGdsNum(int gdsNum) {
		this.gdsNum = gdsNum;
	}
	public int getComNum() {
		return comNum;
	}
	public void setComNum(int comNum) {
		this.comNum = comNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComCon() {
		return comCon;
	}
	public void setComCon(String comCon) {
		this.comCon = comCon;
	}
	public Date getComDate() {
		return comDate;
	}
	public void setComDate(Date comDate) {
		this.comDate = comDate;
	}
	
}
