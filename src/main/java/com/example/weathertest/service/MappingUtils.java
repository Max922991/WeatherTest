package com.example.weathertest.service;

import com.example.weathertest.dto.ModelDTO;
import com.example.weathertest.models.Model;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public ModelDTO mapToModelDTO(Model entity) {

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setГород(entity.getГород());
        modelDTO.setТемпература(entity.getТемпература());
        modelDTO.setПогода(entity.getПогода());
        modelDTO.setСкорость_ветра(entity.getСкорость_ветра());
        modelDTO.setДавление(entity.getДавление());
        modelDTO.setДата_запроса(entity.getДата_запроса());

        return modelDTO;
    }
}
