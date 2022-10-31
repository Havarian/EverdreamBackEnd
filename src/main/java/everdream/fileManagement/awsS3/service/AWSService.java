package everdream.fileManagement.awsS3.service;

import com.amazonaws.services.s3.model.Bucket;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AWSService {

    List<Bucket> getBucketList ();
    void saveFileToS3 (String fileName) throws IOException, InterruptedException;
    ByteArrayResource getFileFromS3 (String fileName) throws IOException;
    void deleteFileFromS3 (String fileName);
    boolean isFileExists (String fileName);
}
