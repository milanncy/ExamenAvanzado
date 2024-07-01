package com.codigo.ms_caceres_anculle_hexagonal.infraestructure.mapper;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.infraestructure.entity.EmpleadoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public EmpleadoDTO mapToDto(EmpleadoEntity personaEntity){
        return modelMapper.map(personaEntity, EmpleadoDTO.class);
    }

    public EmpleadoEntity mapToEntity(EmpleadoDTO personaDTO){
        return modelMapper.map(personaDTO,EmpleadoEntity.class);
    }
}
