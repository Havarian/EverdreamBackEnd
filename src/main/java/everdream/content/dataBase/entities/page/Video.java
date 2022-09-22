package everdream.content.dataBase.entities.page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table (name = "videos")
public class Video {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private Long id;
    @Column (name = "file_name")
    private String fileName;
    @Column (name = "loop_start")
    private Double loopStart;
    @Column (name = "loop_end")
    private Double loopEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (id != null ? !id.equals(video.id) : video.id != null) return false;
        if (fileName != null ? !fileName.equals(video.fileName) : video.fileName != null) return false;
        if (loopStart != null ? !loopStart.equals(video.loopStart) : video.loopStart != null) return false;
        return loopEnd != null ? loopEnd.equals(video.loopEnd) : video.loopEnd == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (loopStart != null ? loopStart.hashCode() : 0);
        result = 31 * result + (loopEnd != null ? loopEnd.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", loopStart=" + loopStart +
                ", loopEnd=" + loopEnd +
                '}';
    }
}
