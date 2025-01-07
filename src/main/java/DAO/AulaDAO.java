package DAO;

import com.example.inventario_hib.HibernateUtil;
import modelo.Aula;
import org.hibernate.Session;

import java.util.List;

public class AulaDAO extends GenericDAO<Aula> {
    public AulaDAO() {
        super(Aula.class);
    }

    public List<Aula> getPorNumeracion(String numeracion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Aula a WHERE a.numeracion LIKE :numeracion", Aula.class)
                    .setParameter("numeracion", "%" + numeracion + "%")
                    .getResultList();
        }
    }

    public List<Aula> getPorDescripcion(String descripcion) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM Aula a WHERE a.descripcion LIKE :descripcion", Aula.class)
                    .setParameter("descripcion", "%" + descripcion + "%")
                    .getResultList();
        }
    }
}
