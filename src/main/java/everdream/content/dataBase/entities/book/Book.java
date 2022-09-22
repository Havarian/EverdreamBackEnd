package everdream.content.dataBase.entities.book;

import everdream.content.dataBase.entities.page.Page;
import everdream.content.dataBase.entities.page.PageTree;
import everdream.content.dataBase.entities.author.BookAuthorType;
import everdream.user.services.AppUserService;
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
@Table (name = "books")
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column (name = "title", nullable = false)
    private String title;
    @Column (name = "description")
    private String description;
    @Column (name = "cover_image_name")
    private String coverImageName;
    @Column (name = "is_in_creation")
    private boolean inCreation = true;
    @Column (name = "is_published")
    private boolean isPublished = false;
    @Column (name = "creator_id")
    private Long creatorId;
    @OneToMany (mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private Set<BookAuthorType> bookAuthorType = new HashSet<>();
    @OneToMany (
            mappedBy = "book" ,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Page> pages = new HashSet<>();
    @OneToMany (mappedBy ="book", cascade = CascadeType.REMOVE)
    private Set<PageTree> pageTree = new HashSet<>();

    public void addPages (Set<Page> pages) {
        pages.forEach(page -> {
            page.setCreatorId(AppUserService.getCurrentUserId());
            page.setBook(this);
        });
        this.pages = pages;
    }

    public void removePage (Page page) {
        this.pages.remove(page);
        page.setBook(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (inCreation != book.inCreation) return false;
        if (isPublished != book.isPublished) return false;
        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (description != null ? !description.equals(book.description) : book.description != null) return false;
        if (coverImageName != null ? !coverImageName.equals(book.coverImageName) : book.coverImageName != null)
            return false;
        return creatorId != null ? creatorId.equals(book.creatorId) : book.creatorId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (coverImageName != null ? coverImageName.hashCode() : 0);
        result = 31 * result + (inCreation ? 1 : 0);
        result = 31 * result + (isPublished ? 1 : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coverImageName='" + coverImageName + '\'' +
                ", inCreation=" + inCreation +
                ", isPublished=" + isPublished +
                ", creatorId=" + creatorId +
                '}';
    }
}
