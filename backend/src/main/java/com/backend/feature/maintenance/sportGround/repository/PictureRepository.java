package com.backend.feature.maintenance.sportGround.repository;

import com.backend.feature.maintenance.sportGround.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {

    @Query(value = "SELECT * FROM picture e WHERE e.sport_ground_id =:sportGroundId", nativeQuery = true)
    List<Picture> findAllBySportGroundId(@Param("sportGroundId") String sportGroundId);
}
