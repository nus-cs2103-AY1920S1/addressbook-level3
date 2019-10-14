package seedu.address.reimbursement.model;

import java.util.ArrayList;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.util.Deadline;
import seedu.address.reimbursement.model.util.Description;
import seedu.address.transaction.model.Transaction;

/**
 * Reimbursement class. Stores data of the reimbursement to be made.
 */
public class Reimbursement {
    private static final String VB = " | ";
    private ArrayList<Transaction> list;
    private Person person;
    private double amount;
    private Description description;
    private Deadline deadline;

    private String idCol;
    private String personCol;
    private String descriptionCol;
    private String deadlineCol;

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

    public String getIdCol() {
        return idCol;
    }

    public void setIdCol(int id) {
        this.idCol = Integer.toString(id);
    }

    public String getPersonCol() {
        return personCol;
    }

    public String getDescriptionCol() {
        return descriptionCol;
    }

    public String getDeadlineCol() {
        return deadlineCol;
    }

    public void setPersonCol() {
        this.personCol = person.getName().toString();
    }

    public void setDescriptionCol() {
        this.descriptionCol = description.toString();
    }

    public void setDeadlineCol() {
        this.deadlineCol = deadline.toString();
    }

    /**
     * Calculates the total amount to reimburse based off the transaction list.
     */
    private void calculateAmount() {
        double total = 0;
        for (Transaction trans : list) {
            total += trans.getAmount();
        }
        amount = total;
    }

    /**
     * Merges two reimbursements if they are from the same person.
     *
     * @param reimbursement the reimbursement to be merged.
     */
    public void merge(Reimbursement reimbursement) {
        assert reimbursement.getPerson().isSamePerson(this.getPerson()) : "Merging reimbursements is invalid.";
        for (Transaction trans : reimbursement.getList()) {
            list.add(trans);
        }
        this.calculateAmount();
        description = new Description(list);
    }


    /**
     * Adds a new deadline for the reimbursement.
     *
     * @param date the date by which the reimbursement is due.
     * @throws InvalidDeadlineException If the date is in an incorrect format.
     */
    public void addDeadline(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        deadline = new Deadline(year, month, day);
    }

    /**
     * Used for loading off the file. If no date is specified, create an empty deadline (no deadline).
     *
     * @param date The date of the deadline.
     */
    public void matchDeadline(String date) {
        if(date.length() == 8) {
            this.addDeadline(date);
        }else {
            deadline = new Deadline();
        }
    }


    /**
     * Compares to see if the person for another reimbursement is the same as the current person.
     *
     * @param reimbursement the reimbursement to be compared to.
     * @return true if the person is the same, false otherwise.
     */
    public boolean comparePerson(Reimbursement reimbursement) {
        if (this.person.isSamePerson(reimbursement.getPerson())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Marks a reimbursement as done.
     */
    public void done() {
        for (Transaction trans : list) {
            trans.updateStatus();
        }
    }

    public String toString() {
        return person.getName() + " " + amount + deadline.toString() + System.lineSeparator() + description.toString();
    }

    public String toStringNoDeadline() {
        return person.getName() + " " + amount + System.lineSeparator() + description.toString();
    }

    /**
     * @return a string for use when saving to file.
     */
    public String toWriteIntoFile() {
        String msg = this.person.getName() + VB + this.amount + VB + this.deadline.toString();
        return msg;
    }

}
