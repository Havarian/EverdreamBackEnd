package everdream.fileManagement.awsS3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AWSServiceImpl implements AWSService{

    @Value("${BucketName}")
    private String bucketName;
    private final AmazonS3 amazonS3Client;
    private final TransferManager transferManager;


    public AWSServiceImpl(AmazonS3 amazonS3Client, TransferManager transferManager) {
        this.amazonS3Client = amazonS3Client;
        this.transferManager = transferManager;
    }

    public List<Bucket> getBucketList () {
        return amazonS3Client.listBuckets();
    }

    @Override
    public void saveFileToS3(File file, String path)
            throws IOException, InterruptedException, AmazonS3Exception {

        Upload transfer = transferManager.upload(bucketName, path, file);
        transfer.waitForCompletion();
        FileUtils.delete(file);
    }

    @Override
    public ByteArrayResource getFileFromS3 (String fileName) throws IOException {

        byte[] file;
        S3ObjectInputStream inputStream = amazonS3Client.getObject(bucketName, fileName).getObjectContent();
        file = IOUtils.toByteArray(inputStream);
        return new ByteArrayResource(file);
    }

    @Override
    public void deleteFileFromS3(String fileName) throws AmazonS3Exception {
        amazonS3Client.deleteObject(bucketName, fileName);
    }
}
