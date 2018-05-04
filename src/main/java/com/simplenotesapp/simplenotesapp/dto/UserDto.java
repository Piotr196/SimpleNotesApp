package com.simplenotesapp.simplenotesapp.dto;

public class UserDto {

    private long id;
    private String login;
    private String name;
    private String surname;
    private String password;

    public UserDto(final long id, final String login, final String name, final String surname, final String password) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public UserDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
