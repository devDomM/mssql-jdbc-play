package models;

import io.ebean.annotation.Cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Cache
public class PointBalance extends AbstractEntity {

    @ManyToOne
    public Customer customer;

    public boolean isExpired = false;

    @Column(columnDefinition = "decimal(28,2)")
    public BigDecimal remainingPoints = BigDecimal.ZERO;
}
