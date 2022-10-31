package everdream.content.controllers.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageTreeDto {

    private Long id;
    private BookDto bookDto;
    private PageDto parentPageDto;
    private PageDto currentPageDto;
}
