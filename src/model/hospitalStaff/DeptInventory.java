package model.hospitalStaff;

import java.time.LocalDate;

public class DeptInventory {
    private int depItemId;
    private int inventoryItemId;
    private String name;
    private int availableQuantity;
    private int limitQuantity;
    private String type;
    private LocalDate lastUpdated;

    public DeptInventory(int depItemId, int inventoryItemId, String name, int availableQuantity, int limitQuantity, String type, LocalDate lastUpdated) {
        this.depItemId = depItemId;
        this.inventoryItemId = inventoryItemId;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.limitQuantity = limitQuantity;
        this.type = type;
        this.lastUpdated = lastUpdated;
    }

    // Getter methods
    public int getDepItemId() {
        return depItemId;
    }

    public int getInventoryItemId() {
        return inventoryItemId;
    }

    public String getName() {
        return name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getLimitQuantity() {
        return limitQuantity;
    }

    public String getType() {
        return type;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    // Setter methods
    public void setDepItemId(int depItemId) {
        this.depItemId = depItemId;
    }

    public void setInventoryItemId(int inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setLimitQuantity(int limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "DeptItem ID: " + depItemId +
               ", Inventory Item ID: " + inventoryItemId +
               ", Name: " + name +
               ", Available Quantity: " + availableQuantity +
               ", Limit Quantity: " + limitQuantity +
               ", Type: " + type +
               ", Last Updated: " + lastUpdated;
    }
}
