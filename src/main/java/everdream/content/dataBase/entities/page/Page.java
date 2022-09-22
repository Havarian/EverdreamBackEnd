package everdream.content.dataBase.entities.page;

import everdream.content.dataBase.entities.book.Book;
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
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "is_root_page")
    private Boolean isRootPage;

    @Column (name = "creator_id")
    private Long creatorId;

    @Column (name = "text")
    private String text;

    @OneToOne (cascade = CascadeType.ALL)
    private Video video;

    @OneToMany (
            mappedBy = "page",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Fetch (FetchMode.SUBSELECT)
    private Set<Audio> audios = new HashSet<>();

    @OneToMany (mappedBy = "currentPage", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PageTree> currentPage = new HashSet<>();

    @OneToMany (mappedBy = "parentPage", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PageTree> parentPage = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public void addAudios (Set<Audio> audios) {
        audios.forEach(audio -> audio.setPage(this));
        this.audios = audios;
    }

    public void removeAudio (Audio audio) {
        this.audios.remove(audio);
        audio.setPage(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (id != null ? !id.equals(page.id) : page.id != null) return false;
        if (isRootPage != null ? !isRootPage.equals(page.isRootPage) : page.isRootPage != null) return false;
        if (creatorId != null ? !creatorId.equals(page.creatorId) : page.creatorId != null) return false;
        if (text != null ? !text.equals(page.text) : page.text != null) return false;
        if (video != null ? !video.equals(page.video) : page.video != null) return false;
        return audios != null ? audios.equals(page.audios) : page.audios == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isRootPage != null ? isRootPage.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        result = 31 * result + (audios != null ? audios.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", isRootPage=" + isRootPage +
                ", creatorId=" + creatorId +
                ", text='" + text + '\'' +
                ", video=" + video +
                ", audios=" + audios +
                '}';
    }
}