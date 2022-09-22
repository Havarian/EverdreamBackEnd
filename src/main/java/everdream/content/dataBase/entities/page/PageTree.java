package everdream.content.dataBase.entities.page;

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

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "current_page_id")
    private Page currentPage;

    @ManyToOne
    @JoinColumn(name = "parent_page_id")
    private Page parentPage;

}