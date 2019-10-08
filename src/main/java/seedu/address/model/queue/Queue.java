package seedu.address.model.queue;

import seedu.address.model.person.Person;

import java.util.LinkedList;

public class Queue {
    private LinkedList<Person> listOfPatients;

    public Queue() {
        listOfPatients = new LinkedList<>();
    }

    public Person poll() {
        return listOfPatients.poll();
    }

    public void add(Person person) {
        listOfPatients.add(person);
    }

    public void remove(Person person) {
        listOfPatients.remove(person);
    }

    public boolean contains(Person person) {
        if(listOfPatients.contains(person)) {
            return true;
        } else {
            return false;
        }

    }

    public void remove(int index) {
        listOfPatients.remove(index);
    }

    public int getSize() {
        return listOfPatients.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Queue // instanceof handles nulls
                && listOfPatients.equals(((Queue) other).listOfPatients));
    }
}
