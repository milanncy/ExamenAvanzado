package com.codigo.ms_caceres_anculle_hexagonal.domain.exception.personalizada;

public class EmpleadoException extends RuntimeException{
    public EmpleadoException(String mensaje){
        super(mensaje);
    }
}
