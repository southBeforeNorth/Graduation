package com.backend.feature.maintenance.dictionary.repository;

import com.backend.feature.maintenance.dictionary.entity.DictionaryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryOptionRepository extends JpaRepository<DictionaryOption, String> {

    @Query(value = "select b.* from dictionary a  " +
            "inner join dictionary_option b on a.id = b.dictionary_id  " +
            "where a.name=?1 " +
            "and b.option_key=?2 ", nativeQuery = true)
    DictionaryOption findByNameAndOptionKey(String name, String optionKey);
}
