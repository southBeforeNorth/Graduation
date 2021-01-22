package com.backend.feature.maintenance.dictionary.entity;

import com.backend.comment.entity.UUIDPersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "dictionary_option")
public class DictionaryOption extends UUIDPersistence {

    @Column(name = "OPTION_KEY", length = 255, unique = true)
    private String key;
    @Column(name = "OPTION_VALUE", length = 255)
    private String value;
    @Column(name = "OPTION_META_VALUE", columnDefinition = "LONGBLOB")
    private String description;
    @Column(name = "SEQUENCE")
    private int sequence;
    @Column(name = "IS_DEFAULT")
    private boolean isDefault;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DICTIONARY_ID", nullable = false)
    private Dictionary dictionary;

}
