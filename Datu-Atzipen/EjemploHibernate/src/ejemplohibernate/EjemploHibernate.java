package ejemplohibernate;

import ejemplohibernate.util.HibernateUtil;
import modelo.Helbidea;
import modelo.Ikaslea;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EjemploHibernate {
    public static void main(String[] args) {
        // INSERT de prueba
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Ikaslea ik = new Ikaslea("12345678A", "Ane", "Etxeberria", 21);
            ik.addHelbidea(new Helbidea("Kalea 1, Donostia"));
            ik.addHelbidea(new Helbidea("Kalea 2, Bilbo"));

            session.persist(ik); // cascade persiste también las direcciones
            tx.commit();
            System.out.println("Guardado ikaslea: " + ik.getNan());
        }

        // SELECT con las direcciones
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Ikaslea> lista = session
                .createQuery("select distinct i from Ikaslea i left join fetch i.helbideak", Ikaslea.class)
                .list();

            for (Ikaslea i : lista) {
                System.out.println(i.getNan() + " - " + i.getIzena() + " " + i.getAbizena());
                i.getHelbideak().forEach(h -> System.out.println("   * " + h.getHelbidea()));
            }
        }

        HibernateUtil.shutdown();
    }
}
