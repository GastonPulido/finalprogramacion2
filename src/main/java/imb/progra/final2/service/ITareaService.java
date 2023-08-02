package imb.progra.final2.service;

import java.util.List;

import imb.progra.final2.entity.Tarea;

public interface ITareaService {
	
	List<Tarea> buscarTodas();
	Tarea buscarPorId(Integer id);
	void crear(Tarea tarea);
	void eliminar(Integer id);

}
