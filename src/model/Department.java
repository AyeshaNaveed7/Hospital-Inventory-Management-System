package model;

public class Department {
    private int deptId;
    private String name;
    private int contactInfo;

    public Department(int deptId, String name, int contactInfo) {
        this.deptId = deptId;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Additional methods if needed
}

