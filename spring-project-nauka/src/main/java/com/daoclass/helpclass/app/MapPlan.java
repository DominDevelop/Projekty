package com.daoclass.helpclass.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.domain.app.Plan;

public class MapPlan implements RowMapper<Plan> {

	@Override
	public Plan mapRow(ResultSet result, int arg1) throws SQLException {
		
		// TODO Auto-generated method stub
		Plan plan = new Plan();
		plan.setId(result.getInt("id"));
		plan.setIdUzytkownik(result.getInt("id_uzytkownik"));
		plan.setIdZestaw(result.getInt("id_zestaw"));
		return plan;
	}

}
