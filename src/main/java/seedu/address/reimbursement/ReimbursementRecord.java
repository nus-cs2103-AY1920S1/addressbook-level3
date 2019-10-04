package seedu.address.reimbursement;

import seedu.address.model.person.Person;

public class ReimbursementRecord {
    private static final String PENDING = "pending";
    private static final String COMPLETED = "completed";
    private TransactionList list;
    private Person person;
    private double amount;
    private String description;
    private String status;

    /**
     * Constructs a ReimbursementRecord object.
     *
     * @param person The person that should be reimbursed.
     * @param amount The amount of money that should be given to the person.
     * @param des    Description for the details of the reimbursement.
     * @param status Status can be pending or completed.
     */
    public ReimbursementRecord(Person person, double amount, String des, String status) {
        this.person = person;
        this.amount = amount;
        this.description = des;
        this.status = status;
        list = new TransactionList();
    }

    public ReimbursementRecord(Transaction trans) {
        Person transPerson = trans.getPerson();
        double transAmount = trans.getAmount();
        String transDes = trans.getDescription();
        String transStatus = trans.getStatus();
        assert transPerson != null : "Person in a transaction must exist.";
        assert transAmount > 0 : "Reimbursement amount must be positive.";
        assert transStatus.equals(PENDING) | transStatus.equals(COMPLETED) : "Status must be either 'pending' or " +
                "'completed'.";
        this(transPerson, transAmount, trans.getDescription(), transDes, transStatus);
    }

    public Person getPerson() {
        return person;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        assert status.equals(PENDING) | status.equals(COMPLETED) : "Status is invalid";
        return status;
    }

    public void updateStatus() {
        status = COMPLETED;
    }

}
