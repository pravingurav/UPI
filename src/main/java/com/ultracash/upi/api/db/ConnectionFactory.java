package com.ultracash.upi.api.db;

import java.io.FileReader;
import java.io.Reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory {

	private static SqlSessionFactory sqlMap;

	static {
		try {
			Reader reader = new FileReader(
					"/usr/share/tomcat8/upi/src/main/java/config/SqlMapConfig.xml");
			sqlMap = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlMap;
	}
}
