package com.acpc.mobilepatienttracker;

import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseManager {

    FirebaseFirestore database;

    public DatabaseManager()
    {
        database = FirebaseFirestore.getInstance();
    }
}
