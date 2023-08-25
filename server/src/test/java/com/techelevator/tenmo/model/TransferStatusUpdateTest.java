package com.techelevator.tenmo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferStatusUpdateTest {

    @Test
    void getAndSetTransferId() {
        TransferStatusUpdate sut = new TransferStatusUpdate();
        sut.setTransferId(200);
        assertEquals(200, sut.getTransferId());
    }

    @Test
    void getAndSetStatus() {
        TransferStatusUpdate sut = new TransferStatusUpdate();
        sut.setStatus("Approved");
        assertEquals("Approved", sut.getStatus());
    }
}