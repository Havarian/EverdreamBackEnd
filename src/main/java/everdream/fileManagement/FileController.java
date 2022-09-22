package everdream.fileManagement;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import everdream.fileManagement.awsS3.service.AWSService;
import everdream.fileManagement.service.TempFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping ("/api/content/files")
public class FileController {

    private final AWSService awsService;
    private final TempFileService tempFileService;

    public FileController(AWSService awsService, TempFileService tempFileService) {
        this.awsService = awsService;
        this.tempFileService = tempFileService;
    }

    @GetMapping("/bucketList")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_CREATOR')")
    public List<Bucket> fetchBuckets () {
        return awsService.getBucketList();
    }

    @GetMapping("/{fileName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_CREATOR')")
    public ResponseEntity<?> getFile (@PathVariable  String fileName) {
        try {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+
                    fileName+"\"").body(awsService.getFileFromS3(fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_CREATOR', 'ROLE_ADMIN')")
    public ResponseEntity<?> uploadFile (@RequestParam ("file")MultipartFile file) {
        try {
            return ResponseEntity.ok(tempFileService.saveTempFile(file));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{fileName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_CREATOR')")
    public ResponseEntity<String> deleteFile (@PathVariable String fileName) {
        try {
            awsService.deleteFileFromS3(fileName);
            return ResponseEntity.status(HttpStatus.OK).body("File " + fileName + " deleted");
        } catch (AmazonS3Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
