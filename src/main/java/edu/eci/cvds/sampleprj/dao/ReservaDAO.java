package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import java.util.List;
import edu.eci.cvds.samples.entities.Reserva;

public interface ReservaDAO{

	public List<Reserva> consultarReservasPendientes(long id) throws PersistenceException;
}