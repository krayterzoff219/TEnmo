package com.techelevator.tenmo.model;

import org.apache.juli.logging.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    @Test
    void getAndSetUsername() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("test");
        assertEquals("test", loginDTO.getUsername());
    }

    @Test
    void getAndSetPassword() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("testing");
        assertEquals("testing", loginDTO.getPassword());
    }
}