package model.user;

import model.Department;

public class Supplier extends User {
    private String type;
//constructor 1
    public Supplier(User user, String type) {
        // Call the User constructor to fill user attributes
        super(user.getUserId(), user.getName(), user.getPassword(), user.getUserType(),
                user.getDepartment(), user.getContactInfo());

        // Validate and set the type
        setType(type);
    }
 //constructor 2   
    public Supplier(int userId, String name, String password, String userType, Department department, int contactInfo, String type) {
        super(userId, name, password, userType, department, contactInfo);
        this.type = type;
    }
//getter and setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        // Validate the type
        if ("Medical Supplies".equalsIgnoreCase(type) || "Equipment".equalsIgnoreCase(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Type must be either 'Medical Supplies' or 'Equipment'.");
        }
    }

    // Additional methods if needed
}
