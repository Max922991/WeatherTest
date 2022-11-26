package com.example.weathertest.service;

import com.example.weathertest.dto.ModelDTO;
import com.example.weathertest.models.ModelRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final ModelRepo modelRepo;
    private final MappingUtils mappingUtils;

    public ModelService(MappingUtils mappingUtils, ModelRepo modelRepo) {
        this.mappingUtils = mappingUtils;
        this.modelRepo = modelRepo;
    }

    public List<ModelDTO> findAll(){
        return modelRepo.findAll().stream()
                .map(mappingUtils::mapToModelDTO)
                .collect(Collectors.toList());
    }
}
