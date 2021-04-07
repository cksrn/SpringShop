package com.cksrn.shop.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cksrn.dao.AdminDAO;
import com.cksrn.domain.GoodsVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AdminMapperTests {

	private static final Logger logger = LoggerFactory.getLogger(AdminMapperTests.class);
	
	@Autowired
	private AdminDAO dao;
	
	@Test
	public void testInsert() throws Exception {
		GoodsVO goods = new GoodsVO();
		goods.setCateCode("100");
		goods.setGdsName("바지");
		goods.setGdsPrice(1000);
		goods.setGdsStock(1);
		goods.setGdsDes("바지");
		goods.setGdsNum(1);
		goods.setGdsDate(null);
		goods.setGdsImg(null);
		dao.insertGoods(goods);
		
	}

}
