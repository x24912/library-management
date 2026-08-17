package com.huade.entity;

public class Book {
    private int id;
    private String bookTitle;
    private String author;
    private String isbn;
    private String category;
    private int stockQuantity;
    private String storageLocation;

    public Book() {
    }

    public Book(int id, String bookTitle, String author, String isbn, String category, int stockQuantity, String storageLocation) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.storageLocation = storageLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    @Override
    public String toString() {
        return "Book{id=" + id
                + ", bookTitle='" + bookTitle + "'"
                + ", author='" + author + "'"
                + ", isbn='" + isbn + "'"
                + ", category='" + category + "'"
                + ", stockQuantity=" + stockQuantity
                + ", storageLocation='" + storageLocation + "'}";
    }
}