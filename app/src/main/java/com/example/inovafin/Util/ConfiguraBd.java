package com.example.inovafin.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfiguraBd {

    private static FirebaseAuth auth;

    private static FirebaseFirestore firestore;

    public static FirebaseAuth Firebaseautentificacao() {
        if (auth == null)
            auth = FirebaseAuth.getInstance();

        return auth;
    }

    public static FirebaseFirestore Firebasefirestore() {
        if (firestore == null)
            firestore = FirebaseFirestore.getInstance();

        return firestore;
    }
}
