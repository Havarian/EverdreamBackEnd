package everdream.content.services;

import everdream.content.controllers.dtos.AuthorDto;
import everdream.content.dataBase.entities.author.Author;
import everdream.content.dataBase.entities.author.AuthorType;
import everdream.content.dataBase.entities.author.BookAuthorType;
import everdream.content.dataBase.entities.author.EAuthorType;
import everdream.content.dataBase.entities.book.Book;
import everdream.content.dataBase.repositories.AuthorTypeRepository;
import everdream.content.dataBase.repositories.BookAuthorTypeRepository;
import everdream.content.services.contentMapper.ContentMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorTypeRepository bookAuthorTypeRepository;
    private final AuthorTypeRepository authorTypeRepository;
    private final AuthorService authorService;

    public BookAuthorServiceImpl(BookAuthorTypeRepository bookAuthorTypeRepository, AuthorTypeRepository authorTypeRepository, AuthorService authorService) {
        this.bookAuthorTypeRepository = bookAuthorTypeRepository;
        this.authorTypeRepository = authorTypeRepository;
        this.authorService = authorService;
    }

    @Override
    public void addAuthorToBook (Book book, AuthorDto authorDto) throws IOException, InterruptedException {
        AuthorType authorType = authorTypeRepository.findByType(EAuthorType.valueOf(authorDto.getType()))
                .orElseThrow(() -> new IllegalArgumentException("Type not Found!"));
        Author author = authorService.saveAuthor(ContentMapper.fromAuthorDto(authorDto));
        if (!bookAuthorTypeRepository.existsByBookAndAuthorAndAuthorType(book, author, authorType)) {
            bookAuthorTypeRepository.save(new BookAuthorType(book, author, authorType));
        }

    }

    @Override
    public void removeAuthorFromBook(BookAuthorType bookAuthorType) {
        bookAuthorTypeRepository.delete(bookAuthorType);
    }
}
