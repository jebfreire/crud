package com.edu.plant.domain.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlantId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String PlantCode;
//    private OffsetDateTime PlantRecordTimestamp = OffsetDateTime.now();

}