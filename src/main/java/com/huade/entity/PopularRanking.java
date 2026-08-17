package com.huade.entity;

public class PopularRanking {
    private int id;
    private String bookTitle;
    private int monthlyBorrowTimes;
    private int totalBorrowTimes;
    private String statisticsMonth;

    public PopularRanking() {
    }

    public PopularRanking(int id, String bookTitle, int monthlyBorrowTimes, int totalBorrowTimes, String statisticsMonth) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.monthlyBorrowTimes = monthlyBorrowTimes;
        this.totalBorrowTimes = totalBorrowTimes;
        this.statisticsMonth = statisticsMonth;
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

    public int getMonthlyBorrowTimes() {
        return monthlyBorrowTimes;
    }

    public void setMonthlyBorrowTimes(int monthlyBorrowTimes) {
        this.monthlyBorrowTimes = monthlyBorrowTimes;
    }

    public int getTotalBorrowTimes() {
        return totalBorrowTimes;
    }

    public void setTotalBorrowTimes(int totalBorrowTimes) {
        this.totalBorrowTimes = totalBorrowTimes;
    }

    public String getStatisticsMonth() {
        return statisticsMonth;
    }

    public void setStatisticsMonth(String statisticsMonth) {
        this.statisticsMonth = statisticsMonth;
    }

    @Override
    public String toString() {
        return "PopularRanking{id=" + id
                + ", bookTitle='" + bookTitle + "'"
                + ", monthlyBorrowTimes=" + monthlyBorrowTimes
                + ", totalBorrowTimes=" + totalBorrowTimes
                + ", statisticsMonth='" + statisticsMonth + "'}";
    }
}