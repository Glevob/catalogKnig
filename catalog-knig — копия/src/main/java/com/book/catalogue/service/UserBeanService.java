package com.book.catalogue.service;

import com.book.catalogue.model.BookBean;
import com.book.catalogue.model.BookReadingStatus;
import com.book.catalogue.model.ReviewBean;
import com.book.catalogue.model.UserBean;

public class UserBeanService {
    public void addReviewToUser(UserBean user, ReviewBean review) {
        user.getReviews().add(review);
    }

    public void addBookToReadList(UserBean user, BookBean book) {
        user.getReadBooks().add(book);
        user.setReadingStatus(BookReadingStatus.NOT_STARTED); // или другой статус
    }

    public void saveCurrentPageForBook(UserBean user, BookBean book, int page) {
        // имплементация сохранения текущей страницы
    }

}
