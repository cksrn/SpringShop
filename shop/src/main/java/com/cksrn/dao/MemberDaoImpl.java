package com.cksrn.dao;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cksrn.domain.MemberVO;
@Repository
public class MemberDaoImpl implements MemberDAO{
	@Inject
	SqlSessionTemplate sql;

	@Override
	public void signup(MemberVO vo) throws Exception {
//		System.out.println("signupDAO()");
		sql.insert("sign.signup", vo);		
	}

	@Override
	public MemberVO signin(MemberVO vo) throws Exception {
//		System.out.println("signinDAO()");
		return sql.selectOne("sign.signin", vo);
	}
	
}
