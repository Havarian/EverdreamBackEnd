package everdream.fileManagement.utils;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import liquibase.util.FilenameUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

@Component
public class NameGenerator {

    public String generateRandomName (String fileName) {
        int length = 12;
        return RandomStringUtils.randomAlphanumeric(length) + "." + FilenameUtils.getExtension(fileName);
    }
}
