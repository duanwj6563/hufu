package com.sunlands.feo.domain.model.user;

/**
 * Created by yangchao on 17/11/6.
 */
public class UserSimpleView {

    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public UserSimpleView setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserSimpleView setEmail(String email) {
        this.email = email;
        return this;
    }
}
