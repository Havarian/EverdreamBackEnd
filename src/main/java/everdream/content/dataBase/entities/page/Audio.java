package everdream.content.dataBase.entities.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table (name = "audios")
public class Audio {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column (name = "file_name")
    private String fileName;
    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audio audio = (Audio) o;

        if (id != null ? !id.equals(audio.id) : audio.id != null) return false;
        return fileName != null ? fileName.equals(audio.fileName) : audio.fileName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
