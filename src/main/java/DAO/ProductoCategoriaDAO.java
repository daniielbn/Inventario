package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Categoria;
import modelo.Producto;
import modelo.ProductoCategoria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductoCategoriaDAO extends GenericDAO<ProductoCategoria>{
    public ProductoCategoriaDAO() {
        super(ProductoCategoria.class);
    }

    @Override
    public boolean create(ProductoCategoria productoCategoria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Producto producto = productoCategoria.getProducto();
            Categoria categoria = productoCategoria.getCategoria();

            Hibernate.initialize(producto.getCategorias());
            Hibernate.initialize(categoria.getProductos());

            // Actualizamos la lista de categorías del producto
            if (producto.getCategorias() == null) {
                producto.setCategorias(new ArrayList<>());
            }

            producto.getCategorias().add(categoria);

            session.persist(productoCategoria);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ProductoCategoria getPorIdProductoYIdCategoria(Long idProducto, Long idCategoria) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT pc FROM ProductoCategoria pc WHERE pc.producto.idProducto = :producto AND pc.categoria.idCategoria = :categoria", ProductoCategoria.class)
                    .setParameter("producto", idProducto)
                    .setParameter("categoria", idCategoria)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<ProductoCategoria> getPorIdProducto(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT pc FROM ProductoCategoria pc WHERE pc.producto.idProducto = :producto", ProductoCategoria.class)
                    .setParameter("producto", id)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductoCategoria> getPorIdCategoria(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT pc FROM ProductoCategoria pc WHERE pc.categoria.idCategoria = :categoria", ProductoCategoria.class)
                    .setParameter("categoria", id)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
