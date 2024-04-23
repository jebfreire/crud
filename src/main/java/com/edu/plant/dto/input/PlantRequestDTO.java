package com.edu.plant.dto.input;


import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlantRequestDTO {
    private String code;
    private String name;
    private String address;
    private String comment;
    private String logoURL;
    private String pictureURL;
//    private String plantCode;
//    private String plantName;
//    private String plantAddress;
//    private String comment;
//    private String plantLogoURL;
//    private String plantPictureURL;
//    private String plantRecordTimestamp;
}
