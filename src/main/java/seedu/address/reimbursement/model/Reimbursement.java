package seedu.address.reimbursement.model;

import java.util.ArrayList;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.util.Deadline;
import seedu.address.reimbursement.model.util.Description;
import seedu.address.transaction.model.Transaction;

public class Reimbursement {
    private ArrayList<Transaction> list;
    private Person person;
    private double amount;
    private Description description;
    private Deadline deadline;

    /**
     * Constructs a reimbursement based on 1 piece of transaction.
     *
     * @param trans Transaction that I want to create reimbursement for.
     */
    public Reimbursement(Transaction trans) {
        list = new ArrayList<>();
        list.add(trans);
        amount = trans.getAmount();
        person = trans.getPerson();
        description = new Description(list);
        deadline = null;
    }

    public void addDeadline(String date) throws InvalidDeadlineException {
        if(date.length() != 8) {
            throw new InvalidDeadlineException();
        }else {
            int year = Integer.parseInt(date.substring(0,4));
            int month = Integer.parseInt(date.substring(4,6));
            int day = Integer.parseInt(date.substring(6,8));
            deadline = new Deadline(year,month,day);
        }
    }

    public boolean comparePerson(Reimbursement reimbursement) {
        if (this.person.equals(reimbursement.getPerson())) {
            return true;
        } else {
            return false;
        }
    }

    public Person getPerson() {
        return person;
    }

    public ArrayList<Transaction> getList() {
        return list;
    }

    public double getAmount() {
        return amount;
    }

    public void merge(Reimbursement reimbursement) {
        assert reimbursement.getPerson().equals(this.getPerson()) : "Merging reimbursements is invalid.";
        for (Transaction trans : reimbursement.getList()) {
            list.add(trans);
        }
        this.calculateAmount();
        description = new Description(list);
    }

    private void calculateAmount() {
        double total = 0;
        for (Transaction trans : list) {
            total += trans.getAmount();
        }
        amount = total;
    }

    public String toString() {
        return person.getName() + " " + amount + deadline.toString() + "\n" + description.toString();
    }

}
