package com.jaws.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jaws.app.model.JawsModel;

@Component
public class JawsModelRowMapper implements RowMapper<JawsModel> {

    @Override
    public JawsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        JawsModel model = new JawsModel();
        model.setUniqueId(rs.getInt("uniqueId"));
        model.setMembershipNo(rs.getString("membershipNo"));
        model.setFullName(rs.getString("name"));
        model.setMobileNo(rs.getString("mobileNo"));
        model.setDoj(rs.getDate("doj"));
        model.setPhoto(rs.getBytes("photo"));
        return model;
    }
}