package org.fintexel.supplier.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import java.io.File;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.s3.AmazonS3Client;

@Service
public class S3Service {

    @Autowired
    AmazonS3Client amazonS3Client;

    @Value("${application.bucket.name}")
    String defaultBucketName = "supplier-app-s3";

    @Value("${application.bucket.basepath}")
    String defaultBaseFolder="docs";

    public List<Bucket> getAllBuckets() {
        return amazonS3Client.listBuckets();
    }


    public void uploadFile(File uploadFile) {
        amazonS3Client.putObject(defaultBucketName,uploadFile.getName() , uploadFile);
    }

    public FileUploadResponse uploadFile(String name,byte[] content)  {
    	FileUploadResponse fileUploadResponse = new FileUploadResponse();
        File file = new File(name);
        file.canWrite();
        file.canRead();
        FileOutputStream iofs = null;
        try {
            iofs = new FileOutputStream(file);
            iofs.write(content);
            amazonS3Client.putObject(defaultBucketName, file.getName(), file);
            amazonS3Client.setObjectAcl(defaultBucketName,file.getName(),CannedAccessControlList.PublicRead);
            URL url = amazonS3Client.getUrl(defaultBucketName, file.getName());
            fileUploadResponse.setFileName(file.getName());
            fileUploadResponse.setPath(url+"");
            System.out.println("-------   "+fileUploadResponse.toString());
            return fileUploadResponse;
        } catch (FileNotFoundException e) {
        	
           throw new VendorNotFoundException(e.getMessage());
        } catch (IOException e) {
        	 throw new VendorNotFoundException(e.getMessage());
        }

    }

    public byte[] getFile(String key) {
        S3Object object = amazonS3Client.getObject(defaultBucketName, defaultBaseFolder+"/"+key);
        S3ObjectInputStream stream = object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(stream);
            object.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<String> listFiles() {

        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(defaultBucketName);

        List<String> keys = new ArrayList<>();

        ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.size() < 1) {
                break;
            }

            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }

            objects = amazonS3Client.listNextBatchOfObjects(objects);
        }

        return keys;
    }
    
    public ResponseEntity<?> getPublicUrl(String key) {
//    	amazonS3Client.setObjectAcl(defaultBucketName,key,CannedAccessControlList.PublicRead);
    	URL url = amazonS3Client.getUrl(defaultBucketName, key);
    	String path = url.getPath();
    	return ResponseEntity.ok(url);
    }

}
