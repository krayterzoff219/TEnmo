package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    @Test
    void getName() {
        Authority authority = new Authority("test");
        assertEquals("test", authority.getName());

        authority.setName("test2");
        assertEquals("test2", authority.getName());
    }
}