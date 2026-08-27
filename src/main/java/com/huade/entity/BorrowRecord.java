package com.huade.entity;

public class BorrowRecord {
    private int id;
    /** 外键：图书ID（book_title 为冗余快照，兼容旧数据） */
    private int bookId;
    /** 外键：读者ID（reader_name 为冗余快照，兼容旧数据） */
    private int readerId;
    private String bookTitle;
    private String readerName;
    private String borrowDate;
    private String dueDate;
    private String actualReturnDate;
    private String status;

    public BorrowRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
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
                + ", bookId=" + bookId
                + ", readerId=" + readerId
                + ", bookTitle='" + bookTitle + "'"
                + ", readerName='" + readerName + "'"
                + ", borrowDate='" + borrowDate + "'"
                + ", dueDate='" + dueDate + "'"
                + ", actualReturnDate='" + actualReturnDate + "'"
                + ", status='" + status + "'}";
    }
}
