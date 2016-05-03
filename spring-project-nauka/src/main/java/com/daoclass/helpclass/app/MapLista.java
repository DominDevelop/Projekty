package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.domain.app.Lista;

public class MapLista implements RowMapper<Lista> {

	@Override
	public Lista mapRow(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
