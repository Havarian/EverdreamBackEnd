package everdream.content.dataBase.repositories;

import everdream.content.dataBase.entities.page.PageTree;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PageTreeRepository extends CrudRepository<PageTree, Long> {
    void deleteByBookId (Long bookId);
    Optional<PageTree> findByCurrentPageId (Long pageId);
    Iterable<PageTree> findByParentPageId (Long parentId);
    void deleteByCurrentPageId (Long pageId);
    void deleteByParentPageId (Long parentPageId);
}