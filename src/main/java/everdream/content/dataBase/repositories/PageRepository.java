package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.page.Page;
import org.springframework.data.repository.CrudRepository;

public interface PageRepository extends CrudRepository <Page, Long> {
}
