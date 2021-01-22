package com.backend.feature.maintenance.dictionary.repository;

import com.backend.feature.maintenance.dictionary.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, String> {

    Optional<Dictionary> findByName(String name);

    List<Dictionary> findAllByNameIn(String... names);
}
