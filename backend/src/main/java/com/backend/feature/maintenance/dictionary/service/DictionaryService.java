package com.backend.feature.maintenance.dictionary.service;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.dictionary.assembler.DictionaryDTOAssembler;
import com.backend.feature.maintenance.dictionary.assembler.DictionaryOptionAssembler;
import com.backend.feature.maintenance.dictionary.dto.DictionaryDTO;
import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.dictionary.entity.Dictionary;
import com.backend.feature.maintenance.dictionary.entity.DictionaryOption;
import com.backend.feature.maintenance.dictionary.exception.DictionaryException;
import com.backend.feature.maintenance.dictionary.repository.DictionaryOptionRepository;
import com.backend.feature.maintenance.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DictionaryService {

    @Autowired
    private  DictionaryRepository dictionaryRepository;
    @Autowired
    private  DictionaryOptionRepository dictionaryOptionRepository;



    public DictionaryDTO createDictionary(DictionaryDTO dictionaryDTO) {
        Optional<Dictionary> old = dictionaryRepository.findByName(dictionaryDTO.getName());
        if(old.isPresent()){
            throw new DictionaryException(DictionaryException.DICTIONARY_IS_EXIST);
        }
        Dictionary dictionary = DictionaryDTOAssembler.convertEntity(dictionaryDTO);
        return DictionaryDTOAssembler.convertDTO(dictionaryRepository.save(dictionary));
    }

    public DictionaryDTO deleteDictionary(String dictionaryID) {
        Dictionary dictionary = dictionaryRepository.findById(dictionaryID)
                .orElseThrow(() -> new DictionaryException(DictionaryException.DICTIONARY_NO_EXIST));
        dictionaryRepository.deleteById(dictionaryID);
        return DictionaryDTOAssembler.convertDTO(dictionary);
    }

    public DictionaryDTO updateDictionary(DictionaryDTO dictionaryDTO, String dictionaryID) {
        String exitsDictionaryID = dictionaryRepository.findById(dictionaryID)
                .orElseThrow(() -> new DictionaryException(DictionaryException.DICTIONARY_NO_EXIST))
                .getId();
        Dictionary dictionary = DictionaryDTOAssembler.convertEntity(dictionaryDTO);
        dictionary.setId(exitsDictionaryID);
        return DictionaryDTOAssembler.convertDTO(dictionaryRepository.saveAndFlush(dictionary));
    }

    public CommonDTO<List<DictionaryDTO>> listDictionaries() {
        return dictionaryRepository.findAll().stream()
                .sorted(Comparator.comparing(Dictionary::getName))
                .map(DictionaryDTOAssembler::convertDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), CommonDTOAssembler::convertTODTO));
    }


    public List<DictionaryOptionDTO> getDictionaryOptions(String dictionaryEnum) {
        return dictionaryRepository.findByName(dictionaryEnum)
                .map(DictionaryDTOAssembler::convertDTO)
                .map(DictionaryDTO::getDictionaryOptions)
                .orElseThrow(() -> new DictionaryException(DictionaryException.DICTIONARY_NO_EXIST));
    }

    public List<DictionaryDTO> getDictionaryByNames(String... dictionaryNames) {
        return dictionaryRepository.findAllByNameIn(dictionaryNames).stream()
                .map(DictionaryDTOAssembler::convertDTO)
                .collect(Collectors.toList());
    }

    public DictionaryOptionDTO deleteDictionaryOption(String dictionaryOptionID) {
        DictionaryOption dictionary = dictionaryOptionRepository.findById(dictionaryOptionID)
                .orElseThrow(() ->new DictionaryException(DictionaryException.DICTIONARY_NO_EXIST));
        dictionaryOptionRepository.deleteById(dictionaryOptionID);
        return DictionaryOptionAssembler.convertDTO(dictionary);
    }

}
