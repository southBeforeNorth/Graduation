package com.backend.feature.maintenance.dictionary.assembler;
import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.dictionary.entity.DictionaryOption;
import org.springframework.beans.BeanUtils;

public class DictionaryOptionAssembler {

    private DictionaryOptionAssembler() {
    }

    public static DictionaryOptionDTO convertDTO(DictionaryOption dictionaryOption) {
        DictionaryOptionDTO dictionaryOptionDTO = new DictionaryOptionDTO();
        BeanUtils.copyProperties(dictionaryOption, dictionaryOptionDTO);
        return dictionaryOptionDTO;
    }

    public static DictionaryOption convertEntity(DictionaryOptionDTO dictionaryOptionDTO) {
        DictionaryOption dictionaryOption = new DictionaryOption();
        BeanUtils.copyProperties(dictionaryOptionDTO, dictionaryOption);
        return dictionaryOption;
    }
}
