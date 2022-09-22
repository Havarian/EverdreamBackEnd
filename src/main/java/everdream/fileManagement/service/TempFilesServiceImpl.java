package everdream.fileManagement.service;

import everdream.fileManagement.utils.NameGenerator;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TempFilesServiceImpl implements TempFileService {

    private final NameGenerator nameGenerator;
    @Value("${TempFolder}")
    private String tempFolder;

    public TempFilesServiceImpl(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public String saveTempFile (MultipartFile multipartFile) throws IOException {

        String fileName = nameGenerator.generateRandomName(multipartFile.getOriginalFilename());
        File temp = FileUtils.getFile(new File(tempFolder), fileName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), temp);
        return fileName;
    }

    @Override
    public File getTempFile(String fileName) {
        return new File(tempFolder + "/" + fileName);
    }

    @PostConstruct
    public void tempCleaner () {

        try {
            Files.list(Path.of(tempFolder)).forEach(this::deleteUselessTempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void deleteUselessTempFile (Path path) {
    }
 }
