package com.cksrn.shop.dao;


import org.springframework.beans.factory.annotation.Autowired;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cksrn.domain.GoodsVO;
import com.cksrn.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AdminServiceTests {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminServiceTests.class);
	
	@Autowired
	private AdminService service;

	@Test
	public void test() throws Exception {
		GoodsVO goods = new GoodsVO();
		goods.setCateCode("100");
		goods.setGdsName("바지");
		goods.setGdsPrice(1000);
		goods.setGdsStock(1);
		goods.setGdsDes("바지");
		goods.setGdsNum(1);
		goods.setGdsDate(null);
		goods.setGdsImg(null);
		service.insertGoods(goods);
		
		
		
	}

}
