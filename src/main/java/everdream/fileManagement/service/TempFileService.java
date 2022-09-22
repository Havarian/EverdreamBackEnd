package everdream.fileManagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface TempFileService {
    String saveTempFile (MultipartFile multipartFile) throws IOException;
    File getTempFile (String fileName);
}
