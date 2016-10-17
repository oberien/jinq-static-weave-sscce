package org.jinq.sscce.jpa;


import org.jinq.sscce.jpa.entities.Customer;
import org.jinq.sscce.jpa.entities.Sale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SampleDbCreator {
    private EntityManager em;

    public SampleDbCreator(EntityManagerFactory factory) {
        this.em = factory.createEntityManager();
    }

    public void initDatabase() {
        em.getTransaction().begin();

        Customer customer = new Customer();
        customer.setName("Customer 1");
        em.persist(customer);
        customer = new Customer();
        customer.setName("Customer 2");
        em.persist(customer);
        customer = new Customer();
        customer.setName("Customer 3");
        em.persist(customer);

        em.flush();

        Sale sale = new Sale();
        sale.setName("Sale 1");
        sale.setCustomer(new Customer(1, null, null));
        em.persist(sale);

        sale = new Sale();
        sale.setName("Sale 2");
        sale.setCustomer(new Customer(1, null, null));
        em.persist(sale);

        sale = new Sale();
        sale.setName("Sale 3");
        sale.setCustomer(new Customer(2, null, null));
        em.persist(sale);

        em.getTransaction().commit();
    }
}
