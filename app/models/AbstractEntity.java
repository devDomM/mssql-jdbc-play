package models;

import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class AbstractEntity extends Model {

    @Id
    public Long id;

    @CreatedTimestamp
    public Timestamp created;
}
