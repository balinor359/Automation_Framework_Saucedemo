package com.saucedemo.objects;

public class TestUser {
    private String username;
    private String password;

    public TestUser() {

    }

    public TestUser(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return '\'' + username + '\'' +
                ", '" + password + '\'';
    }
}
