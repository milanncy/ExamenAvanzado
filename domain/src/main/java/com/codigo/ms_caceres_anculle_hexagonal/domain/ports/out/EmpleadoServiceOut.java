package com.codigo.ms_caceres_anculle_hexagonal.domain.ports.out;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.request.RequestEmpleado;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;

import java.util.List;

public interface EmpleadoServiceOut {
    EmpleadoDTO crearEmpleadoOut(RequestEmpleado empleado);
    EmpleadoDTO buscarPorDocumentoOut(String numDoc);
    List<EmpleadoDTO> buscarTodosActivosOut();
    EmpleadoDTO actualizarEmpleadoOut(String numDoc, EmpleadoDTO empleadoDTO);
    ResponseBase  eliminarLogicoEmpleadoOut(String numDoc);
}
