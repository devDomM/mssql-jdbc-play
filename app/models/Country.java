package models;

import io.ebean.annotation.Cache;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cache(enableQueryCache = true, readOnly = true)
public class Country extends AbstractEntity {

    @Column(nullable = false, unique = true)
    @Constraints.Required
    public String isoCode;
}
