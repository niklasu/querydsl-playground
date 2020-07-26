package niklasu.querydslplayground;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private EntityManagerFactory emf;


    @Override
    public void run(String... args) throws Exception {
        saveFred();

        QUser user = QUser.user;

        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        User c = queryFactory.selectFrom(user)
                .where(user.login.eq("bernd"))
                .fetchOne();

        System.out.println("Number of users: " + queryFactory.selectFrom(user).fetchCount());
        List<User> usersWithBlogPosts = queryFactory.selectFrom(user).leftJoin(user.blogPosts).fetchJoin().fetch();
        System.out.println("Users with blogposts" + usersWithBlogPosts);

    }

    private void saveFred() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        User user = new User();
        user.setLogin("fred");
        em.persist(user);

        User user2 = new User();
        user2.setLogin("bernd");
        BlogPost blogPost = new BlogPost();
        blogPost.setBody("this is the blogpost yo!");
        blogPost.setUser(user2);
        user2.getBlogPosts().add(blogPost);
        em.persist(blogPost);
        em.persist(user2);
        tx.commit();
        em.close();

    }
}