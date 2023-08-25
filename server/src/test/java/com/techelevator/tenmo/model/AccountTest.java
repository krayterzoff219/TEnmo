package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void username() {
        Account account = new Account();
        account.setUsername("test");
        assertEquals("test", account.getUsername());
    }


    @Test
    void balance() {
        Account account = new Account();
        account.setBalance(new BigDecimal("10"));
        assertEquals(new BigDecimal("10"), account.getBalance());
    }
}