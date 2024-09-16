package com.flavorbooking.services.firebase;

import com.google.api.client.util.Value;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class FirebaseInitialization {

//public String uploadFile(MultipartFile file) throws IOException {
//    // Khởi tạo FirebaseApp (chỉ cần thực hiện một lần)
//    InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
//
//    FirebaseOptions options = new FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//            .setStorageBucket("intern-f77cc.appspot.com")
//            .build();
//
//    if (FirebaseApp.getApps().isEmpty()) {
//        FirebaseApp.initializeApp(options);
//    }
//
//    // Upload file lên Firebase Storage
//    Bucket bucket = StorageClient.getInstance().bucket();
//    Blob blob = bucket.create(file.getOriginalFilename(), file.getBytes(), file.getContentType());
//
//    // Lấy URL của file vừa upload
//    return blob.getMediaLink();
//}

    public String uploadFile(MultipartFile file) throws IOException {
        // Khởi tạo FirebaseApp (chỉ cần thực hiện một lần)
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("intern-f77cc.appspot.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        // Upload file lên Firebase Storage
        String bucketName = "intern-f77cc.appspot.com";
        BlobInfo blobInfo = StorageClient.getInstance().bucket(bucketName)
                .create(file.getOriginalFilename(), file.getBytes(), file.getContentType());

        // Lấy URL của file vừa upload
        String storagePath = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/"
                + blobInfo.getName().replace("/", "%2F") + "?alt=media";

        return storagePath;
    }
}
