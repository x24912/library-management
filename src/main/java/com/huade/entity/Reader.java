package com.huade.entity;

public class Reader {
    private int id;
    private String fullName;
    private String studentId;
    private String className;
    private int maxBorrowCount;
    private int borrowedCount;
    private String contactNumber;

    public Reader() {
    }

    public Reader(int id, String fullName, String studentId, String className, int maxBorrowCount, int borrowedCount, String contactNumber) {
        this.id = id;
        this.fullName = fullName;
        this.studentId = studentId;
        this.className = className;
        this.maxBorrowCount = maxBorrowCount;
        this.borrowedCount = borrowedCount;
        this.contactNumber = contactNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMaxBorrowCount() {
        return maxBorrowCount;
    }

    public void setMaxBorrowCount(int maxBorrowCount) {
        this.maxBorrowCount = maxBorrowCount;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        this.borrowedCount = borrowedCount;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Reader{id=" + id
                + ", fullName='" + fullName + "'"
                + ", studentId='" + studentId + "'"
                + ", className='" + className + "'"
                + ", maxBorrowCount=" + maxBorrowCount
                + ", borrowedCount=" + borrowedCount
                + ", contactNumber='" + contactNumber + "'}";
    }
}