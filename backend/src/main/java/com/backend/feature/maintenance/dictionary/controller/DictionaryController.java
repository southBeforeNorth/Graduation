package com.backend.feature.maintenance.dictionary.controller;
import java.util.ArrayList;
import java.util.List;

import com.backend.comment.assembler.CommonDTOAssembler;
import com.backend.comment.dto.CommonDTO;
import com.backend.feature.maintenance.dictionary.dto.DictionaryDTO;
import com.backend.feature.maintenance.dictionary.dto.DictionaryOptionDTO;
import com.backend.feature.maintenance.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;


    @PostMapping
    public CommonDTO<DictionaryDTO> create(@RequestBody DictionaryDTO dictionaryDTO) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.createDictionary(dictionaryDTO));
    }


    @GetMapping
    public CommonDTO<List<DictionaryDTO>> list() {
        return dictionaryService.listDictionaries();
    }

    @PostMapping("/{dictionaryId}")
    public CommonDTO<DictionaryDTO> update(@RequestBody DictionaryDTO dictionaryDTO, @PathVariable String dictionaryId) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.updateDictionary(dictionaryDTO, dictionaryId));
    }

    @DeleteMapping("/{dictionaryId}")
    public CommonDTO<DictionaryDTO> delete(@PathVariable String dictionaryId) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.deleteDictionary(dictionaryId));
    }

    @DeleteMapping("/option/{dictionaryOptionID}")
    public CommonDTO<DictionaryOptionDTO> deleteOption(@PathVariable String dictionaryOptionID) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.deleteDictionaryOption(dictionaryOptionID));
    }

    @GetMapping("/{dictionaryName}")
    public CommonDTO<List<DictionaryOptionDTO>> getDictionaryOptionList(@PathVariable String dictionaryName) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.getDictionaryOptions(dictionaryName));
    }

    @GetMapping("multiple/{dictionaryNames}")
    public CommonDTO<List<DictionaryDTO>> getDictionaryList(@PathVariable String[] dictionaryNames) {
        return CommonDTOAssembler.convertTODTO(dictionaryService.getDictionaryByNames(dictionaryNames));
    }
}
