package com.huade.entity;

public class OverdueFine {
    private int id;
    private String readerName;
    private String bookTitle;
    private int overdueDays;
    private double fineAmount;
    private String paymentStatus;

    public OverdueFine() {
    }

    public OverdueFine(int id, String readerName, String bookTitle, int overdueDays, double fineAmount, String paymentStatus) {
        this.id = id;
        this.readerName = readerName;
        this.bookTitle = bookTitle;
        this.overdueDays = overdueDays;
        this.fineAmount = fineAmount;
        this.paymentStatus = paymentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "OverdueFine{id=" + id
                + ", readerName='" + readerName + "'"
                + ", bookTitle='" + bookTitle + "'"
                + ", overdueDays=" + overdueDays
                + ", fineAmount=" + fineAmount
                + ", paymentStatus='" + paymentStatus + "'}";
    }
}