package everdream.content.dataBase.entities.author;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
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
    @JsonBackReference
    @OneToMany (mappedBy = "author", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<BookAuthorType> bookAuthorTypeSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != null ? !id.equals(author.id) : author.id != null) return false;
        if (name != null ? !name.equals(author.name) : author.name != null) return false;
        if (surname != null ? !surname.equals(author.surname) : author.surname != null) return false;
        if (email != null ? !email.equals(author.email) : author.email != null) return false;
        if (description != null ? !description.equals(author.description) : author.description != null) return false;
        if (profilePictureName != null ? !profilePictureName.equals(author.profilePictureName) : author.profilePictureName != null)
            return false;
        if (homePageUrl != null ? !homePageUrl.equals(author.homePageUrl) : author.homePageUrl != null) return false;
        return creatorId != null ? creatorId.equals(author.creatorId) : author.creatorId == null;
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