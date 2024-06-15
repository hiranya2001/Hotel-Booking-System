package com.example.demo.hotel.service;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;

public class BlobHelper {

    public static String blobToBase64(Blob blob) {
        try {
            int blobLength = (int) blob.length();
            byte[] blobAsBytes = blob.getBytes(1, blobLength);
            // release the blob and free up memory. (since JDBC 4.0)
            blob.free();
            return Base64.getEncoder().encodeToString(blobAsBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error converting BLOB to Base64", e);
        }
    }
}

