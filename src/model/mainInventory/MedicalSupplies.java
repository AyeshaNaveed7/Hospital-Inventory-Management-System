package model.mainInventory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicalSupplies extends InventoryItem {
    private LocalDate manufacturingDate;
    private LocalDate expiryDate;
    private int temperature;
    private String dosageForm;

    // Constructors

    public MedicalSupplies() {
        // Default constructor
    }

    public MedicalSupplies(int itemId, String name, int availableQuantity, int limitQuantity, String type, int supplierId, LocalDate lastUpdated, int costPerQuantity,
                           LocalDate manufacturingDate, LocalDate expiryDate, int temperature, String dosageForm) {
        super(itemId, name, availableQuantity, limitQuantity, type, supplierId, lastUpdated, costPerQuantity);
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.temperature = temperature;
        this.dosageForm = dosageForm;
    }

    
 // Constructor that accepts an InventoryItem object and additional attributes
    public MedicalSupplies(InventoryItem inventoryItem, LocalDate manufacturingDate, LocalDate expiryDate, int temperature, String dosageForm) {
        super(inventoryItem.getItemId(), inventoryItem.getName(), inventoryItem.getAvailableQuantity(),
                inventoryItem.getLimitQuantity(), inventoryItem.getType(), inventoryItem.getSupplierId(),
                inventoryItem.getLastUpdated(), inventoryItem.getCostPerQuantity());

        // Set additional attributes specific to MedicalSupplies
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.temperature = temperature;
        this.dosageForm = dosageForm;
    }

    
    // Getters and Setters

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

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
}

