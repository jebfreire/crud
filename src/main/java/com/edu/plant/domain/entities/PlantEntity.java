package com.edu.plant.domain.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "Plant")
@IdClass(PlantId.class)
public class PlantEntity {

    @Id
    @Column(name = "PlantCode", nullable = false, updatable = false)
    private String PlantCode;

    @Column(name = "PlantName", nullable = false)
    private String PlantName;

    @Column(name = "PlantAddress")
    private String PlantAddress;

    @Column(name = "Comment")
    private String Comment;

    @Column(name = "PlantLogoURL")
    private String PlantLogoURL;

    @Column(name = "PlantPictureURL")
    private String PlantPictureURL;

    @Column(name = "PlantRecordTimestamp", nullable = false, updatable = false)
    private OffsetDateTime PlantRecordTimestamp = OffsetDateTime.now();


    @Override
    public String toString() {
        return "PlantEntity{" +
                "PlantCode='" + PlantCode + '\'' +
                ", PlantName='" + PlantName + '\'' +
                ", PlantAddress='" + PlantAddress + '\'' +
                ", Comment='" + Comment + '\'' +
                ", PlantLogoURL='" + PlantLogoURL + '\'' +
                ", PlantPictureURL='" + PlantPictureURL + '\'' +
                ", RecordTimestamp=" + PlantRecordTimestamp +
                '}';
    }
}
