package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.domain.app.Slownik;

public class MapSlowo implements RowMapper<Slownik>{

	@Override
	public Slownik mapRow(ResultSet result, int arg1) throws SQLException {
		
		Slownik slowo = new Slownik();
		slowo.setId(result.getInt("id"));
		slowo.setIdZestaw(result.getInt("id_zestaw"));
		slowo.setPl(result.getString("pl_slowo"));
		slowo.setEn(result.getString("en_slowo"));
		slowo.setPlSentence(result.getString("pl_zdanie"));
		slowo.setEnSentence(result.getString("en_zdanie"));
		return slowo;
	}

}
