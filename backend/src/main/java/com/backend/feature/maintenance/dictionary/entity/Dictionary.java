package com.backend.feature.maintenance.dictionary.entity;

import com.backend.comment.entity.UUIDPersistence;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "dictionary")
public class Dictionary extends UUIDPersistence {

    @Column(name = "Name", length = 255, unique = true)
    private String name;
    @Column(name = "DESCRIPTION", length = 255)
    private String description;
    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "OPTION_KEY")
    private List<DictionaryOption> dictionaryOptions = new ArrayList<>();
}
