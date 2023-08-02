package imb.progra.final2.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.progra.final2.entity.Tarea;
import imb.progra.final2.repository.TareaRepository;
import imb.progra.final2.service.ITareaService;

@Service
public class TareaService implements ITareaService {

    @Autowired
    TareaRepository repository;

    @Override
    public List<Tarea> buscarTodas() {
        return repository.findAll();
    }

    @Override
	public Tarea buscarPorId(Integer id) {
		Optional<Tarea> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

    @Override
    public void crear(Tarea tarea) {
        repository.save(tarea);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
