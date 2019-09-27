package seedu.address.model.loan;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

public class Loan {

    private String id = "13245";

    public Loan (String id) {
        this.id = id;
    }

    public Loan() {

    }

    public String getId() {
        return id;
    }


//    public Address getSerialNumber() {
//        return new Address("sn");
//    }
//
//    public Address getLoanDateTime() {
//        return new Address("ldt");
//    }
//
//    public Address getDueDateTime() {
//        return new Address("ddt");
//    }
//
//    public Address getReturnedDateTime() {
//        return new Address("rdt");
//    }
//
//    public Name getUserName() {
//        return new Name("username");
//    }
}
