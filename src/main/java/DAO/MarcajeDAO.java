package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Marcaje;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MarcajeDAO extends GenericDAO<Marcaje> {
    public MarcajeDAO() {
        super(Marcaje.class);
    }

    public List<Marcaje> obtenerMarcajesDeProductoEntreFechas(int idProducto, Date fechaInicio, Date fechaFin) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE idProducto.id = :idProducto AND fecha BETWEEN :fechaInicio AND :fechaFin";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("idProducto", idProducto);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("fechaFin", fechaFin);
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }

    public List<Marcaje> getEntreFechas(LocalDate inicio, LocalDate fin) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE fecha BETWEEN :inicio AND :fin";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("inicio", java.sql.Date.valueOf(inicio));
            query.setParameter("fin", java.sql.Date.valueOf(fin));
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }
}
