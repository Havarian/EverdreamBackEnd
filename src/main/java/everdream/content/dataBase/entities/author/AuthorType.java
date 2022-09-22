package everdream.content.dataBase.entities.author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "author_types")
public class AuthorType {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private EAuthorType type;

    public AuthorType(EAuthorType type) {
        this.type = type;
    }
}