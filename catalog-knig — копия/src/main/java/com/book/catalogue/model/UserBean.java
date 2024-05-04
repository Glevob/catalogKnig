package com.book.catalogue.model;

import java.util.List;

public class UserBean {
    private List<ReviewBean> reviews;
    private List<BookBean> readBooks;
    private BookReadingStatus readingStatus;

    public List<ReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewBean> reviews) {
        this.reviews = reviews;
    }

    public List<BookBean> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(List<BookBean> readBooks) {
        this.readBooks = readBooks;
    }

    public BookReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(BookReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }
}
