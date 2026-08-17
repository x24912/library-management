package com.huade.entity;

public class BookReview {
    private int id;
    private String bookTitle;
    private String reviewerName;
    private int rating;
    private String reviewContent;
    private String reviewTime;

    public BookReview() {
    }

    public BookReview(int id, String bookTitle, String reviewerName, int rating, String reviewContent, String reviewTime) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.reviewContent = reviewContent;
        this.reviewTime = reviewTime;
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

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    @Override
    public String toString() {
        return "BookReview{id=" + id
                + ", bookTitle='" + bookTitle + "'"
                + ", reviewerName='" + reviewerName + "'"
                + ", rating=" + rating
                + ", reviewContent='" + reviewContent + "'"
                + ", reviewTime='" + reviewTime + "'}";
    }
}