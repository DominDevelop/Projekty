package com.daoclass.app;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.daoclass.helpclass.app.MapLista;
import com.domain.app.Lista;
import com.domain.app.Slownik;
import com.domain.app.ZestawSlow;

public class SerwisLista extends MapLista{

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
	
	public List<Lista> getLista(int idUzytkownik){
		
		String SQL = "SELECT * FROM lista WHERE id_uzytkownik='" + idUzytkownik + "';";
		
		List<Lista> list = jdbcTemplate.query(SQL, new MapLista());
		return list;
	}
	
	public void organizujPlan(List<Slownik> list,int idUzytkownik){
		
		boolean condition = true;
		String SQL = "INSERT INTO lista (id_uzytkownik,id_slowa,liczba_poprawnych,nauczone,data) VALUES";
		if(list.size()>0){
			for(Slownik s : list){
				if(condition){
					SQL +="('" + idUzytkownik + "','" + s.getId() + "','0','0','')";
					condition = false;
				}else{
					SQL +=",('" + idUzytkownik + "','" + s.getId() + "','0','0','')";
				}
			}
			SQL += ";";
			System.out.println("Powsta³o zapytanie:");
			System.out.println(SQL);
			jdbcTemplate.update(SQL);
		}
	}

	public void updateSlowo(int idSlowa, int idUser,int num) {
		
		System.out.println("aktualizuje");
		String SQL = "UPDATE lista SET liczba_poprawnych='" + num 
				+ "' WHERE id_slowa='" + idSlowa 
				+ "' AND id_uzytkownik='" + idUser + "';";
		if(num > 4){
			SQL = "UPDATE lista SET liczba_poprawnych='" + num
					+ "', nauczone='1' WHERE id_slowa='" + idSlowa 
					+ "' AND id_uzytkownik='" + idUser + "';";
		}
		System.out.println(SQL);
		jdbcTemplate.update(SQL);
	}
	
	
}
