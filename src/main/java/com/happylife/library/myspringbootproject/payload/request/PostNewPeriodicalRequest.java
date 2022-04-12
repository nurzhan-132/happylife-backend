package com.happylife.library.myspringbootproject.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
public class PostNewPeriodicalRequest {

    private String publisher;

    private String name;

    private String description;

    private String available;

    private MultipartFile file;
}
