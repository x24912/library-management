package com.huade.entity;

public class BorrowRecord {
    private int id;
    private String bookTitle;
    private String readerName;
    private String borrowDate;
    private String dueDate;
    private String actualReturnDate;
    private String status;

    public BorrowRecord() {
    }

    public BorrowRecord(int id, String bookTitle, String readerName, String borrowDate, String dueDate, String actualReturnDate, String status) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.readerName = readerName;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
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

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowRecord{id=" + id
                + ", bookTitle='" + bookTitle + "'"
                + ", readerName='" + readerName + "'"
                + ", borrowDate='" + borrowDate + "'"
                + ", dueDate='" + dueDate + "'"
                + ", actualReturnDate='" + actualReturnDate + "'"
                + ", status='" + status + "'}";
    }
}