package everdream.content.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder

public class PageDto {

    private Long id;
    private Long creatorId;
    private String text;
    private String description;
    private VideoDto video;
    private Set<AudioDto> audio;
    private Long parentId;

    @Override
    public String toString() {
        return "PageDto{" +
                "id=" + id +
                ", creatorId=" + creatorId +
                ", text='" + text + '\'' +
                ", description='" + description + '\'' +
                ", videoDto=" + video +
                ", audioDtos=" + audio +
                ", nextPages=" + parentId +
                '}';
    }
}
