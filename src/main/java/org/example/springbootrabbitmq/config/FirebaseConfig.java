package org.example.springbootrabbitmq.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import java.io.InputStream;



@Slf4j
@Configuration
public class FirebaseConfig {

    public void initialize() {
        try {
            InputStream serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("firebase-service-key.json");

            if (serviceAccount == null) {
                throw new IllegalStateException("❌ Firebase service account file not found!");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("✅ FirebaseApp initialized successfully!");
            } else {
                log.info("✅ FirebaseApp already initialized!");
            }
        } catch (Exception e) {
            log.error("❌ Error initializing Firebase: {}", e.getMessage());
            throw new RuntimeException("Firebase initialization failed!", e);
        }
    }



   /* @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.info("✅ FirebaseApp already initialized. Returning existing instance.");
            return FirebaseApp.getInstance();
        }

        InputStream serviceAccount = getClass().getClassLoader()
                .getResourceAsStream("firebase-service-key.json");

        if (serviceAccount == null) {
            throw new IllegalStateException("❌ Firebase service account file not found!");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        log.info("✅ Initializing FirebaseApp...");
        return FirebaseApp.initializeApp(options);
    }
    */

}
