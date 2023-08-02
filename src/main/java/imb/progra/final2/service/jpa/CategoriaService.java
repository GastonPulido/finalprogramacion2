package imb.progra.final2.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imb.progra.final2.entity.Categoria;
import imb.progra.final2.repository.CategoriaRepository;
import imb.progra.final2.service.ICategoriaService;

@Service
public class CategoriaService implements ICategoriaService {

	@Autowired
	CategoriaRepository repository;
	
	@Override
	public List<Categoria> buscarTodas() {
		return repository.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	@Override
	public void crear(Categoria categoria) {
		repository.save(categoria);
	}

	@Override
	public void eliminar(Integer id) {
		repository.deleteById(id);
	}
	
	@Override
    public Categoria obtenerCategoriaPorId(Integer id) {
        Optional<Categoria> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

}
