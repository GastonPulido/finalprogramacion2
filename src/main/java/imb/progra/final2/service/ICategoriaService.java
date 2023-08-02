package imb.progra.final2.service;

import java.util.List;

import imb.progra.final2.entity.Categoria;

public interface ICategoriaService {

	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer id);
	void crear(Categoria categoria);
	void eliminar(Integer id);
	Categoria obtenerCategoriaPorId(Integer id);
	
}
