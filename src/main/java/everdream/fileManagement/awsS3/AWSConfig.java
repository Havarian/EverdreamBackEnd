package everdream.fileManagement.awsS3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    @Value("${AWSAccessKey}")
    private String AccessKey;
    @Value("${AWSSecretKey}")
    private String SecretKey;

    public AWSCredentials credentials () {
        return new BasicAWSCredentials(AccessKey, SecretKey);
    }

    @Bean
    public AmazonS3 amazonS3 () {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }
    @Bean
    public TransferManager transferManager () {
            return TransferManagerBuilder.standard()
                    .withS3Client(amazonS3())
                    .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
                    .build();
    }
}
