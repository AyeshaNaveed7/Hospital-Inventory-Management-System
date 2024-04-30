package model.user;

import model.Department;

public class User {
    private int userId;
    private String name;
    private String password;
    private String userType;
    private Department department;
    private int contactInfo;

    public User(int userId, String name, String password, String userType, Department department, int contactInfo) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.userType = userType;
        this.department = department;
        this.contactInfo = contactInfo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Additional methods if needed
}

