package com.jaws.app.model;

import lombok.Data;

@Data
public class EventModel {
    private int eventId;
    private String eventName;
    private String eventDate;
    private int eventYear;
    private String description;
}
