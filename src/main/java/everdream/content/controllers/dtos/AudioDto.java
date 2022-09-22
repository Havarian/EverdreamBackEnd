package everdream.content.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class AudioDto {

    private Long id;
    private String fileName;
}
