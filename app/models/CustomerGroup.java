package models;

import io.ebean.annotation.Cache;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Cache(enableQueryCache = true)
public class CustomerGroup extends AbstractEntity {

    @Column(nullable = false)
    @Constraints.Required
    public String name;

    @Column(nullable = false)
    @Constraints.Required
    @ManyToOne
    public Country country;
}
