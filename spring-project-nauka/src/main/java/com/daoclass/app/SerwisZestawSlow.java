package com.daoclass.app;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapZestawSlow;
import com.domain.app.ZestawSlow;

public class SerwisZestawSlow extends MapZestawSlow {
	
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
	
	public List<ZestawSlow> getUserSlownik(int idUzytkownik){
		
		String SQL = "SELECT * FROM slownik LEFT JOIN lista ON lista.id_uzytkownik='" + idUzytkownik + "' WHERE slownik.id=lista.id_slowa";
		System.out.println(SQL);
		
		List<ZestawSlow> list = jdbcTemplate.query(SQL, new MapZestawSlow());
		
		/*
		for(ZestawSlow z : list){
			System.out.println("-------------------------------------------- ");
			System.out.println("pl: " + z.getPl());
			System.out.println("en: " + z.getEn());
			System.out.println("pl: " + z.getPlSentence());
			System.out.println("en: " + z.getEnSentence());
		}*/
		
		return list;
	}
}
