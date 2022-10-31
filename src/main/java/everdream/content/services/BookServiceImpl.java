package everdream.content.services;

import everdream.content.controllers.dtos.BookDto;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.repositories.BookRepository;
import everdream.content.exceptions.UnableToDeleteException;
import everdream.content.services.contentMapper.ContentMapper;
import everdream.fileManagement.awsS3.service.AWSService;
import everdream.user.services.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final PageService pageService;
    private final AuthorService authorService;
    private final AWSService awsService;

    public BookServiceImpl(BookRepository bookRepository, PageService pageService, AuthorService authorService, AWSService awsService) {
        this.bookRepository = bookRepository;
        this.pageService = pageService;
        this.authorService = authorService;
        this.awsService = awsService;
    }

    @Override
    public BookDto getBookDtoById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: " + bookId + " not found"));
        return ContentMapper.toBookDto(book);
    }

    @Override
    public BookDto saveBookAndRefresh (BookDto bookDto) throws IOException, InterruptedException {
        Book savedBook = saveBook(bookDto);
        bookRepository.refresh(savedBook);
        return ContentMapper.toBookDto(savedBook);
    }

    @Override
    @Transactional
    public Book saveBook(BookDto bookDto) throws IllegalArgumentException, IOException, InterruptedException {

        if (bookDto.getCreatorId() == null) {
            bookDto.setCreatorId(AppUserService.getCurrentUserId());
        }
        Book savedBook = bookRepository.save(ContentMapper.fromBookDto(bookDto));
        // Pages
        if (bookDto.getPages() != null) {
            bookDto.getPages().forEach(pageDto -> {
                try {
                    pageService.savePage(savedBook, pageDto);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // Authors
        if (bookDto.getAuthors() != null) {
            bookDto.getAuthors().forEach(authorDto -> {
                try {
                    authorService.addAuthorToBook(savedBook, authorDto);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // Files
        if (bookDto.getCoverImageName() != null) {
            awsService.saveFileToS3(bookDto.getCoverImageName());
        }

        return savedBook;
    }


    @Override
    @Transactional
    public void deleteBook(Long bookId) throws UnableToDeleteException, NoSuchElementException{
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id: " + bookId + "does not exist!"));
        if (AppUserService.getCurrentUserId().equals(book.getCreatorId()) || AppUserService.getCurrentUserRoles()
                .stream().anyMatch(e -> e.equals("ROLE_ADMIN"))){
            bookRepository.deleteById(bookId);
            pageService.deletePagesByBookId(bookId);
        } else {
            throw new UnableToDeleteException("Unable to delete book with id: " + bookId);
        }
    }
    @Override
    public Set<BookDto> getBooksInCreation() throws NoResultException {
        Set<BookDto> bookDtoSet = new HashSet<>();
        bookRepository.findAllByInCreationIsTrueAndCreatorId(AppUserService.getCurrentUserId())
                .forEach(book -> bookDtoSet.add(ContentMapper.toBookDto(book)));
        if (bookDtoSet.isEmpty()) {
            throw new NoResultException("No books found");
        }
        return bookDtoSet;
    }

    @Override
    public Set<BookDto> getPublishedBooks () throws NoResultException {
        Set<BookDto> bookDtoSet = new HashSet<>();
        bookRepository.findAllByPublishedIsTrue().forEach(book -> bookDtoSet.add(ContentMapper.toBookDto(book)));
        if (bookDtoSet.isEmpty()) {
            throw new NoResultException("No books found");
        }
        return bookDtoSet;
    }

    @Override
    public Set<BookDto> getPendingBooks() throws NoResultException {
        Set<BookDto> bookDtoSet = new HashSet<>();
        bookRepository.findAllByPublishedIsFalseAndInCreationIsFalse().forEach(book ->
                bookDtoSet.add(ContentMapper.toBookDto(book)));
        if (bookDtoSet.isEmpty()) {
            throw new NoResultException("No books found");
        }
        return bookDtoSet;
    }
}

