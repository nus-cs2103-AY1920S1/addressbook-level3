/*package seedu.address.transaction.util;

import java.util.ArrayList;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;

public class PersonList {
    private ArrayList<Person> pList;

    public PersonList() {
        pList = new ArrayList<>();
    }

    public PersonList(ArrayList<Person> pList) {
        this.pList = pList;
    }

    public Person get(String name) throws NoSuchPersonException {
        Person dummyWithSameName = new DummyNamedPerson(name).getDummy();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).equals(dummyWithSameName)) {
                return pList.get(i);
            }
        }
        throw new NoSuchPersonException();
    }

    public boolean contains(String name) {
        Person dummyWithSameName = new DummyNamedPerson(name).getDummy();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).equals(dummyWithSameName)) {
                return true;
            }
        }
        return false;
    }

    public void add(Person person) {
        pList.add(person);
    }

    public void delete(String name) throws NoSuchPersonException {
        Person dummyWithSameName = new DummyNamedPerson(name).getDummy();
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).equals(dummyWithSameName)) {
                pList.remove(i);
            }
        }
        throw new NoSuchPersonException();
    }

    public int size() {
        return pList.size();
    }
}*/
