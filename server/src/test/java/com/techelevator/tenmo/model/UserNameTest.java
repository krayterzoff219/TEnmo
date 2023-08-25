package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNameTest {

    @Test
    void getAndSetUserName() {
        UserName sut = new UserName();
        sut.setUserName("HelloDolly");
        assertEquals("HelloDolly", sut.getUserName());
    }
}