package com.codigo.ms_caceres_anculle_hexagonal.infraestructure.adapters;

import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.constants.Constants;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.dto.EmpleadoDTO;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.request.RequestEmpleado;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseBase;
import com.codigo.ms_caceres_anculle_hexagonal.domain.aggregates.response.ResponseReniec;
import com.codigo.ms_caceres_anculle_hexagonal.domain.exception.personalizada.EmpleadoException;
import com.codigo.ms_caceres_anculle_hexagonal.domain.ports.out.EmpleadoServiceOut;
import com.codigo.ms_caceres_anculle_hexagonal.infraestructure.dao.EmpleadoRepository;
import com.codigo.ms_caceres_anculle_hexagonal.infraestructure.entity.EmpleadoEntity;
import com.codigo.ms_caceres_anculle_hexagonal.infraestructure.mapper.EmpleadoMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmpleadoAdapter implements EmpleadoServiceOut {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final RestTemplate restTemplate;

    @Value("${token}")
    private String tokenApi;


    @Override
    public EmpleadoDTO crearEmpleadoOut(RequestEmpleado requestEmpleado) {
        boolean existe = empleadoRepository.existsByNumDoc(requestEmpleado.getNumDoc());
        if(existe){
            throw new EmpleadoException("Error, el empleado ya existe");
        }else{
            Optional<EmpleadoEntity> personaEntityOpt = getEntityRestTemplate(requestEmpleado);
            if (personaEntityOpt.isPresent()) {
                return empleadoMapper.mapToDto(empleadoRepository.save(personaEntityOpt.get()));
            } else {
                throw new RuntimeException("No se pudo crear el empleado");
            }
        }

    }

    @Override
    public EmpleadoDTO buscarPorDocumentoOut(String numDoc) {
        Optional<EmpleadoEntity> empleadoOpt = empleadoRepository.findByNumDoc(numDoc);
        if (empleadoOpt.isPresent()) {
            EmpleadoDTO empleadoDTO = empleadoMapper.mapToDto(empleadoOpt.get());
            return  empleadoDTO;
        } else {
            throw new EmpleadoException("Error, el empleado no existe");
        }
    }

    @Override
    public List<EmpleadoDTO> buscarTodosActivosOut() {
        log.error("Iniciando búsqueda de empleados activos.");
        List<EmpleadoEntity> empleadosActivos = empleadoRepository.findByEstado(true);

        if (empleadosActivos.isEmpty()) {
            log.info("No se encontraron empleados activos.");
            log.error("NO ENCONTRAMOS NADA");
            throw new EmpleadoException("No se encontraron empleados activos");
        } else {
            log.info("Se encontraron {} empleados activos.", empleadosActivos.size());
            log.error("objeto encontrado");
            return empleadosActivos.stream()
                    .map(empleadoMapper::mapToDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public EmpleadoDTO actualizarEmpleadoOut(String numDoc, EmpleadoDTO empleadoDTO) {
        boolean existe = empleadoRepository.existsByNumDoc(numDoc);
        if(existe){
            Optional<EmpleadoEntity> personaEntityOpt = updateEmpleado(numDoc,empleadoDTO);
            if (personaEntityOpt.isPresent()) {
                return empleadoMapper.mapToDto(empleadoRepository.save(personaEntityOpt.get()));
            } else {
                throw new RuntimeException("No se pudo crear el empleado");
            }

        }else{
            throw new EmpleadoException("Error, el empleado no existe");
        }
    }

    @Override
    public ResponseBase eliminarLogicoEmpleadoOut(String numDoc) {
        Optional<EmpleadoEntity> empleadoOpt = empleadoRepository.findByNumDoc(numDoc);

        if (empleadoOpt.isPresent()) {
            EmpleadoEntity empleadoEntity = empleadoOpt.get();
            empleadoEntity.setEstado(false);
            empleadoEntity.setUsuaDelete(Constants.USU_ADMIN);
            empleadoEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));

            empleadoRepository.save(empleadoEntity);
            return new ResponseBase(Constants.CODIGO_EXITO, "Empleado eliminado lógicamente", null);
        } else {
            throw new EmpleadoException("No se encontró el empleado con número de documento: " + numDoc);
        }
    }

    private Optional<EmpleadoEntity> updateEmpleado(String numDoc, EmpleadoDTO empleadoDTO) {

            Optional<EmpleadoEntity> empleadoEntityOpt = empleadoRepository.findByNumDoc(numDoc);
            if (empleadoEntityOpt.isPresent()) {
                EmpleadoEntity empleadoEntity = empleadoEntityOpt.get();

                if (empleadoDTO.getEdad() > 0 && empleadoDTO.getCargo() != null && empleadoDTO.getSalario() > 0 && empleadoDTO.getTelefono() != null && empleadoDTO.getCorreo() != null && empleadoDTO.getDepartamento() != null) {
                    empleadoEntity.setEdad(empleadoDTO.getEdad());
                    empleadoEntity.setCargo(empleadoDTO.getCargo());
                    empleadoEntity.setSalario(empleadoDTO.getSalario());
                    empleadoEntity.setTelefono(empleadoDTO.getTelefono());
                    empleadoEntity.setCorreo(empleadoDTO.getCorreo());
                    empleadoEntity.setDepartamento(empleadoDTO.getDepartamento());
                    empleadoEntity.setUsuaUpdate(Constants.USU_ADMIN);
                    empleadoEntity.setDateUpdate(new Timestamp(System.currentTimeMillis()));
                    return Optional.of(empleadoEntity);
                }else{
                    throw new EmpleadoException("Error, Faltan campos");
                }
            } else {
                throw new EmpleadoException("No se encontró el empleado");
            }
    }

    private Optional<EmpleadoEntity> getEntityRestTemplate(RequestEmpleado requestEmpleado){
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero="+requestEmpleado.getNumDoc();

        try {
            ResponseEntity<ResponseReniec> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(createHeaders(tokenApi)),
                    ResponseReniec.class
            );
            ResponseReniec responseReniec = response.getBody();
            if (responseReniec == null) {
                log.error("Respuesta del API externa es nula");
                return Optional.empty();
            }else{
                log.info("SI TRAE RESPUESTA "+responseReniec );
            }
            EmpleadoEntity empleadoEntity = new EmpleadoEntity();

            empleadoEntity.setNombre(responseReniec.getNombres());
            empleadoEntity.setApellido(responseReniec.getApellidoPaterno()+" "+responseReniec.getApellidoMaterno());
            empleadoEntity.setNumDoc(responseReniec.getNumeroDocumento());
            empleadoEntity.setTipoDoc(responseReniec.getTipoDocumento());
            empleadoEntity.setUsuaCrea(Constants.USU_ADMIN);
            empleadoEntity.setDateCrea(new Timestamp(System.currentTimeMillis()));
            return Optional.of(empleadoEntity);
        }catch (HttpClientErrorException e){
            System.err.println("ERROR AL CONSUMIR EL API EXTERNA " +e.getStatusCode());
        }

        return null;
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer " + token);
        return headers;
    }

}
