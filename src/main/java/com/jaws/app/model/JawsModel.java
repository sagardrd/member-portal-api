package com.jaws.app.model;

import java.util.Date;

import lombok.Data;

@Data
public class JawsModel {
    private int uniqueId;
    private String membershipNo;
    private String mobileNo;
    private String fullName;
    private Date doj;
    private byte[] photo;

}
