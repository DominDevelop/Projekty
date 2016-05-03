package com.daoclass.app;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapSlowo;
import com.domain.app.Slownik;

public class SerwisSlownik extends MapSlowo{

	private JdbcTemplate jdbcTemplate;
	 
	public void setDataSource(DataSource dataSource) {
		
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Slownik> getSlownik(int idZestaw){
		
		String SQL = "SELECT * FROM slownik WHERE id_zestaw='" + idZestaw + "'";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new MapSlowo());
	}
	
	/*
	public List<Slownik> getUserSlownik(int idUzytkownik){
		
		String SQL = "SELECT * FROM slownik LEFT JOIN lista ON lista.id_uzytkownik='" + idUzytkownik + "' WHERE slownik.id=lista.id_slowa";
		return jdbcTemplate.query(SQL, new MapSlowo());
	}
	*/

}
