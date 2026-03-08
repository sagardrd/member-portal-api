package com.jaws.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jaws.app.model.EventModel;

public class EventModelRowMapper implements RowMapper<EventModel> {

    @Override
    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        EventModel event = new EventModel();
        event.setEventId(rs.getInt("eventId"));
        event.setEventName(rs.getString("eventName"));
        event.setEventYear(rs.getInt("eventYear"));
        event.setEventDate(rs.getDate("eventDate").toString());
        return event;
    }


}
