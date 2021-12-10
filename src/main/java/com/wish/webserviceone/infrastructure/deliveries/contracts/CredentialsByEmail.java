package com.wish.webserviceone.infrastructure.deliveries.contracts;

public class CredentialsByEmail {
    private String email;
    private String password;

    public CredentialsByEmail(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CredentialsByEmail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
