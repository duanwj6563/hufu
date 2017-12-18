package com.feo.domain.event;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实体
 * Created by yangchao on 17/11/16.
 */
public class EntityObject {
    /**
     * 领域事件
     * @return
     */
    @DomainEvents
    Collection<Object> domainEvents() {
        System.out.println("****** Event receive ~");
        List<Object> events= new ArrayList<Object>();
        events.add(this);
        return events;
    }

    /**
     * 事件执行后回调
     */
    @AfterDomainEventPublication
    void callbackMethod() {
        System.out.println("****** Event Done !");
    }

}
