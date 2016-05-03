package com.daoclass.app;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapZestaw;
import com.domain.app.Zestaw;

public class SerwisZestaw extends MapZestaw {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setDataSource(DataSource dataSource) {
		
		System.out.println("Wstrzykniêto DAO");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Zestaw>getZestawlist(){
		
		String SQL = "SELECT * FROM `zestaw`";
		List<Zestaw> list = jdbcTemplate.query(SQL,new MapZestaw());
		return list;
	}
}
