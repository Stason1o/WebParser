package model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Message {
    private String description;
    private String url;
    private String price;
    private OffsetDateTime postDate;
}
