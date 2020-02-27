package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CustomerGroupRelation extends AbstractEntity {

    @Column(nullable = false)
    @ManyToOne
    public Customer customer;

    @Column(nullable = false)
    @ManyToOne
    public CustomerGroup customerGroup;

    @Column(nullable = false)
    public String customerNumber;
}
