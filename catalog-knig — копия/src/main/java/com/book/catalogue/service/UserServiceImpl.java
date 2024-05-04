package com.book.catalogue.service;

import com.book.catalogue.model.UserBean;
import com.book.catalogue.model.ReviewBean;
import com.book.catalogue.model.BookBean;
import com.book.catalogue.model.BookReadingStatus;

public class UserServiceImpl implements UserService {
    @Override
    public void addReviewToUser(UserBean user, ReviewBean review) {
        user.getReviews().add(review);
    }

    @Override
    public void addBookToReadList(UserBean user, BookBean book) {
        user.getReadBooks().add(book);
        user.setReadingStatus(BookReadingStatus.NOT_STARTED);
    }

    @Override
    public void saveCurrentPageForBook(UserBean user, BookBean book, int page) {
        // Имплементация сохранения текущей страницы
    }
}
