package com.java.movieticketingsystem.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * Stores the file in the dedicated directory.
     *
     * @param file The file to be stored.
     * @param userId The file associated to the specific user.
     * @return The confirmation that the file has been stored.
     */
    String store(MultipartFile file, long userId);

    /**
     * Retrieves the file from the server.
     *
     * @param fileName The name of the file to be retreived.
     * @return The file metadata and resources altogether.
     */
    Resource loadFileAsResource(String fileName);

    /**
     * Deletes the file
     *
     * @param fileName The name of the file to be deleted.
     * @return The confirmation that the file has been deleted successfully.
     */
    String deleteFile(String fileName);
}
