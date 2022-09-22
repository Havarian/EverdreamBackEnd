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
    private Boolean isRootPage;
    private Long creatorId;
    private String text;
    private VideoDto videoDto;
    private Set<AudioDto> audioDtos;
}
