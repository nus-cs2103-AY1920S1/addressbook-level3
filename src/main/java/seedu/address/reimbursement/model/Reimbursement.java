package seedu.address.reimbursement.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.util.Description;
import seedu.address.transaction.model.Transaction;

/**
 * Reimbursement class. Stores data of the reimbursement to be made.
 */
public class Reimbursement {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final String VB = " | ";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private ArrayList<Transaction> list;
    private Person person;
    private double amount;
    private Description description;
    private LocalDate deadline;
    //Store attributes to be displayed in UI
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
        logger.info("reimbursement only trans: " + trans);
        list = new ArrayList<>();
        list.add(trans);
        logger.info("first in list in reimbursement:" + list.get(0));
        amount = trans.getAmount();
        person = trans.getPerson();
        description = new Description(list);
        deadline = null;
    }

    //Get attributes from reimbursement
    public Person getPerson() {
        return person;
    }

    public ArrayList<Transaction> getList() {
        return list;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDeadline() {
        return deadline;
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
     * adds deadline date to reimbursement.
     *
     * @param date
     */
    public void addDeadline(LocalDate date) {
        this.deadline = date;
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
            logger.info("trans updated: " + trans);
        }
    }

    //For UI display
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
        if (deadline == null) {
            this.deadlineCol = "";
        } else {
            this.deadlineCol = deadline.format(DATE_TIME_FORMATTER);
        }
    }

    //Convert reimbursement to string for saving and display

    /**
     * Converts a reimbursement to string.
     *
     * @return string
     */
    public String toString() {
        return person.getName() + " $" + amount + deadline.format(DATE_TIME_FORMATTER) + System.lineSeparator()
                + description.toString();
    }

    /**
     * Converts a reimbursement to string but without deadline date
     *
     * @return string the reimbursement is converted to.
     */
    public String toStringNoDeadline() {
        return person.getName() + " $" + amount + System.lineSeparator() + description.toString();
    }

    /**
     * @return a string for use when saving to file.
     */
    public String writeIntoFile() {
        String msg = "";
        if (deadline == null) {
            msg = this.person.getName() + VB + this.amount + VB + "";
        } else {
            msg = this.person.getName() + VB + this.amount + VB + this.deadline.format(DATE_TIME_FORMATTER);
        }
        return msg;
    }

}
