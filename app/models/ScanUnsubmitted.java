package models;

import play.data.validation.Constraints;

import javax.persistence.ManyToOne;

public class ScanUnsubmitted {

    @ManyToOne
    @Constraints.Required
    public Customer customer;

    @Constraints.Required
    public int quantity;

    public String value;
}
