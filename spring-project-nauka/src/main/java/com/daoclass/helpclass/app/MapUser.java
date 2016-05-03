package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.domain.app.Uzytkownik;

public class MapUser implements RowMapper<Uzytkownik> {

	@Override
	public Uzytkownik mapRow(ResultSet result, int arg1) throws SQLException {

		Uzytkownik user = new Uzytkownik();
		user.setUser(result.getString("user"));
		user.setId(result.getInt("id"));
		user.setRole(result.getString("role"));
		return user;
	}

}
