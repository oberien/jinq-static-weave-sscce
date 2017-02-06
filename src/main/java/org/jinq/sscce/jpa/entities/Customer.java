package org.jinq.sscce.jpa.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID")
    private List<Sale> sales;

    public Customer() {
    }

    public Customer(long id, String name, List<Sale> sales) {
        this.id = id;
        this.name = name;
        this.sales = sales;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
