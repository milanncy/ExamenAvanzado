package com.codigo.ms_caceres_anculle_hexagonal.domain.ports.in;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.request.RequestEmpleado;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;

import java.util.List;

public interface EmpleadoServiceIn {
    EmpleadoDTO crearEmpleadoIn(RequestEmpleado empleado);
    EmpleadoDTO buscarPorDocumentoIn(String numDoc);
    List<EmpleadoDTO> buscarTodosActivosIn();
    EmpleadoDTO actualizarEmpleadoIn(String numDoc, EmpleadoDTO empleadoDTO);
    ResponseBase  eliminarLogicoEmpleadoIn(String numDoc);
}
