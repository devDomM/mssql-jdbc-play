package models;

import io.ebean.annotation.Cache;
import io.ebean.annotation.Formula;
import io.ebean.annotation.UpdatedTimestamp;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Cache
public class Customer extends AbstractEntity {

    @Formats.NonEmpty
    public String email;

    public String name;

    @ManyToOne
    public Country country;

    public String language;

    @OneToMany(cascade = CascadeType.ALL)
    public List<CustomerGroupRelation> customerGroups;


    @Formula(
            select = "coalesce(point_balance_aggregated.point_balance, 0)",
            join = "left join (select customer_id, sum(pb.remaining_points) AS point_balance from point_balance pb where pb.is_expired = 'FALSE' group by customer_id) point_balance_aggregated on point_balance_aggregated.customer_id = ${ta}.id ")
    public BigDecimal pointBalance;

    @Formula(
            select = "coalesce(scan_aggregated.num_scans, 0)",
            join = "left join ( select customer_id, sum(num_scans) as num_scans " +
                    "from (select customer_id, (sum(1)*quantity) AS num_scans " +
                    "from scan_unsubmitted cs group by customer_id,quantity) ncs " +
                    "group by customer_id) scan_aggregated on scan_aggregated.customer_id = ${ta}.id ")
    public int numScans;

    @UpdatedTimestamp
    public Timestamp lastUpdated;
}
