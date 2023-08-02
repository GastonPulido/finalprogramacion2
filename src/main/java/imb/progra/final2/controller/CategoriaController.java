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
import org.springframework.web.bind.annotation.RestController;

import imb.progra.final2.entity.Categoria;
import imb.progra.final2.service.ICategoriaService;

@RestController
@RequestMapping("/final/v1")
public class CategoriaController {

    @Autowired
    ICategoriaService service;

    @GetMapping("/categoria")
    public ResponseEntity<APIResponse<List<Categoria>>> buscarTodos() {
        APIResponse<List<Categoria>> response = new APIResponse<>(HttpStatus.OK.value(), null, service.buscarTodas());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<APIResponse<Categoria>> buscarPorId(@PathVariable("id") Integer id) {
        Categoria categoria = service.buscarPorId(id);
        if (categoria == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No se encontró la Categoría con el número de id = " + id.toString());
            messages.add("Revise el parámetro");
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.OK.value(), null, categoria);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<APIResponse<Categoria>> crearCategoria(@RequestBody Categoria categoria) {
        if (categoria.getId() != null) {
            Categoria buscaCategoria = service.buscarPorId(categoria.getId());
            if (buscaCategoria != null) {
                List<String> messages = new ArrayList<>();
                messages.add("Ya existe una categoría con el id = " + categoria.getId().toString());
                messages.add("Para actualizar utilice el verbo PUT");
                APIResponse<Categoria> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        service.crear(categoria);
        APIResponse<Categoria> response = new APIResponse<>(HttpStatus.CREATED.value(), null, categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/categoria")
    public ResponseEntity<APIResponse<Categoria>> actualizarCategoria(@RequestBody Categoria categoria) {
        boolean isError;
        String idStr;
        if (categoria.getId() != null) {
            Categoria buscaCategoria = service.buscarPorId(categoria.getId());
            idStr = categoria.getId().toString();
            if (buscaCategoria != null) {
                isError = false;
            } else {
                isError = true;
            }

        } else {
            idStr = "<No definido>";
            isError = true;
        }

        if (isError) {
            List<String> messages = new ArrayList<>();
            messages.add("No existe una categoría para actualizar con el id = " + idStr);
            messages.add("Para crear una nueva categoría utilice el verbo POST");
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            service.crear(categoria);
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.OK.value(), null, categoria);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }

    @DeleteMapping("/categoria/{id}")
    public ResponseEntity<APIResponse<Categoria>> eliminar(@PathVariable("id") Integer id) {
        Categoria buscaCategoria = service.buscarPorId(id);
        if (buscaCategoria == null) {
            List<String> messages = new ArrayList<>();
            messages.add("No existe una categoría para eliminar con el id = " + id.toString());
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), messages, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            service.eliminar(id);
            List<String> messages = new ArrayList<>();
            messages.add("La categoría que figura en el cuerpo ha sido eliminada");
            APIResponse<Categoria> response = new APIResponse<>(HttpStatus.OK.value(), messages, buscaCategoria);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}

