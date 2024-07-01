package com.codigo.ms_caceres_anculle_hexagonal.domain.impl;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.request.RequestEmpleado;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;
import com.codigo.ms_caceres_anculle_hexagonal.domain.ports.in.EmpleadoServiceIn;
import com.codigo.ms_caceres_anculle_hexagonal.domain.ports.out.EmpleadoServiceOut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoServiceIn {
    private final EmpleadoServiceOut empleadoServiceOut;
    @Override
    public EmpleadoDTO crearEmpleadoIn(RequestEmpleado empleado) {
        return empleadoServiceOut.crearEmpleadoOut(empleado);
    }

    @Override
    public EmpleadoDTO buscarPorDocumentoIn(String numDoc) {
        return empleadoServiceOut.buscarPorDocumentoOut(numDoc);
    }

    @Override
    public List<EmpleadoDTO> buscarTodosActivosIn() {
        return empleadoServiceOut.buscarTodosActivosOut();
    }

    @Override
    public EmpleadoDTO actualizarEmpleadoIn(String numDoc, EmpleadoDTO empleadoDTO) {
        return empleadoServiceOut.actualizarEmpleadoOut(numDoc,empleadoDTO);
    }

    @Override
    public ResponseBase  eliminarLogicoEmpleadoIn(String numDoc) {

        return empleadoServiceOut.eliminarLogicoEmpleadoOut(numDoc);
    }
}
