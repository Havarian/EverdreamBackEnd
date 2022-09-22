package everdream.content.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
@Builder
public class BookDto {

    private Long id;
    private String title;
    private String description;
    private String coverImageName;
    private boolean inCreation;
    private Long creatorId;
    private Set<AuthorDto> authors;
    private Set<PageDto> pages;
    private Set<PageTreeDto> pageTree;

}
