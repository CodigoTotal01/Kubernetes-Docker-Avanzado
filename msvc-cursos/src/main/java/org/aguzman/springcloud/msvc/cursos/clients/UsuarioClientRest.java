package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Solo el nombre del servicio
@FeignClient(name="msvc-usuarios")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);




    @GetMapping("/usuario-por-curso") // header es cabezera
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids, @RequestHeader(value = "Authorization", required = true) String token);
}
