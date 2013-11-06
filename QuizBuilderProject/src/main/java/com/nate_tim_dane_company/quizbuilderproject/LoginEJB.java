import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tim
 * Date: 11/5/13
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateful
public class LoginEJB {
    @PersistenceContext(unitName=/*whatever the name is*/)

    private EntityManagerFactory emf = new EntityManagerFactory();
    private EntityManager em = emf.EntityManager();

    public List<User> verifyUser(String username, String password){
        Query query = em.createQuery("SELECT u FROM [User] u WHERE u.username = '%"+username+"%' AND u.password = '%"+password"%'");

        return query.getResultList();
    }
}
