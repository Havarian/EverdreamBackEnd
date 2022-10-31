package everdream.content.dataBase.entities.author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "profile_picture_name")
    private String profilePictureName;
    @Column(name = "home_page_url")
    private String homePageUrl;
    @Column (name = "created_by")
    private Long creatorId;
    @OneToMany (mappedBy = "author", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<BookAuthorType> bookAuthorTypeSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (!Objects.equals(id, author.id)) return false;
        if (!Objects.equals(name, author.name)) return false;
        if (!Objects.equals(surname, author.surname)) return false;
        if (!Objects.equals(email, author.email)) return false;
        if (!Objects.equals(description, author.description)) return false;
        if (!Objects.equals(profilePictureName, author.profilePictureName))
            return false;
        if (!Objects.equals(homePageUrl, author.homePageUrl)) return false;
        return Objects.equals(creatorId, author.creatorId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (profilePictureName != null ? profilePictureName.hashCode() : 0);
        result = 31 * result + (homePageUrl != null ? homePageUrl.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        return result;
    }
}