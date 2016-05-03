package com.daoclass.app;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapPlan;
import com.daoclass.helpclass.app.MapSlowo;
import com.domain.app.Plan;
import com.domain.app.Slownik;
import com.domain.app.Zestaw;

public class SerwisPlan extends MapPlan{

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
	
	public List getZestawy(int id){
		
		String SQL = "SELECT * FROM `plan` WHERE id_uzytkownik=" + id;
		List<Plan> list = jdbcTemplate.query(SQL,new MapPlan());
		return list;
	}
	
	public boolean addZestaw(int idUzytkownik,int idZestawu){
		
		String SQL = "SELECT * FROM plan WHERE id_uzytkownik=" + idUzytkownik;
		List<Plan> list = jdbcTemplate.query(SQL,new MapPlan());
		
		for(Plan p : list){
			if( p.getIdZestaw() == idZestawu){
				return false;
			}
		}
		
		SQL = "INSERT INTO plan (id_zestaw, id_uzytkownik) values ( '" + idZestawu + "', '" + idUzytkownik + "')";
		jdbcTemplate.update(SQL);
		return true;
	}

	public void delete(int idUser,int idZestaw) {
		
		String SQL = "DELETE FROM plan WHERE id_uzytkownik='" + idUser + "' AND id_zestaw='" + idZestaw + "';";
		jdbcTemplate.update(SQL);
		
		SQL = "SELECT * FROM slownik WHERE id_zestaw='" + idZestaw + "';";
		List<Slownik> l = jdbcTemplate.query(SQL, new MapSlowo());
		
		for(Slownik s : l){
			
			SQL = "DELETE FROM lista WHERE id_uzytkownik='" + idUser + "' AND id_slowa='" + s.getId() + "';";
			jdbcTemplate.update(SQL);
		}
		
	}
}
