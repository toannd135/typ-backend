package com.bookmanagement.service;

import com.bookmanagement.model.Book;
import com.bookmanagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // Act
        Optional<Book> result = bookService.getBookById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
        assertEquals("Test Author", result.get().getAuthor());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateBook() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        Book result = bookService.createBook(testBook);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateBook() {
        // Arrange
        Book updatedBookDetails = new Book();
        updatedBookDetails.setTitle("Updated Title");
        updatedBookDetails.setAuthor("Updated Author");
        updatedBookDetails.setIsbn("123-456-789");
        updatedBookDetails.setPublishedYear(2024);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        Book result = bookService.updateBook(1L, updatedBookDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook() {
        // Arrange
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        // Act
        boolean result = bookService.deleteBook(1L);

        // Assert
        assertTrue(result);
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookNotFound() {
        // Arrange
        when(bookRepository.existsById(999L)).thenReturn(false);

        // Act
        boolean result = bookService.deleteBook(999L);

        // Assert
        assertFalse(result);
        verify(bookRepository, times(1)).existsById(999L);
        verify(bookRepository, never()).deleteById(999L);
    }

}
