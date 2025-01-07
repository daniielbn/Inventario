package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Categoria;
import org.hibernate.Session;

import java.util.List;

public class CategoriaDAO extends GenericDAO<Categoria> {
    public CategoriaDAO() {
        super(Categoria.class);
    }

    public List<Categoria> getPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Categoria c WHERE c.nombre LIKE :nombre", Categoria.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        }
    }

    public List<Categoria> getPorDescripcion(String descripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Categoria c WHERE c.descripcion LIKE :descripcion", Categoria.class)
                    .setParameter("descripcion", "%" + descripcion + "%")
                    .getResultList();
        }
    }
}
