package com.book.catalogue.service;

import java.util.*;
import java.util.stream.Collectors;

import com.book.catalogue.exception.BookNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.book.catalogue.model.AuthorBean;
import com.book.catalogue.model.BookBean;

@Service("bookService")
public class BookService {

	static Map<Long, BookBean> bookTempDataMap = new HashMap<Long, BookBean>();
	static long bookIdCnt = 0;


	public BookBean createBook(BookBean book) {

		if(book != null) {
			if (book.getId() == 0) {
				//create new book
				bookIdCnt = bookIdCnt + 1;
				book.setId(bookIdCnt);
				book.getAuthor().setId(bookIdCnt);
				bookTempDataMap.put(bookIdCnt, book);
			} else {
				//update book info
				return updateBook(book);
			}
		}
		return getBookById(book.getId());
	}
	
	public BookBean updateBook(BookBean book) {
		
		BookBean bookDB = null;
		
		if(book != null && book.getId() > 0) {
			//update book
			bookDB = getBookById(book.getId());
			
			bookDB.setBookName(StringUtils.isEmpty(book.getBookName()) ? "" : book.getBookName())
				.setBookDesc(StringUtils.isEmpty(book.getBookDesc()) ? "" : book.getBookDesc())
				.setAuthor(new AuthorBean(book.getId(), 
						StringUtils.isEmpty(book.getAuthor().getFirstName()) ? "" : book.getAuthor().getFirstName(),
						StringUtils.isEmpty(book.getAuthor().getLastName()) ? "" : book.getAuthor().getLastName()
						));
			
			bookTempDataMap.put(bookDB.getId(), bookDB);
		}
		return getBookById(book.getId());
	}
	
	public BookBean getBookById(long id) {		
		Optional<BookBean> book = Optional.ofNullable(bookTempDataMap.get(id));
		return book.orElseThrow(() -> new BookNotFoundException("Книга с данным id не найдена: " + id));
	}

	public List<BookBean> getAllBooks() {
		List<BookBean> allBooks = new ArrayList<>();
		for (Map.Entry<Long, BookBean> entry : bookTempDataMap.entrySet()) {
			allBooks.add(entry.getValue());
		}
		return allBooks;
	}

	public List<BookBean> getBooksByTitle(String title) {
		List<BookBean> foundBooks = bookTempDataMap.values()
				.stream()
				.filter(book -> book.getBookName().toLowerCase().contains(title.toLowerCase()))
				.collect(Collectors.toList());

		if (foundBooks.isEmpty()) {
			throw new BookNotFoundException("Не найдено книг по названию " + title);
		} else {
			return foundBooks;
		}
	}

	public List<BookBean> getBooksByAuthor(String authorName) {
		List<BookBean> foundBooks = bookTempDataMap.values()
				.stream()
				.filter(book -> book.getAuthor().getFirstName().toLowerCase().contains(authorName.toLowerCase()) ||
						book.getAuthor().getLastName().toLowerCase().contains(authorName.toLowerCase()))
				.collect(Collectors.toList());

		if (foundBooks.isEmpty()) {
			throw new BookNotFoundException("Не найдено книг по автору " + authorName);
		} else {
			return foundBooks;
		}
	}
	public void deleteAllBooks() {
		bookTempDataMap.clear();
		bookIdCnt = 0;
	}

	public void deleteBookById(long id) {
		BookBean book = bookTempDataMap.remove(id);
		if (book == null) {
			throw new BookNotFoundException("Книга с id " + id + " не найдена. Невозможно удалить.");
		}
	}

}
