package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.domain.app.Zestaw;

public class MapZestaw implements RowMapper<Zestaw> {

	@Override
	public Zestaw mapRow(ResultSet rs, int arg1) throws SQLException {
		Zestaw zestaw = new Zestaw();
		zestaw.setId(rs.getInt("id"));
		zestaw.setNazwa(rs.getString("nazwa"));
		return zestaw;
	}
}