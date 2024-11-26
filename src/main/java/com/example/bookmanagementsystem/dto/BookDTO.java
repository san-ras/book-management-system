package com.example.bookmanagementsystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private String categoryName;
    private String description;
    private String cover;
    private String isbn;
    private Integer pages;
}
