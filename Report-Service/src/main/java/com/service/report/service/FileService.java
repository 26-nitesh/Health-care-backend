package com.service.report.service;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class FileService {

    @Autowired
    private GridFsOperations gridFsOperations;

    public String saveFile(MultipartFile file) throws IOException {
        // Get the file name
        String fileName = file.getOriginalFilename();

        // Create metadata for the file
        DBObject metaData = new BasicDBObject();
        metaData.put("contentType", file.getContentType());

        // Store the file in MongoDB using GridFS
        ObjectId fileId = gridFsOperations.store(
                file.getInputStream(),
                fileName,
                file.getContentType(),
                metaData
        );

        // Return the file ID
        return fileId.toString();
    }
}
