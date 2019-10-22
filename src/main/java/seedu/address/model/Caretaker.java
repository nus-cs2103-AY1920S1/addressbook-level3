package seedu.address.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.student.Student;

import java.util.ArrayList;

public class Caretaker extends AddressBook {

    private ArrayList<Memento> mementos = new ArrayList<>();
    private int statePointer;
    private final AddressBook addressBook;
    //private final ArrayList<InvalidationListener> invalidationListeners;

    public Caretaker(Memento start, AddressBook addressBook) {
        statePointer = 0;
        mementos.add(start);
        this.addressBook = addressBook;
        //invalidationListeners = new ArrayList<>();
    }

    /*
    public ArrayList<InvalidationListener> getInvalidationListenersList() {
        return this.invalidationListenersList;
    }
     */

    public void saveState() {
        mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        //mementos = new ArrayList<>(mementos.subList(0, statePointer + 1));
        System.out.println("DURING SAVING: " + addressBook);
        Memento mementoToAdd = new Memento(new AddressBook(this.addressBook));
        mementos.add(mementoToAdd);
        statePointer++;
        System.out.println("State pointer: " + statePointer);
        mementoToAdd.getState().getStudentList().forEach((Student student) -> {
            System.out.println(student.getName());
        });
        System.out.println("Save complete");
        /*invalidationListeners.add(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("added");
            }
        });*/
    }

    public void undo() {
        ReadOnlyAddressBook currentCopy = mementos.get(statePointer).getState();
        statePointer--;
        ReadOnlyAddressBook previousCopy = mementos.get(statePointer).getState();
        //ArrayList<InvalidationListener> copy = new ArrayList<>(invalidationListeners);
        resetData(previousCopy);
        /*or (InvalidationListener l : copy) {
            l.invalidated(currentCopy.getStudentList());
            l.invalidated(previousCopy.getStudentList());
        }*/
        System.out.println("Undo complete");

        /*for (Student s : mementos.get(statePointer).getState().getStudentList()) {
            System.out.println("AMIEVENHERE");
            System.out.println(s.getName());
        }*/
    }

    public void redo() {
        System.out.println("Before Redo: " + statePointer);
        statePointer++;
        resetData(mementos.get(statePointer).getState());
    }

    public boolean canUndo() {
        return statePointer > 0;
    }

    public boolean canRedo() {
        return statePointer < (mementos.size() - 1);
    }
}
