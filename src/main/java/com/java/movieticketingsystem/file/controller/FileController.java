package com.java.movieticketingsystem.file.controller;

import com.java.movieticketingsystem.Movie.model.MovieDTO;
import com.java.movieticketingsystem.Movie.service.MovieService;
import com.java.movieticketingsystem.file.service.FileService;
import com.java.movieticketingsystem.utils.RestHelper;
import com.java.movieticketingsystem.utils.RestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "File Management")
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private MovieService movieService;

    /**
     * Uploads a movie image for the movies
     *
     * @param file The image file to be uploaded
     * @return Response containing the updated user information
     */
    @PostMapping("/movie/{movieId}/image")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<RestResponse> uploadMovieImage(
            @PathVariable Long movieId,
            @RequestParam("file") MultipartFile file
    ) {
        MovieDTO updatedMovie = movieService.updateMovieImage(movieId, file);
        Map<String, Object> response = new HashMap<>();
        response.put("movie", updatedMovie);
        return RestHelper.responseSuccess(response);
    }

    /**
     * Retrieves a movie image
     *
     * @param fileName The name of the file to retrieve
     * @return The image resource
     */
    @GetMapping("/movie-image/{fileName:.+}")
    public ResponseEntity<Resource> getMovieImage(@PathVariable String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Deletes a moive image
     *
     * @param fileName The name of the file to delete
     * @return Response indicating success of the operation
     */
    @DeleteMapping("/movie-image/{fileName:.+}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<RestResponse> deleteMoiveImage(@PathVariable String fileName) {
        String message = fileService.deleteFile(fileName);
        return RestHelper.responseMessage(message);
    }

}
