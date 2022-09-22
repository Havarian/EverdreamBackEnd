package everdream.content.controllers;

import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.repositories.AuthorRepository;
import everdream.content.exceptions.UnableToDeleteException;
import everdream.content.services.AuthorService;
import everdream.fileManagement.service.TempFileService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping ("/api/content/author")
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final TempFileService tempFileService;

    public AuthorController(AuthorRepository authorRepository, AuthorService authorService, TempFileService tempFileService) {
        this.authorRepository = authorRepository;
        this.authorService = authorService;
        this.tempFileService = tempFileService;
    }

    @PostMapping ("/saveAuthor")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR', 'ROLE_ADMIN')")
    public ResponseEntity<?> saveAuthor (@RequestBody Author author) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(author));
        } catch (IllegalArgumentException | IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping ("/{authorId}")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR', 'ROLE_ADMIN')")
    public ResponseEntity<String> deleteAuthor (@PathVariable Long authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return ResponseEntity.ok("Author with id: " + authorId + " deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnableToDeleteException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping ("/fetchAll")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> fetchAllAuthors () {
        try {
            Set<Author> authors = new HashSet<>();
            authorRepository.findAll().forEach(authors::add);
            return ResponseEntity.ok(authors);
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @GetMapping ("/{authorId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> fetchAuthorById (@PathVariable Long authorId) {
        try {
            return ResponseEntity.ok(authorRepository.findById(authorId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/profilePicture")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CREATOR')")
    public ResponseEntity<?> saveAuthorPicture (@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tempFileService.saveTempFile(file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
