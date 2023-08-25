package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void getTransferId() {
        Transfer sut = new Transfer();
        sut.setTransferId(101);
        assertEquals(101, sut.getTransferId());
    }

    @Test
    void getTransferAmount() {
        Transfer sut = new Transfer();
        sut.setTransferAmount(new BigDecimal("109"));
        assertEquals(new BigDecimal("109"), sut.getTransferAmount());
    }

    @Test
    void getFrom() {
        Transfer sut = new Transfer();
        sut.setFrom("hi");
        assertEquals("hi", sut.getFrom());
    }

    @Test
    void getTo() {
        Transfer sut = new Transfer();
        sut.setTo("bye");
        assertEquals("bye", sut.getTo());
    }

    @Test
    void getStatus() {
        Transfer sut = new Transfer();
        sut.setStatus("Rejected");
        assertEquals("Rejected", sut.getStatus());
    }
}