package everdream.content.controllers;

import everdream.content.services.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content/page")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @DeleteMapping ("/{pageID}")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR', 'ROLE_ADMIN')")
    public ResponseEntity<String> deletePage (@PathVariable Long pageID) {
        try {
            pageService.deletePage(pageID);
            return ResponseEntity.ok("Page with id: " + pageID + "deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
