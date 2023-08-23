package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private String userName;
    private BigDecimal balance;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
