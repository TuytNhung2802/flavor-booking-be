package com.flavorbooking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

//    private final String firebaseConfigPath = "E:\\FPT_Software\\Project\\flavor-booking\\BE\\flavor-booking\\src\\main\\resources\\serviceAccountKey.json";
//    @Bean
//    public FirebaseApp initialization() {
//        try {
////            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
////
////            FirebaseOptions options = new FirebaseOptions.Builder()
////                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
////                    .build();
////
////           return FirebaseApp.initializeApp(options);
//            FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setStorageBucket("intern-f77cc.appspot.com")
//                    .build();
//
//            return  FirebaseApp.initializeApp(options);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
