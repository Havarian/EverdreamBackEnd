package everdream.content.services;

import everdream.content.controllers.dtos.AuthorDto;
import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.entities.author.AuthorType;
import everdream.content.dataBase.entities.author.BookAuthorType;
import everdream.content.dataBase.entities.author.EAuthorType;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.repositories.AuthorRepository;
import everdream.content.dataBase.repositories.AuthorTypeRepository;
import everdream.content.dataBase.repositories.BookAuthorTypeRepository;
import everdream.content.exceptions.UnableToDeleteException;
import everdream.content.services.contentMapper.ContentMapper;
import everdream.fileManagement.awsS3.service.AWSService;
import everdream.user.services.AppUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AWSService awsService;
    private final AuthorRepository authorRepository;
    private final AuthorTypeRepository authorTypeRepository;
    private final BookAuthorTypeRepository bookAuthorTypeRepository;

    public AuthorServiceImpl(AWSService awsService, AuthorRepository authorRepository, AuthorTypeRepository authorTypeRepository, BookAuthorTypeRepository bookAuthorTypeRepository) {
        this.awsService = awsService;
        this.authorRepository = authorRepository;
        this.authorTypeRepository = authorTypeRepository;
        this.bookAuthorTypeRepository = bookAuthorTypeRepository;
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) throws IllegalArgumentException, IOException, InterruptedException, FileNotFoundException {
        if (author.getCreatorId() == null) {
        author.setCreatorId(AppUserService.getCurrentUserId());
        }
        Author savedAuthor = authorRepository.save(author);
        if (author.getProfilePictureName() != null) {
            awsService.saveFileToS3(author.getProfilePictureName());
            savedAuthor.setProfilePictureName(author.getProfilePictureName());
        }
        return savedAuthor;
    }

    @Override
    public void addAuthorToBook (Book book, AuthorDto authorDto) throws IOException, InterruptedException {
        AuthorType authorType = authorTypeRepository.findByType(EAuthorType.valueOf(authorDto.getType()))
                .orElseThrow(() -> new IllegalArgumentException("Type not Found!"));
        Author author = saveAuthor(ContentMapper.fromAuthorDto(authorDto));
        if (!bookAuthorTypeRepository.existsByBookAndAuthorAndAuthorType(book, author, authorType)) {
            bookAuthorTypeRepository.save(new BookAuthorType(book, author, authorType));
        }

    }

    @Override
    public void deleteAuthor(Long authorId) throws IllegalArgumentException, UnableToDeleteException {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Wrong user id: " + authorId));
        if (AppUserService.getCurrentUserId().equals(author.getCreatorId()) || AppUserService.getCurrentUserRoles()
                                                                    .stream().anyMatch(e -> e.equals("ADMIN_ROLE"))) {
            authorRepository.deleteById(authorId);
        } else {
            throw new UnableToDeleteException("Unable to delete, no authorities!");
        }
    }

    @Override
    public void removeAuthorFromBook(BookAuthorType bookAuthorType) {
        bookAuthorTypeRepository.delete(bookAuthorType);
    }

}
