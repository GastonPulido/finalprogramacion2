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

import imb.progra.final2.entity.Proyecto;
import imb.progra.final2.entity.Tarea;
import imb.progra.final2.service.ITareaService;
import imb.progra.final2.service.jpa.ProyectoService;

@RestController
@RequestMapping("/final/v1")
public class TareaController {

	@Autowired
    ITareaService service;

    @Autowired
    ProyectoService proyectoService;
    
    @GetMapping("/tarea")
    public ResponseEntity<APIResponse<List<Tarea>>> buscarTodos() {
        APIResponse<List<Tarea>> response = new APIResponse<>(HttpStatus.OK.value(), null, service.buscarTodas());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/tarea/{id}")
    public ResponseEntity<APIResponse<Tarea>> buscarPorId(@PathVariable("id") Integer id) {
        Tarea tarea = service.buscarPorId(id);
        if (tarea == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró la Tarea con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            APIResponse<Tarea> response = new APIResponse<>(HttpStatus.OK.value(), null, tarea);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/tarea")
    public ResponseEntity<APIResponse<Tarea>> crearTarea(@RequestBody Tarea tarea, @RequestParam(name = "idProyecto", required = false) Integer idProyecto) {
    	if (idProyecto != null) {
            Proyecto proyecto = proyectoService.buscarPorId(idProyecto);
            if (proyecto == null) {
                List<String> messages = new ArrayList<>();
                messages.add("No existe un proyecto con el id = " + idProyecto.toString());
                APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            tarea.setProyecto(proyecto);
        }

        if (tarea.getId() != null) {
            Tarea buscaTarea = service.buscarPorId(tarea.getId());
            if (buscaTarea != null) {
                List<String> messages = new ArrayList<>();
                messages.add("Ya existe una tarea con el id = " + tarea.getId().toString());
                messages.add("Para actualizar utilice el verbo PUT");
                APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        service.crear(tarea);
        APIResponse<Tarea> response = new APIResponse<>(HttpStatus.CREATED.value(), null, tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/tarea")
    public ResponseEntity<APIResponse<Tarea>> actualizarTarea(@RequestBody Tarea tarea, @RequestParam(name = "idProyecto", required = false) Integer idProyecto) {
    	boolean isError;
        String idStr;
        if (tarea.getId() != null) {
            Tarea buscaTarea = service.buscarPorId(tarea.getId());
            idStr = tarea.getId().toString();
            if (buscaTarea != null) {
                if (idProyecto != null) {
                    Proyecto proyecto = proyectoService.buscarPorId(idProyecto);
                    if (proyecto == null) {
                        List<String> messages = new ArrayList<>();
                        messages.add("No existe un proyecto con el id = " + idProyecto.toString());
                        APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                    tarea.setProyecto(proyecto);
                }

                service.crear(tarea);
                APIResponse<Tarea> response = new APIResponse<>(HttpStatus.OK.value(), null, tarea);
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
            messages.add("No existe una tarea para actualizar con el id = " + idStr);
            messages.add("Para crear un nuevo proyecto utilice el verbo POST");
            APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<String> messages = new ArrayList<>();
        messages.add("La tarea se ha actualizado correctamente.");
        APIResponse<Tarea> response = new APIResponse<>(HttpStatus.OK.value(), messages, tarea);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/tarea/{id}")
    public ResponseEntity<APIResponse<Tarea>> eliminar(@PathVariable("id") Integer id) {
        Tarea buscaTarea = service.buscarPorId(id);
        if (buscaTarea == null) {
            // Error
            List<String> messages = new ArrayList<>();
            messages.add("No existe una tarea para eliminar con el id = " + id.toString());
            APIResponse<Tarea> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
        	service.eliminar(id);
            List<String> messages = new ArrayList<>();
            messages.add("La tarea que figura en el cuerpo ha sido eliminada");
            APIResponse<Tarea> response = new APIResponse<>(HttpStatus.OK.value(), messages, buscaTarea);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
