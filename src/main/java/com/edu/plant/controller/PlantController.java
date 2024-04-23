package com.edu.plant.controller;

import com.edu.plant.domain.utils.errorHandling.EntityAlreadyExistsException;
import com.edu.plant.domain.utils.errorHandling.EntityNotFoundException;
import com.edu.plant.domain.utils.errorHandling.NoResultException;
import com.edu.plant.dto.input.PlantRequestDTO;
import com.edu.plant.dto.output.PlantResponseDTO;
import com.edu.plant.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Tag(name = "Plant", description = "Plant CRUD Api")
@CrossOrigin
@RestController
@RequestMapping(value = "/api/plants")
@RequiredArgsConstructor
public class PlantController {
    private static final Logger logger = LogManager.getLogger(PlantController.class);

    @Autowired
    PlantService plantService;



    /**
     * curl -X POST http://127.0.0.1:8080/api/plants -H "Content-Type: application/json"
     * -d "{\"code\" : \"code3\", \"name\" : \"name3\", \"address\" : \"add3\", \"comment\" : \"c3\", \"logoURL\" : \"logo3\", \"pictureURL\" : \"pic3\"}"
     * @return
     */
    @PostMapping(headers="content-type=application/json")
    @Operation(summary="Save a new plant", description="Save a new plant if not already defined")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPlant(@RequestBody PlantRequestDTO plant) throws EntityAlreadyExistsException {
        logger.trace("Create a plant: " + plant.toString());
        plantService.create(plant);
        return ResponseEntity.status(HttpStatus.CREATED).body(plant);
    }


    /**
     * curl -X GET http://127.0.0.1:8080/api/plants
     */
    @GetMapping(headers = "Accept=application/json")
    @Operation(summary="Retrieve all known plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retrieve all plants",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "no entities found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    public ResponseEntity<?> findAll() throws EntityNotFoundException {
        logger.trace("findAll():");
        List<?> list = plantService.findAll();
        logger.debug("findAll:" + list);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }//end of getAll()

    @GetMapping("{code}")
    public ResponseEntity<?> findById(@PathVariable String code) throws NoResultException {
        logger.trace("findById():" + code) ;
        PlantResponseDTO plant = plantService.findById(code);
        logger.debug("findById:" + plant);
        return ResponseEntity.status(HttpStatus.OK).body(plant);
    }



    @Operation(summary="Delete a plant", description="Delete a plant if it is already defined")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{code}")
    public ResponseEntity<?> delete(@PathVariable String code) throws EntityNotFoundException {
        logger.debug("delete():" + code) ;
        plantService.delete(code);
        return ResponseEntity.status(HttpStatus.OK).body(code);
    }


}
