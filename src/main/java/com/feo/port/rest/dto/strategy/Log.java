package com.feo.port.rest.dto.strategy;

import java.util.Date;

public class Log {
    private Long id;
//    姓名
    private String username;
    private String description;
    private Date dateTime;

    public Log(Long id, String username, String description, Date dateTime) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.dateTime = dateTime;
    }

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
