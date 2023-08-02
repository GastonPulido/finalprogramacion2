package imb.progra.final2.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.progra.final2.entity.Proyecto;
import imb.progra.final2.repository.ProyectoRepository;
import imb.progra.final2.service.IProyectoService;

@Service
public class ProyectoService implements IProyectoService {

    @Autowired
    ProyectoRepository repository;

    @Override
    public List<Proyecto> buscarTodos() {
        return repository.findAll();
    }

    @Override
	public Proyecto buscarPorId(Integer id) {
		Optional<Proyecto> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

    @Override
    public void crear(Proyecto proyecto) {
        repository.save(proyecto);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
