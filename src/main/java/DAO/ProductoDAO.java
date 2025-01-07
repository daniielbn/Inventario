package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Producto;
import org.hibernate.Session;

import java.util.List;

public class ProductoDAO extends GenericDAO<Producto> {
    public ProductoDAO() {
        super(Producto.class);
    }

    public List<Producto> getPorRFID(String RFID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT p FROM Producto p WHERE p.keyRFID LIKE :keyRFID", Producto.class)
                    .setParameter("keyRFID", "%" + RFID + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Producto> getPorDescripcion(String descripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT p FROM Producto p WHERE p.descripcion LIKE :descripcion", Producto.class)
                    .setParameter("descripcion", "%" + descripcion + "%")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
