package com.feo.domain.event;

//import org.apache.commons.lang.builder.EqualsBuilder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 值对象
 * Created by yangchao on 17/11/16.
 */
public class ValueObject {
    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this,obj);
    }
}
