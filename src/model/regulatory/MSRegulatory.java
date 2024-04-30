package model.regulatory;

import java.time.LocalDate;

public class MSRegulatory {
    private int msRegulatoryId;
    private int itemId;  // Foreign key from InventoryItem
    private String name;
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private int temperature;
    private String complianceStatus;  // Compliant/Non-Compliant, calculated by expiry date

    // Constructors

    public MSRegulatory() {
        // Default constructor
    }

    public MSRegulatory(int msRegulatoryId, int itemId, String name, LocalDate manufacturingDate,
                        LocalDate expiryDate, int temperature, String complianceStatus) {
        this.msRegulatoryId = msRegulatoryId;
        this.itemId = itemId;
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.temperature = temperature;
        this.complianceStatus = complianceStatus;
    }

    // Getters and Setters

    public int getMsRegulatoryId() {
        return msRegulatoryId;
    }

    public void setMsRegulatoryId(int msRegulatoryId) {
        this.msRegulatoryId = msRegulatoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(String complianceStatus) {
        this.complianceStatus = complianceStatus;
    }
}

