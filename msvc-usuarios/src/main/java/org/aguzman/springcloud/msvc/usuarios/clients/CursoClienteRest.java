package org.aguzman.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@FeignClient(name="msvc-cursos", url = "${msvc.cursos.url}"), ahora solo el nombre del microservicio, confgiruado en el aplicaciont properties
@FeignClient(name="msvc-cursos") //! debe tener el mismo nombre del servicio de kubernetes como del servicio
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);
}
