package com.backend.feature.maintenance.dictionary.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class DictionaryDTO {
    private String id;
    private String name;
    private String description;
    private String createdBy;
    private Date createdTime;
    private String lastModifiedBy;
    private Date lastModifiedTime;
    private List<DictionaryOptionDTO> dictionaryOptions;
}
