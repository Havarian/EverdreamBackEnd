package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.page.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
}