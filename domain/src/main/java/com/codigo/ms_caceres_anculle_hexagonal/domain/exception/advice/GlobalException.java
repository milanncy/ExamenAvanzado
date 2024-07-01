package com.codigo.ms_caceres_anculle_hexagonal.domain.exception.advice;

import com.codigo.ms_caceres_anculle_hexagonal.domain.exception.personalizada.EmpleadoException;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.constants.Constants;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Optional;

@ControllerAdvice
@Log4j2
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBase> manejandoExepciones(Exception ex){
        log.error("Error manejado desde ******manejandoExepciones******* ");
        ResponseBase response = new ResponseBase(Constants.CODIGO_ERROR_EXCEPTION, "ERROR INTERNO DEL SERVIDOR: " + ex.getMessage(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseBase> manejandoNullPointer(NullPointerException ex){
        log.error("Error manejado desde ******manejandoNullPointer******* ");
        ResponseBase response = new ResponseBase(Constants.CODIGO_ERROR_NULLPOINTER, "ERROR HAY UN DATO NULOOOOO: " + ex.getMessage(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseBase> manejandoNullPointer(IOException ex){
        log.error("Error manejado desde ******manejandoIOException******* ");
        ResponseBase response = new ResponseBase(Constants.CODIGO_ERROR_IOEXCEPTION, "ERROR DE INGRESO O SALIDA: " + ex.getMessage(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseBase> manejandoNullPointer(RuntimeException ex){
        log.error("Error manejado desde ******manejandoRunTime******* ");
        ResponseBase response = new ResponseBase(Constants.CODIGO_ERROR_RUNTIME, "ERROR AL MOMENTO DE EJECUTAR: " + ex.getMessage(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmpleadoException.class)
    public ResponseEntity<ResponseBase> manejandoPersonaException(EmpleadoException ex){
        log.error("Error manejado desde ******manejandoEmpleadoException******* ");
        ResponseBase response = new ResponseBase(Constants.CODIGO_ERROR_PERSONALIZADA, "ERROR EN EL EMPLEADO : " + ex.getMessage(), Optional.empty());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
