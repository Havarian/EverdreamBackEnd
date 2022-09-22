package everdream.fileManagement.utils;

import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class PathGenerator {

    public String generate (String fileType, Long bookId, Long pageId, String fileName) {
        if (pageId == null) {
            return bookId + "/" + "coverImg/" + fileName ;
        }
        return bookId + "/" + pageId + "/" + fileType + "/" + fileName;
    }

    public String generate (String fileType, Long authorId, String filename) {
        return "AuthorsFiles/" + authorId + "/" + fileType + "/" + filename;
    }
}
