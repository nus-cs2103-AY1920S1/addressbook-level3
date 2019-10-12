package seedu.address.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.password.Password;

public class PasswordBook{

    private final ObservableList<Password> internalList = FXCollections.observableArrayList();

    public PasswordBook(){

    }

    public PasswordBook(PasswordBook toBeCopied) {
        this();
        internalList.setAll(toBeCopied.getPasswordList());
    }

    public void addPassword(Password p) {
        internalList.add(p);
    }

    public ObservableList<Password> getPasswordList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
}
