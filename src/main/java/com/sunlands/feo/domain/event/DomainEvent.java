package com.sunlands.feo.domain.event;

import java.util.Date;
import java.util.UUID;

/**
 * 领域事件
 * Created by yangchao on 17/11/16.
 */
public class DomainEvent {
    public Date eventDate;
    public String eventId;

    public Date getEventDate() {
        eventDate = new Date();
        return eventDate;
    }

    public String getEventId() {
        eventId = UUID.randomUUID().toString().trim();
        return eventId;
    }
}
