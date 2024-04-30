package model.regulatory;
import java.time.LocalDate;

public class ERegulatory {
    private int eRegulatoryId;
    private int itemId;  // Foreign key from InventoryItem
    private String name;
    private LocalDate warrantyDate;
    private LocalDate datePeriodicMaintenance;
    private String complianceStatus;  // Compliant/Non-Compliant, filled by manager

    // Constructors

    public ERegulatory() {
        // Default constructor
    }

    public ERegulatory(int eRegulatoryId, int itemId, String name, LocalDate warrantyDate,
                       LocalDate datePeriodicMaintenance, String complianceStatus) {
        this.eRegulatoryId = eRegulatoryId;
        this.itemId = itemId;
        this.name = name;
        this.warrantyDate = warrantyDate;
        this.datePeriodicMaintenance = datePeriodicMaintenance;
        this.complianceStatus = complianceStatus;
    }

    // Getters and Setters

    public int getERegulatoryId() {
        return eRegulatoryId;
    }

    public void setERegulatoryId(int eRegulatoryId) {
        this.eRegulatoryId = eRegulatoryId;
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

    public LocalDate getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(LocalDate warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public LocalDate getDatePeriodicMaintenance() {
        return datePeriodicMaintenance;
    }

    public void setDatePeriodicMaintenance(LocalDate datePeriodicMaintenance) {
        this.datePeriodicMaintenance = datePeriodicMaintenance;
    }

    public String getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(String complianceStatus) {
        this.complianceStatus = complianceStatus;
    }
}

