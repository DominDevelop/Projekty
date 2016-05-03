package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.domain.app.ZestawSlow;

public class MapZestawSlow implements RowMapper<ZestawSlow> {

	@Override
	public ZestawSlow mapRow(ResultSet r, int arg1) throws SQLException {
		
		ZestawSlow zestaw = new ZestawSlow();
		zestaw.setId(r.getInt("id"));
		zestaw.setEn(r.getString("en_slowo"));
		zestaw.setPl(r.getString("pl_slowo"));
		zestaw.setEnSentence(r.getString("en_zdanie"));
		zestaw.setPlSentence(r.getString("pl_zdanie"));
		zestaw.setLiczbaPoprawnych(r.getInt("liczba_poprawnych"));
		zestaw.setNauczone(r.getBoolean("nauczone"));
		zestaw.setIdZestaw(r.getInt("id_zestaw"));
		zestaw.setIdUzytkownik(r.getInt("id_uzytkownik")); 
		zestaw.setData(r.getString("data"));
		zestaw.setIdSlowa(r.getInt("id_slowa"));
		return zestaw;
	}
}
