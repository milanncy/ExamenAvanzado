package com.codigo.ms_caceres_anculle_hexagonal.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "empleado")
@Getter
@Setter
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String cargo;
    private String tipoDoc;
    private String numDoc;
    private String departamento;
    private double salario;
    private String telefono;
    private String correo;
    private boolean estado;
    private String direccion;
    private Timestamp dateCrea;
    private String usuaCrea;
    private Timestamp dateUpdate;
    private String usuaUpdate;
    private Timestamp dateDelete;
    private String usuaDelete;

}
