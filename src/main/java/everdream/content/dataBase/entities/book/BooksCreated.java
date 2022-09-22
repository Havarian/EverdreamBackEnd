package everdream.content.dataBase.entities.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books_created")
public class BooksCreated {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "user_id")
    private Long userId;


}