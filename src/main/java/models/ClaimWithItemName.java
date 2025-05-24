package models;

import java.time.LocalDate;

public class ClaimWithItemName {
    private long claimId;
    private String claimantName;
    private String itemName;
    private String claimantContact;
    private java.sql.Timestamp  claimDate;
    private String status;

    public long getClaimId() {
        return claimId;
    }

    public void setClaimId(long claimId) {
        this.claimId = claimId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getClaimantName() {
        return claimantName;
    }

    public void setClaimantName(String claimantName) {
        this.claimantName = claimantName;
    }


    public String getClaimantContact() {
        return claimantContact;
    }

    public void setClaimantContact(String claimantContact) {
        this.claimantContact = claimantContact;
    }


    public java.sql.Timestamp getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(java.sql.Timestamp claimDate) {
        this.claimDate = claimDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
