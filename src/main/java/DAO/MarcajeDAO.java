package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Aula;
import modelo.Marcaje;
import modelo.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

public class MarcajeDAO extends GenericDAO<Marcaje> {
    public MarcajeDAO() {
        super(Marcaje.class);
    }

    // Consulta 1
    public List<Marcaje> obtenerMarcajesDeProductoEntreFechas(Producto producto, LocalDate inicio, LocalDate fin) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE idProducto.id = :idProducto AND fecha BETWEEN :fechaInicio AND :fechaFin";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("idProducto", producto.getIdProducto());
            query.setParameter("fechaInicio", Date.valueOf(inicio));
            query.setParameter("fechaFin", Date.valueOf(fin));
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }

    // Consulta 2
    public List<Marcaje> obtenerMarcajesDeAulaEntreFechas(Aula aula, LocalDate inicio, LocalDate fin) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE idAula.id = :idAula AND fecha BETWEEN :fechaInicio AND :fechaFin";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("idAula", aula.getIdAula());
            query.setParameter("fechaInicio", Date.valueOf(inicio));
            query.setParameter("fechaFin", Date.valueOf(fin));
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }

    // Consulta 3
    public List<Marcaje> obtenerMarcajesAulaYProducto(Aula aula, Producto producto) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE idAula.id = :idAula AND idProducto.id = :idProducto";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("idAula", aula.getIdAula());
            query.setParameter("idProducto", producto.getIdProducto());
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }

    public List<Marcaje> obtenerEntreFechas(LocalDate inicio, LocalDate fin) {
        List<Marcaje> marcajes = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Marcaje WHERE fecha BETWEEN :inicio AND :fin";
            Query<Marcaje> query = session.createQuery(hql, Marcaje.class);
            query.setParameter("inicio", Date.valueOf(inicio));
            query.setParameter("fin", Date.valueOf(fin));
            marcajes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marcajes;
    }
}
