package DAO;

import com.example.inventario_hib.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import modelo.Producto;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;


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


    public List<Producto> getProductosNoEnCategoria(Long idCategoria) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Producto p WHERE p.idProducto NOT IN (SELECT pc.producto.idProducto FROM ProductoCategoria pc WHERE pc.categoria.idCategoria = :idCategoria)", Producto.class)
                    .setParameter("idCategoria", idCategoria).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Consulta 4
    public List<Producto> getProductosSinCategorias() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT p FROM Producto p WHERE p.idProducto NOT IN (SELECT pc.producto.idProducto FROM ProductoCategoria pc)", Producto.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Consulta 5
    public List<Object[]> getNumeroProductoPorCategoria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c.nombre, COUNT(p.idProducto) FROM Producto p JOIN p.categorias c GROUP BY c.nombre", Object[].class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
