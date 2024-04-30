package model.mainInventory;

import java.time.LocalDate;

public class Equipment extends InventoryItem {
    private String condition;
    private LocalDate warrantyDate;

    // Constructors

    public Equipment() {
        // Default constructor
    }

    public Equipment(InventoryItem inventoryItem, String condition, LocalDate warrantyDate) {
        super(inventoryItem.getItemId(), inventoryItem.getName(), inventoryItem.getAvailableQuantity(),
                inventoryItem.getLimitQuantity(), inventoryItem.getType(), inventoryItem.getSupplierId(),
                inventoryItem.getLastUpdated(), inventoryItem.getCostPerQuantity());

        this.condition = condition;
        this.warrantyDate = warrantyDate;
    }

    public Equipment(int itemId, String name, int availableQuantity, int limitQuantity, String type, int supplierId, LocalDate lastUpdated, int costPerQuantity, String condition, LocalDate warrantyDate) {
        super(itemId, name, availableQuantity, limitQuantity, type, supplierId, lastUpdated, costPerQuantity);
        this.condition = condition;
        this.warrantyDate = warrantyDate;
    }
    // Getters and Setters

   

	public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public LocalDate getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(LocalDate warrantyDate) {
        this.warrantyDate = warrantyDate;
    }
}
