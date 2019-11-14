package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.EventoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.EventoMapper;
import edu.eci.cvds.samples.entities.Evento;

public class MyBATISEventoDAO implements EventoDAO {

    @Inject
    private EventoMapper eventoMapper;
    @Override
    public void cancelarEventosPendientesRecurso(long idRecurso) throws PersistenceException {
        try{
            eventoMapper.cancelarEventosPendientesRecurso(idRecurso);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al cancelar los eventos futuros");
        }
    }

    @Override
    public void registrarEvento(Evento evento, long idReserva) throws PersistenceException {
        try{
            eventoMapper.registrarEvento(evento,idReserva);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al registrar evento");
        }
    }
}
