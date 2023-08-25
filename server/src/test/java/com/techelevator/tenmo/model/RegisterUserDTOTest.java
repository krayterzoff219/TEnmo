package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserDTOTest {

    @Test
    void getAndSetUsername() {
        RegisterUserDTO sut = new RegisterUserDTO();
        sut.setUsername("test");
        assertEquals("test", sut.getUsername());
    }

    @Test
    void getAndSetPassword() {
        RegisterUserDTO sut = new RegisterUserDTO();
        sut.setPassword("testing");
        assertEquals("testing", sut.getPassword());
    }
}