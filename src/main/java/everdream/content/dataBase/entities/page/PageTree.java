package everdream.content.dataBase.entities.page;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import everdream.content.dataBase.entities.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "page_tree")

public class PageTree {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonBackReference(value = "book-pageTree")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonBackReference (value = "page-currentPage")
    @ManyToOne
    @JoinColumn(name = "current_page_id")
    private Page currentPage;

    @JsonBackReference (value = "page-parentPage")
    @ManyToOne
    @JoinColumn(name = "parent_page_id")
    private Page parentPage;

    public PageTree(Book book, Page currentPage, Page parentPage) {
        this.book = book;
        this.currentPage = currentPage;
        this.parentPage = parentPage;
    }

    public PageTree(Book book, Page currentPage) {
        this.book = book;
        this.currentPage = currentPage;
    }
}