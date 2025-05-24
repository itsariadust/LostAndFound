package models;


public class Claims {

  private long claimId;
  private long itemId;
  private String claimantName;
  private String claimantContact;
  private java.sql.Timestamp claimDate;
  private String descriptionOfProof;
  private String status;
  private long approvedBy;
  private java.sql.Timestamp approvalDate;


  public long getClaimId() {
    return claimId;
  }

  public void setClaimId(long claimId) {
    this.claimId = claimId;
  }


  public long getItemId() {
    return itemId;
  }

  public void setItemId(long itemId) {
    this.itemId = itemId;
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


  public String getDescriptionOfProof() {
    return descriptionOfProof;
  }

  public void setDescriptionOfProof(String descriptionOfProof) {
    this.descriptionOfProof = descriptionOfProof;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public long getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(long approvedBy) {
    this.approvedBy = approvedBy;
  }


  public java.sql.Timestamp getApprovalDate() {
    return approvalDate;
  }

  public void setApprovalDate(java.sql.Timestamp approvalDate) {
    this.approvalDate = approvalDate;
  }

}
