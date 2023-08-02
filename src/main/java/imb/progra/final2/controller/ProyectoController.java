package imb.progra.final2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imb.progra.final2.entity.Categoria;
import imb.progra.final2.entity.Proyecto;
import imb.progra.final2.service.IProyectoService;
import imb.progra.final2.service.jpa.CategoriaService;

@RestController
@RequestMapping("/final/v1")
public class ProyectoController {

    @Autowired
    IProyectoService service;
    
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/proyecto")
    public ResponseEntity<APIResponse<List<Proyecto>>> buscarTodos() {
        APIResponse<List<Proyecto>> response = new APIResponse<>(HttpStatus.OK.value(), null, service.buscarTodos());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/proyecto/{id}")
    public ResponseEntity<APIResponse<Proyecto>> buscarPorId(@PathVariable("id") Integer id) {
        Proyecto proyecto = service.buscarPorId(id);
        if (proyecto == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró el Proyecto con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.OK.value(), null, proyecto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/proyecto")
    public ResponseEntity<APIResponse<Proyecto>> crearProyecto(@RequestBody Proyecto proyecto, @RequestParam(name = "idCategoria", required = false) Integer idCategoria) {
        if (idCategoria != null) {
            Categoria categoria = categoriaService.buscarPorId(idCategoria);
            if (categoria == null) {
                List<String> messages = new ArrayList<>();
                messages.add("No existe una categoría con el id = " + idCategoria.toString());
                APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            proyecto.setCategoria(categoria);
        }

        if (proyecto.getId() != null) {
            Proyecto buscaProyecto = service.buscarPorId(proyecto.getId());
            if (buscaProyecto != null) {
                List<String> messages = new ArrayList<>();
                messages.add("Ya existe un proyecto con el id = " + proyecto.getId().toString());
                messages.add("Para actualizar utilice el verbo PUT");
                APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        service.crear(proyecto);
        APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.CREATED.value(), null, proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/proyecto")
    public ResponseEntity<APIResponse<Proyecto>> actualizarProyecto(@RequestBody Proyecto proyecto, @RequestParam(name = "idCategoria", required = false) Integer idCategoria) {
        boolean isError;
        String idStr;
        if (proyecto.getId() != null) {
            Proyecto buscaProyecto = service.buscarPorId(proyecto.getId());
            idStr = proyecto.getId().toString();
            if (buscaProyecto != null) {
                if (idCategoria != null) {
                    Categoria categoria = categoriaService.buscarPorId(idCategoria);
                    if (categoria == null) {
                        List<String> messages = new ArrayList<>();
                        messages.add("No existe una categoría con el id = " + idCategoria.toString());
                        APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    proyecto.setCategoria(categoria);
                }

                service.crear(proyecto);
                APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.OK.value(), null, proyecto);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                isError = true;
            }

        } else {
            idStr = "<No definido>";
            isError = true;
        }

        if (isError) {
            List<String> messages = new ArrayList<>();
            messages.add("No existe un proyecto para actualizar con el id = " + idStr);
            messages.add("Para crear un nuevo proyecto utilice el verbo POST");
            APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<String> messages = new ArrayList<>();
        messages.add("El proyecto se ha actualizado correctamente.");
        APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.OK.value(), messages, proyecto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @DeleteMapping("/proyecto/{id}")
    public ResponseEntity<APIResponse<Proyecto>> eliminar(@PathVariable("id") Integer id) {
        Proyecto buscaProyecto = service.buscarPorId(id);
        if (buscaProyecto == null) {
            // Error
            List<String> messages = new ArrayList<>();
            messages.add("No existe un proyecto para eliminar con el id = " + id.toString());
            APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            service.eliminar(id);
            List<String> messages = new ArrayList<>();
            messages.add("El proyecto que figura en el cuerpo ha sido eliminado");
            APIResponse<Proyecto> response = new APIResponse<>(HttpStatus.OK.value(), messages, buscaProyecto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}

