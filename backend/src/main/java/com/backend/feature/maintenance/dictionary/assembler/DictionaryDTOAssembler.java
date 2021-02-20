package com.backend.feature.maintenance.dictionary.assembler;

import com.backend.feature.maintenance.dictionary.dto.DictionaryDTO;
import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.dictionary.entity.Dictionary;
import com.backend.feature.maintenance.dictionary.entity.DictionaryOption;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DictionaryDTOAssembler {

    private DictionaryDTOAssembler() {
    }

    public static DictionaryDTO convertDTO(Dictionary dictionary) {
        DictionaryDTO dictionaryDTO = new DictionaryDTO();
        BeanUtils.copyProperties(dictionary, dictionaryDTO);
        if (Objects.nonNull(dictionary.getDictionaryOptions())) {
            List<DictionaryOptionDTO> dictionaryOptions = new ArrayList<>();
            dictionary.getDictionaryOptions().forEach((value) -> dictionaryOptions.add(DictionaryOptionAssembler.convertDTO(value)));
            dictionaryOptions.sort(Comparator.comparing(DictionaryOptionDTO::getSequence));
            dictionaryDTO.setDictionaryOptions(dictionaryOptions);
        }
        return dictionaryDTO;
    }

    public static Dictionary convertEntity(DictionaryDTO dictionaryDTO) {
        Dictionary dictionary = new Dictionary();
        BeanUtils.copyProperties(dictionaryDTO, dictionary);
        if (Objects.nonNull(dictionaryDTO.getDictionaryOptions())) {
            List<DictionaryOption> dictionaryOptions = new ArrayList<>();
            dictionaryDTO.getDictionaryOptions().forEach((value) -> {
                DictionaryOption dictionaryOption = DictionaryOptionAssembler.convertEntity(value);
                dictionaryOptions.add(dictionaryOption);
                dictionaryOption.setDictionary(dictionary);
            });
            dictionary.setDictionaryOptions(dictionaryOptions);
        }

        return dictionary;
    }
}
