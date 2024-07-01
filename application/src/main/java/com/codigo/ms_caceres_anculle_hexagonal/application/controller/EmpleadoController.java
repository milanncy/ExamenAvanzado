package com.codigo.ms_caceres_anculle_hexagonal.application.controller;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.request.RequestEmpleado;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;
import com.codigo.ms_caceres_anculle_hexagonal.domain.ports.in.EmpleadoServiceIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/empleado")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoServiceIn empleadoServiceIn;

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crear(@RequestBody RequestEmpleado persona){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empleadoServiceIn.crearEmpleadoIn(persona));
    }
    @GetMapping("/buscar/{numDoc}")
    public ResponseEntity<EmpleadoDTO> buscarxdocumento(@PathVariable String numDoc){
        return ResponseEntity.ok(empleadoServiceIn.buscarPorDocumentoIn(numDoc));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<EmpleadoDTO>> todosempleados(){
        return ResponseEntity.ok(empleadoServiceIn.buscarTodosActivosIn());
    }

    @PutMapping("/actualizar/{numDoc}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpresa(@PathVariable String numDoc, @RequestBody EmpleadoDTO empleado){
        return ResponseEntity.ok(empleadoServiceIn.actualizarEmpleadoIn(numDoc, empleado));
    }


    @PutMapping("/eliminar/{numDoc}")
    public ResponseEntity<ResponseBase> borradoLogico(@PathVariable String numDoc) {
        return ResponseEntity.ok(empleadoServiceIn.eliminarLogicoEmpleadoIn(numDoc));
    }
}
