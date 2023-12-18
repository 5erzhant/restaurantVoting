package ru.project.repository.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.project.model.User;
import ru.project.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        }
        return get(user.id()) == null ? null : em.merge(user);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }
}
