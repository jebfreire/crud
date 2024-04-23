package com.edu.plant.config;

import com.edu.plant.domain.entities.PlantEntity;
import com.edu.plant.dto.input.PlantRequestDTO;
import com.edu.plant.dto.output.PlantResponseDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<String, OffsetDateTime> timestampToObject = new AbstractConverter<String, OffsetDateTime>() {
            protected OffsetDateTime convert(String source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                OffsetDateTime offsetDateTime;
                if(source == null) {
                    source = OffsetDateTime.now().format(formatter);
                }
                offsetDateTime = OffsetDateTime.parse(source, formatter);
                return offsetDateTime;
            }//end of convert
        };
        modelMapper.addConverter(timestampToObject);


        modelMapper.typeMap(PlantEntity.class, PlantResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getPlantCode(), PlantResponseDTO::setCode);
                    mapper.map(src -> src.getPlantName(), PlantResponseDTO::setName);
                    mapper.map(src -> src.getPlantAddress(), PlantResponseDTO::setAddress);
                    mapper.map(src -> src.getPlantName(), PlantResponseDTO::setName);
                });

        modelMapper.typeMap(PlantRequestDTO.class, PlantEntity.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getCode(), PlantEntity::setPlantCode);
                    mapper.map(src -> src.getName(), PlantEntity::setPlantName);
                    mapper.map(src -> src.getAddress(), PlantEntity::setPlantAddress);
                    mapper.map(src -> src.getName(), PlantEntity::setPlantName);
                    mapper.map(src -> src.getPictureURL(), PlantEntity::setPlantPictureURL);
                    mapper.map(src -> src.getLogoURL(), PlantEntity::setPlantLogoURL);
                });

        return modelMapper;
    }
}
