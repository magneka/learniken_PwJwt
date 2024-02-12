package uc.no.identity.controllers;

public class LoginModel {
    
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String userName) {
        email = userName;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }
}