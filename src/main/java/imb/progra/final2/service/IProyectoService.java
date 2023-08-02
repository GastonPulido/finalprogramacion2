package imb.progra.final2.service;

import java.util.List;

import imb.progra.final2.entity.Proyecto;

public interface IProyectoService {
	
	List<Proyecto> buscarTodos();
	Proyecto buscarPorId(Integer id);
	void crear(Proyecto proyecto);
	void eliminar(Integer id);

}
