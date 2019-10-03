package seedu.address.reimbursement;

import java.util.ArrayList;

import seedu.address.model.person.Person;

public class ReimbursementList {
    private ArrayList<ReimbursementRecord> list;

    public ReimbursementList(ArrayList<ReimbursementRecord> list) {
        this.list = list;
    }

    public ReimbursementList() {
        this(new ArrayList<ReimbursementRecord>());
    }

    /**
     * Adds a new reimbursement record into reimbursement list.
     * if the the person in the new record is already in the list, merge the new record with the existing record.
     * otherwise, add the new record directly to the list.
     *
     * @param newRecord new reimbursement list to be added to the list.
     */
    public void addRecord(ReimbursementRecord newRecord) {

    }

    public int getSize() {
        return list.size();
    }

    public double getAmount(Person person) {
        for (ReimbursementRecord item : list) {
            if (person.equals(item.getPerson())) {
                return item.getAmount();
            }
        }
        return 0;
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (ReimbursementRecord item : list) {
            totalAmount += item.getAmount();
        }
        return totalAmount;
    }

    public ArrayList<Person> getPeople() {
        ArrayList<Person> peopleList = new ArrayList<>();
        for (ReimbursementRecord item : list) {
            peopleList.add(item.getPerson());
        }
        return peopleList;
    }

    /**
     * Checks whether the person is already in the list.
     *
     * @param person the person to be checked whether he/she is already in the list.
     * @return true if the person is in the list, otherwise false.
     */
    public boolean checkPerson(Person person) {
        ReimbursementRecord result = getRecord(person);
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public ReimbursementRecord getRecord(Person person) {
        for (ReimbursementRecord record : list) {
            if (record.getPerson().equals(person)) {
                return record;
            }
        }
        return null;
    }


}
