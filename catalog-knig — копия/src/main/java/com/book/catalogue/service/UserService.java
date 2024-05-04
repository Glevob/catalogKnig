package com.book.catalogue.service;

import com.book.catalogue.model.UserBean;
import com.book.catalogue.model.ReviewBean;
import com.book.catalogue.model.BookBean;

public interface UserService {
    void addReviewToUser(UserBean user, ReviewBean review);
    void addBookToReadList(UserBean user, BookBean book);
    void saveCurrentPageForBook(UserBean user, BookBean book, int page);
}
