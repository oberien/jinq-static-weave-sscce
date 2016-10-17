package org.jinq.sscce;

import org.jinq.sscce.jpa.SampleDbCreator;
import org.jinq.sscce.jpa.entities.Customer;
import org.jinq.sscce.jpa.entities.Sale;
import org.jinq.jpa.JPAQueryLogger;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.jinq.orm.stream.JinqStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;


public class Main {
    private static EntityManagerFactory entityManagerFactory;
    private static JinqJPAStreamProvider streams;

    private static EntityManager em;

    public static void main(String[] args) throws Exception {
        // Configure Jinq for the given JPA database connection
        entityManagerFactory = Persistence.createEntityManagerFactory("sscce");
        SampleDbCreator dbCreator = new SampleDbCreator(entityManagerFactory);
        dbCreator.initDatabase();

        // init Jinq
        streams = new JinqJPAStreamProvider(entityManagerFactory);

        // Configure Jinq to output the queries it executes
        streams.setHint("queryLogger", new JPAQueryLogger() {
            @Override
            public void logQuery(String query, Map<Integer, Object> positionParameters,
                                 Map<String, Object> namedParameters) {
                System.out.println("  " + query);
            }
        });

        em = entityManagerFactory.createEntityManager();
        streams.streamAll(em, Customer.class)
            .join(b -> JinqStream.from(b.getSales()))
            .forEach(System.out::println);

        streams.streamAll(em, Sale.class)
            .joinFetch(s -> JinqStream.of(s.getCustomer()))
            .forEach(System.out::println);
    }
}
