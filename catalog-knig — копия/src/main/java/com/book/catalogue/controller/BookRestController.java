package com.book.catalogue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.book.catalogue.exception.BookException;
import com.book.catalogue.exception.BookNotFoundException;
import com.book.catalogue.exception.ErrorResponse;
import com.book.catalogue.model.BookBean;
import com.book.catalogue.service.BookService;

import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookRestController {

	private final Logger logger = LoggerFactory.getLogger(BookRestController.class);
	
	@Autowired
	private BookService bookService;

	//POST /book/createOrUpdate - создание книги в Body: {"bookName"(название),
	//                                                    "bookDesc"(описание)
	//                                                    "author"{
	//                                                    "firstName"
	//                                                    "lastName"}}
	@ApiOperation(value="Внесите или измените данные о книге")
	@RequestMapping(value="/createOrUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookBean> createOrUpdateBook(@RequestBody BookBean book) {
		logger.debug("Создание или обновление книги");
		if (book != null && StringUtils.isEmpty(book.getBookName()) ) {
			throw new BookException("Название книги не может быть пустым.");
		}
		book = bookService.createBook(book);
		return new ResponseEntity<BookBean>(book, HttpStatus.OK);
	}

	//Get /book/view/{id} - выводит данные о книге с данным id
	@ApiOperation(value="Получить подробную информацию о книге")
	@RequestMapping(value="/view/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookBean> getBook(@PathVariable long id) {
		logger.debug("Получить подробную информацию о книге по id: " + id);
		if(id <= 0) {
			throw new BookNotFoundException("Неверный идентификатор сотрудника. Он должен быть больше 0.");
		}
		BookBean book = bookService.getBookById(id);
		return new ResponseEntity<BookBean>(book, HttpStatus.OK);
	}

	//GET book/all - выводит список всех книг
	@ApiOperation(value="Получить информацию обо всех книгах")
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBean> getAllBooks() {
		logger.debug("Извлечение всех книг");
		List<BookBean> allBooks = bookService.getAllBooks();
		return allBooks;
	}

	//GET /book/search?title=название_книги - выводит список книг с таким названием
	@ApiOperation(value="Поиск книги по названию")
	@RequestMapping(value="/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBean> getBooksByTitle(@RequestParam String title) {
		logger.debug("Поиск книги по названию: {}", title);
		List<BookBean> books = bookService.getBooksByTitle(title);
		return books;
	}

	//GET /book/searchByAuthor?author=имя_или_фамилия_автора - выводит список книг автора с таким именем или фамилией
	@ApiOperation(value="Поиск книги по автору")
	@RequestMapping(value="/searchByAuthor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBean> getBooksByAuthor(@RequestParam String author) {
		logger.debug("Поиск книги по автору: {}", author);
		List<BookBean> books = bookService.getBooksByAuthor(author);
		return books;
	}

	@ExceptionHandler({BookNotFoundException.class, BookException.class})
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
	}

	//DELETE /book/deleteAll - удаляет все книги
	@ApiOperation(value="Удалить все книги")
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAllBooks() {
		logger.debug("Удаление всех книг");
		bookService.deleteAllBooks();
		return ResponseEntity.ok().build();
	}

	//DELETE /book/delete/{id} - удаляет книгу по id
	@ApiOperation(value="Удалить книгу по id")
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBookById(@PathVariable long id) {
		logger.debug("Удаление книги по id: " + id);
		bookService.deleteBookById(id);
		return ResponseEntity.ok().build();
	}

}
