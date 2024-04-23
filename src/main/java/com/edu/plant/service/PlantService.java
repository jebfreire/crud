package com.edu.plant.service;


import com.edu.plant.domain.entities.GenericAssembler;
import com.edu.plant.domain.entities.PlantEntity;
import com.edu.plant.domain.utils.errorHandling.EntityAlreadyExistsException;
import com.edu.plant.domain.utils.errorHandling.EntityNotFoundException;
import com.edu.plant.domain.utils.errorHandling.NoResultException;
import com.edu.plant.dto.input.PlantRequestDTO;
import com.edu.plant.dto.output.PlantResponseDTO;
import com.edu.plant.repository.PlantRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PlantService {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private GenericAssembler genericAssembler;

    public List<?> findAll()  throws EntityNotFoundException{
        List<PlantEntity> entityList = plantRepository.findAll();
        if (entityList == null || entityList.isEmpty()) throw new EntityNotFoundException(PlantEntity.class, "PlantEntity", "all");
        return genericAssembler.mapTo(entityList, PlantResponseDTO.class);
    }

    public void create(PlantRequestDTO plant) throws EntityAlreadyExistsException {
        PlantEntity plantEntity;
        try {
            plantEntity = (PlantEntity) genericAssembler.mapTo(plant, PlantEntity.class);
            plantRepository.save(plantEntity);
        } catch(Exception e){
            throw new EntityAlreadyExistsException(PlantEntity.class, "PlantEntity", plant.toString());
        }
    }

    public void delete(String id) throws EntityNotFoundException{
        int i = plantRepository.delete(id);
        if(i==0) throw new EntityNotFoundException(PlantEntity.class, "PlantEntity code:", id);
    }

    public PlantResponseDTO findById(String id){
        try {
            PlantEntity entity = plantRepository.findById(id);
            if (entity == null) throw new EntityNotFoundException(PlantEntity.class, "PlantEntity", id);
            return (PlantResponseDTO) genericAssembler.mapTo(entity, PlantResponseDTO.class);
        }catch(Exception e){
            throw new NoResultException(PlantEntity.class, "PlantEntity code:", id);
        }
    }
}
