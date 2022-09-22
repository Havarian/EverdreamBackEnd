package everdream.content.controllers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class AuthorDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String description;
    private String profilePictureName;
    private String homePageUrl;
    private Long creatorId;
    private String type;
}
