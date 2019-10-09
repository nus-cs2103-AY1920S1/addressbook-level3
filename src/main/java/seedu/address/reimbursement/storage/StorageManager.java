package seedu.address.reimbursement.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.util.TransactionList;

public class StorageManager implements Storage {
    private static final String VBSPLIT = " [|] ";
    private static final String DOTSPLIT = "[.] ";
    private final String filepathReimbursement;
    private final String filepathTransaction;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.transaction.storage.StorageManager transactionStorageManager;

    public StorageManager(String filepathReimbursement, String filepathTransaction,
                          seedu.address.person.model.Model personModel) {
        this.filepathReimbursement = filepathReimbursement;
        this.filepathTransaction = filepathTransaction;
        this.personModel = personModel;
        this.transactionStorageManager = new seedu.address.transaction.storage.StorageManager(filepathTransaction,
                personModel);
    }

    public static void readInFileLine(HashMap<Person, String> map, String line,
                                      seedu.address.person.model.Model personModel) {
        String[] stringArr = line.split(VBSPLIT, 0);
        String[] nameArr = stringArr[0].split(DOTSPLIT);
        String personName = nameArr[1];
        Person person = personModel.getPersonByName(personName);
        String date = stringArr[2];
        boolean exists = false;
        for (Person personKey : map.keySet()) {
            if (personKey.isSamePerson(person)) {
                exists = true;
                break;
            }
        }
        if (exists == false) {
            map.put(person, date);
        }
    }

    public ReimbursementList getReimbursementList() {
        try {
            HashMap<Person, String> map = new HashMap<>();
            File f = new File(filepathReimbursement);
            f.getParentFile().mkdirs();
            f.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                this.readInFileLine(map, line, personModel);
            }
            TransactionList transList = transactionStorageManager.readTransactionList();
            ReimbursementList newList = new ReimbursementList(transList);
            matchDeadline(newList, map);
            this.writeFile(newList);
            return newList;
        } catch (Exception e) {
            return new ReimbursementList();
        }
    }

    private void matchDeadline(ReimbursementList newList, HashMap<Person, String> map) throws Exception{
        for (int i = 0; i < newList.size(); i++) {
            Reimbursement rb = newList.get(i);
            Person rbPerson = rb.getPerson();
            String date = map.get(rbPerson);
            rb.matchDeadline(date);
        }
    }

    public void writeFile(ReimbursementList reimbursementList) throws Exception{
        FileWriter fw = new FileWriter(this.filepathReimbursement);
        String textFileMsg = "";
        for (int i = 0; i < reimbursementList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + reimbursementList.get(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". " +
                        reimbursementList.get(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }
}
