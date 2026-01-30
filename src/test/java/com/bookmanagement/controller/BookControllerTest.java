package com.bookmanagement.controller;

import com.bookmanagement.model.Book;
import com.bookmanagement.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("123-456-789");
        testBook.setPublishedYear(2023);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book2 = new Book(2L, "Another Book", "Another Author", "987-654-321", 2022);
        List<Book> books = Arrays.asList(testBook, book2);
        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        ResponseEntity<List<Book>> result = bookController.getAllBooks();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

        // Act
        ResponseEntity<Book> result = bookController.getBookById(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Test Book", result.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testGetBookByIdNotFound() {
        // Arrange
        when(bookService.getBookById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Book> result = bookController.getBookById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(bookService, times(1)).getBookById(999L);
    }

    @Test
    void testCreateBook() {
        // Arrange
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setIsbn("111-222-333");
        newBook.setPublishedYear(2024);

        Book savedBook = new Book();
        savedBook.setId(3L);
        savedBook.setTitle("New Book");
        savedBook.setAuthor("New Author");
        savedBook.setIsbn("111-222-333");
        savedBook.setPublishedYear(2024);

        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        // Act
        ResponseEntity<Book> result = bookController.createBook(newBook);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(3L, result.getBody().getId());
        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("123-456-789");
        updatedBook.setPublishedYear(2024);

        Book savedUpdatedBook = new Book();
        savedUpdatedBook.setId(1L);
        savedUpdatedBook.setTitle("Updated Title");
        savedUpdatedBook.setAuthor("Updated Author");
        savedUpdatedBook.setIsbn("123-456-789");
        savedUpdatedBook.setPublishedYear(2024);

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(savedUpdatedBook);

        // Act
        ResponseEntity<Book> result = bookController.updateBook(1L, updatedBook);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Updated Title", result.getBody().getTitle());
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    void testUpdateBookNotFound() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        
        when(bookService.updateBook(eq(999L), any(Book.class))).thenReturn(null);

        // Act
        ResponseEntity<Book> result = bookController.updateBook(999L, updatedBook);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(bookService, times(1)).updateBook(eq(999L), any(Book.class));
    }

    @Test
    void testDeleteBook() {
        // Arrange
        when(bookService.deleteBook(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> result = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void testDeleteBookNotFound() {
        // Arrange
        when(bookService.deleteBook(999L)).thenReturn(false);

        // Act
        ResponseEntity<Void> result = bookController.deleteBook(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(bookService, times(1)).deleteBook(999L);
    }

}
