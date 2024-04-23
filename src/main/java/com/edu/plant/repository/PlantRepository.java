package com.edu.plant.repository;

import com.edu.plant.domain.entities.PlantEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlantRepository {
    private static final Logger logger = LogManager.getLogger(PlantRepository.class);

    @PersistenceContext
    private EntityManager manager;


    public List<PlantEntity> findAll() {
        String jpql =
            "select p from PlantEntity p " +
            "order by p.PlantCode " ;
        TypedQuery<PlantEntity> typedQuery = manager.createQuery(jpql, PlantEntity.class);
        List<PlantEntity> plantList = typedQuery.getResultList();
        logger.debug("findAll:" + plantList.toString());

        return plantList;
    }

    public PlantEntity findById(String id) {
        String jpql =
            "select p from PlantEntity p " +
            "where p.PlantCode = :plantCode ";
        TypedQuery<PlantEntity> typedQuery = manager.createQuery(jpql, PlantEntity.class);
        typedQuery.setParameter("plantCode", id);
        PlantEntity plant = typedQuery.getSingleResult();
        logger.debug("findById:" + plant.toString());

        return plant;
    }


    @Transactional
    public void save(PlantEntity plant){
        logger.trace("save():" + plant);
        manager.persist(plant);
    }

    @Transactional
    public int delete(String code) {
        String jpql =
            "delete from PlantEntity p " +
            "where p.PlantCode = :plantCode " ;
        Query query = manager.createQuery(jpql);
        query.setParameter("plantCode", code);
        int rows = query.executeUpdate();
        logger.debug("delete rows:" + rows);
        return rows;
    }

}
