package model.mainInventory;

import javafx.beans.property.*;

import java.time.LocalDate;


public class InventoryItem {
    private final IntegerProperty itemId = new SimpleIntegerProperty(this, "itemId");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final IntegerProperty availableQuantity = new SimpleIntegerProperty(this, "availableQuantity");
    private final IntegerProperty limitQuantity = new SimpleIntegerProperty(this, "limitQuantity");
    private final StringProperty type = new SimpleStringProperty(this, "type");
    private final IntegerProperty supplierId = new SimpleIntegerProperty(this, "supplierId");
    private final ObjectProperty<LocalDate> lastUpdated = new SimpleObjectProperty<>(this, "lastUpdated");
    private final IntegerProperty costPerQuantity = new SimpleIntegerProperty(this, "costPerQuantity");

    // Constructors

    public InventoryItem() {
        // Default constructor
    }

    public InventoryItem(int itemId, String name, int availableQuantity, int limitQuantity, String type, int supplierId, LocalDate lastUpdated, int costPerQuantity) {
        setItemId(itemId);
        setName(name);
        setAvailableQuantity(availableQuantity);
        setLimitQuantity(limitQuantity);
        setType(type);
        setSupplierId(supplierId);
        setLastUpdated(lastUpdated);
        setCostPerQuantity(costPerQuantity);
    }

    // Getters and Setters for JavaFX properties

    public int getItemId() {
        return itemId.get();
    }

    public IntegerProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAvailableQuantity() {
        return availableQuantity.get();
    }

    public IntegerProperty availableQuantityProperty() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity.set(availableQuantity);
    }

    public int getLimitQuantity() {
        return limitQuantity.get();
    }

    public IntegerProperty limitQuantityProperty() {
        return limitQuantity;
    }

    public void setLimitQuantity(int limitQuantity) {
        this.limitQuantity.set(limitQuantity);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public LocalDate getLastUpdated() {
        return lastUpdated.get();
    }

    public ObjectProperty<LocalDate> lastUpdatedProperty() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated2) {
        this.lastUpdated.set(lastUpdated2);
    }

    public int getCostPerQuantity() {
        return costPerQuantity.get();
    }

    public IntegerProperty costPerQuantityProperty() {
        return costPerQuantity;
    }

    public void setCostPerQuantity(int costPerQuantity) {
        this.costPerQuantity.set(costPerQuantity);
    }
}
