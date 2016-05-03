package com.daoclass.app;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapUser;
import com.domain.app.Uzytkownik;

public class SerwisUzytkownik extends MapUser {

	private JdbcTemplate jdbcTemplate;
	 
	public void setDataSource(DataSource dataSource) {
		
		System.out.println("Wstrzykniêto DAO");
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public boolean dodajUzytkownika(Uzytkownik user){
		
		if(jdbcTemplate != null){ 
			System.out.println("mam dao");
			}
		else{
			System.out.println("nie ma dao");
		}
		
		String SQL = "INSERT INTO uzytkownik (user, password, login, role) values ( '" + user.getUser() + "', '" + user.getPassword() + "', '1','ROLE_USER')";
		jdbcTemplate.update(SQL);
		
		return true;
	}
	
	public void setPowtorki(int pow,int idUzytkownik){
		
		String SQL = "UPDATE uzytkownik SET powtorki='" + pow + "' WHERE id=" + "'" + idUzytkownik + "'";
		jdbcTemplate.update(SQL);
	}
	
	public Uzytkownik getUser(String name){
		
		String SQL = "SELECT * FROM `uzytkownik` WHERE user='" + name + "';";
		List<Uzytkownik> list = jdbcTemplate.query(SQL, new MapUser());
		
		if(list.size() > 0){
			
			Uzytkownik user = new Uzytkownik();
			user.setId(list.get(0).getId());
			user.setLogin(true);
			user.setUser(list.get(0).getUser());
			return user;
		}
		
		return null;
	}
}
