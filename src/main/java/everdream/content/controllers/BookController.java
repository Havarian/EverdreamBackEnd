package everdream.content.controllers;

import everdream.content.controllers.dtos.BookDto;
import everdream.content.exceptions.UnableToDeleteException;
import everdream.content.services.BookService;
import everdream.content.services.contentMapper.ContentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping ("/api/content/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping ("/saveBook")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR','ROLE_ADMIN')")
    public ResponseEntity<?> saveBook (@RequestBody BookDto bookDto) {
        try {
            return ResponseEntity.ok(bookService.saveBookAndRefresh(bookDto));
        } catch (IllegalArgumentException | IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping ("/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteBook (@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.GONE).body("Book with id: " + bookId + " deleted!");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnableToDeleteException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

//    @PostMapping ("/saveBookCover")
//    @PreAuthorize("hasAnyRole('ROLE_CREATOR','ROLE_ADMIN')")
//    public ResponseEntity<?> saveBookCover (@RequestParam("file")MultipartFile file,
//                                            @RequestParam("bookId") Long bookId) {
//        try {
//            return ResponseEntity.ok(bookService.addBookCover(file, bookId));
//        } catch (IOException | InterruptedException | IllegalArgumentException exception) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//        }
//    }

    @GetMapping ("/inCreation")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR','ROLE_ADMIN')")
    public ResponseEntity<?> getBooksInCreation () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooksInCreation());
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getPendingBooks () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getPendingBooks());
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping ("/published")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_CREATOR','ROLE_ADMIN')")
    public ResponseEntity<?> getPublishedBooks () {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getPublishedBooks());
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PostMapping ("/testSave")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveComplex (@RequestBody BookDto bookComplexDto) {
        return ResponseEntity.ok().body(ContentMapper.fromBookDto(bookComplexDto));
//        System.out.println(ComplexBookMapper.fromComplexBookDto(bookComplexDto));
    }

}
