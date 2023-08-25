package Tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private String from;
    private String to;
    private String status;

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        return "Transfer ID: " + transferId +
                "\nTransfer Amount: " + amount +
                "\nFrom: " + from +
                "\nTo: " + to +
                "\nStatus: " + status;

    }
}
