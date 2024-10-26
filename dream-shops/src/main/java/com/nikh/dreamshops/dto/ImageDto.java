package com.nikh.dreamshops.dto;

import lombok.Data;

// ImageDto specifies the data that is exposed to the client interface.
@Data
public class ImageDto { // create a DTO to specify what to return back to the client after the image has been saved
    private Long id;    // Long is in built data type
    private String fileName;
    private String downloadUrl;
}
