package com.backend.feature.maintenance.dictionary.dto;

import lombok.Data;

@Data
public class DictionaryOptionDTO {
    private String id;
    private String key;
    private String value;
    private String description;
    private int sequence;
    private boolean isDefault;
}
