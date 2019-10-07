package seedu.address.reimbursement.model;

import java.util.ArrayList;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.util.Deadline;
import seedu.address.reimbursement.model.util.Description;
import seedu.address.transaction.model.Transaction;

public class Reimbursement {
    private final static String VB = " | ";
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
        deadline = new Deadline();
    }

    public void addDeadline(String date) throws InvalidDeadlineException {
        if (date.length() != 8) {
            throw new InvalidDeadlineException();
        } else {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));
            deadline = new Deadline(year, month, day);
        }
    }

    public void matchDeadline(String date) {
        try {
            this.addDeadline(date);
        } catch (InvalidDeadlineException e) {
            deadline = new Deadline();
        }

    }

    public boolean comparePerson(Reimbursement reimbursement) {
        if (this.person.isSamePerson(reimbursement.getPerson())) {
            return true;
        } else {
            return false;
        }
    }

    public void done() {
        for (Transaction trans : list) {
            trans.updateStatus();
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

    public Deadline getDeadline() {
        return deadline;
    }

    public void merge(Reimbursement reimbursement) {
        assert reimbursement.getPerson().isSamePerson(this.getPerson()) : "Merging reimbursements is invalid.";
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
        return person.getName() + " " + amount + deadline.toString() + System.lineSeparator() + description.toString();
    }

    public String toStringNoDeadline() {
        return person.getName() + " " + amount + System.lineSeparator() + description.toString();
    }

    public String toWriteIntoFile() {
        String msg = this.person.getName() + VB + this.amount + VB + this.deadline.toString();
        return msg;
    }

}
