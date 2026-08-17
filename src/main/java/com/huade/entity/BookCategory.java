package com.huade.entity;

public class BookCategory {
    private int id;
    private String categoryName;
    private int allowedBorrowDays;
    private String description;

    public BookCategory() {
    }

    public BookCategory(int id, String categoryName, int allowedBorrowDays, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.allowedBorrowDays = allowedBorrowDays;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getAllowedBorrowDays() {
        return allowedBorrowDays;
    }

    public void setAllowedBorrowDays(int allowedBorrowDays) {
        this.allowedBorrowDays = allowedBorrowDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BookCategory{id=" + id
                + ", categoryName='" + categoryName + "'"
                + ", allowedBorrowDays=" + allowedBorrowDays
                + ", description='" + description + "'}";
    }
}