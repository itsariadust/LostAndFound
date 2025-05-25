package models;


public class LostItems {

  private String itemId;
  private String itemName;
  private String itemCategory;
  private String itemDescription;
  private String imageUrl;
  private java.sql.Timestamp dateFound;
  private String locationFound;
  private String foundBy;
  private String status;


  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }


  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }


  public String getItemCategory() {
    return itemCategory;
  }

  public void setItemCategory(String itemCategory) {
    this.itemCategory = itemCategory;
  }


  public String getItemDescription() {
    return itemDescription;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  public java.sql.Timestamp getDateFound() {
    return dateFound;
  }

  public void setDateFound(java.sql.Timestamp dateFound) {
    this.dateFound = dateFound;
  }


  public String getLocationFound() {
    return locationFound;
  }

  public void setLocationFound(String locationFound) {
    this.locationFound = locationFound;
  }


  public String getFoundBy() {
    return foundBy;
  }

  public void setFoundBy(String foundBy) {
    this.foundBy = foundBy;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
