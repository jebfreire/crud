package com.edu.plant.dto.output;


import lombok.*;
import org.apache.logging.log4j.message.StringFormattedMessage;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlantResponseDTO {
    private String code;
    private String name;
    private String address;
    private String comment;
}
