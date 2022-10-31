package everdream.fileManagement.awsS3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import everdream.fileManagement.service.TempFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AWSServiceImpl implements AWSService{

    @Value("${BucketName}")
    private String bucketName;
    private final AmazonS3 amazonS3Client;
    private final TransferManager transferManager;
    private final TempFileService tempFileService;


    public AWSServiceImpl(AmazonS3 amazonS3Client, TransferManager transferManager, TempFileService tempFileService) {
        this.amazonS3Client = amazonS3Client;
        this.transferManager = transferManager;
        this.tempFileService = tempFileService;
    }

    public List<Bucket> getBucketList () {
        return amazonS3Client.listBuckets();
    }

    @Override
    public void saveFileToS3(String fileName) throws AmazonS3Exception, InterruptedException, IOException {

        File file = tempFileService.getTempFile(fileName);
        Upload transfer = transferManager.upload(bucketName, fileName, file);
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

    @Override
    public boolean isFileExists (String fileName) throws AmazonS3Exception{
        return amazonS3Client.doesObjectExist(bucketName, fileName);
    }
}
