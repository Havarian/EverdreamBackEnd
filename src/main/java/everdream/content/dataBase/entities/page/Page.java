package everdream.content.dataBase.entities.page;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import everdream.content.dataBase.entities.book.Book;
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
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column (name = "creator_id")
    private Long creatorId;
    @Column (name = "text")
    private String text;
    @Column(name = "description")
    private String description;
    @OneToOne (cascade = CascadeType.ALL)
    private Video video;
    @OneToMany (
            mappedBy = "page",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Fetch (FetchMode.SUBSELECT)
    private Set<Audio> audios = new HashSet<>();
    @JsonManagedReference (value = "page-currentPage")
    @OneToMany (mappedBy = "currentPage", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PageTree> currentPage = new HashSet<>();
    @JsonManagedReference(value = "page-parentPage")
    @OneToMany (mappedBy = "parentPage", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PageTree> parentPage = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (!Objects.equals(id, page.id)) return false;
        if (!Objects.equals(description, page.description)) return false;
        if (!Objects.equals(creatorId, page.creatorId)) return false;
        if (!Objects.equals(text, page.text)) return false;
        return Objects.equals(video, page.video);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creatorId=" + creatorId +
                ", text='" + text + '\'' +
                ", video=" + video +
                '}';
    }
}