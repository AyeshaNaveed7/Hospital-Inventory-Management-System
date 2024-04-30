package model.hospitalStaff;


import java.time.LocalDate;

public class StaffRequest {
    private int requestId;
    private int inventoryItemId;
    private int deptId;
    private int requestedQuantity;
    private LocalDate requestDate;
    private String status;

    public StaffRequest(int requestId, int inventoryItemId, int deptId, int requestedQuantity, LocalDate requestDate, String status) {
        this.requestId = requestId;
        this.inventoryItemId = inventoryItemId;
        this.deptId = deptId;
        this.requestedQuantity = requestedQuantity;
        this.requestDate = requestDate;
        this.status = status;
    }

    // Getter methods
    public int getRequestId() {
        return requestId;
    }

    public int getInventoryItemId() {
        return inventoryItemId;
    }

    public int getDeptId() {
        return deptId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    // Setter methods
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setInventoryItemId(int inventoryItemId) {
        this.inventoryItemId = inventoryItemId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
               ", Inventory Item ID: " + inventoryItemId +
               ", Department ID: " + deptId +
               ", Requested Quantity: " + requestedQuantity +
               ", Request Date: " + requestDate +
               ", Status: " + status;
    }

   
}

