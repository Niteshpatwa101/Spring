package com.nitesh.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitesh.api.model.Book;
import com.nitesh.api.repository.BookRepository;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookRepository repository;
	
	@PostMapping("/addBook")
	@ApiOperation(value = "Adds a book to library", notes = "Provide a JSON object in the request", response = String.class)
	public String saveBook(@RequestBody Book book) {
		repository.save(book);
		return "Added book with Id : " + book.getId();
	}
	
	@GetMapping("/findAllBooks")
	@ApiOperation(value = "Finds all books available in library", notes = "Provide a list of all available books in library", response = Book.class)
	public List<Book> getBook(){
		return repository.findAll();
	}
	
	@GetMapping("/findAllBooks/{id}")
	@ApiOperation(value = "Finds a paricular book from library", notes = "Provide an ID of book required in the request", response = Book.class)
	public Optional<Book> getBook(@PathVariable int id) {
		return repository.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Deletes a book from library", notes = "Provide an ID of book to be deleted in the request", response = String.class)
	public String deleteBook(@PathVariable int id) {
		repository.deleteById(id);
		return "Book deleted with id : " + id;
	}
	
	@PutMapping("/updateBook/{id}")
	@ApiOperation(value = "Updates a book present in library or adds a new book when not found", notes = "Provide an ID of book to be updated in the request", response = Book.class)
	public Book updateBook(@RequestBody Book book, @PathVariable int id) {
		return repository.findById(id).map(bk -> {
			bk.setBookName(book.getBookName());
			bk.setAuthorName(book.getAuthorName());
			return repository.save(bk);
		}).orElseGet(() -> {
			book.setId(id);
			return repository.save(book);
		});
	}
	
}
