package seedu.address.transaction.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.util.TransactionList;

public class StorageManager implements Storage {
    private final String filepath;
    private final seedu.address.person.model.Model personModel;

    public StorageManager(String filepath, seedu.address.person.model.Model personModel) {
        this.filepath = filepath;
        this.personModel = personModel;
    }

    public TransactionList getTransactionList() {
        try {
            ArrayList<Transaction> transactionArrayList = new ArrayList<>();
            File f = new File(filepath);
            f.getParentFile().mkdirs();
            f.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                Transaction t = this.readInFileLine(line, personModel);
                transactionArrayList.add(t);
            }
            return new TransactionList(transactionArrayList);
        } catch (IOException e) {
            return new TransactionList();
        }
    }

    public static Transaction readInFileLine(String line, seedu.address.person.model.Model personModel) {
        String[] stringArr = line.split(" [|] ", 0);
        String[] dateTimeArr = stringArr[0].split(" ");
        Person person = personModel.getPersonByName(stringArr[4]);
        Transaction t = new Transaction(dateTimeArr[1], stringArr[1],
                stringArr[2], Double.parseDouble(stringArr[3]), person,
                Integer.parseInt(dateTimeArr[0].split("[.]")[0]));
        return t;
    }

    public void writeFile(TransactionList transactionList) throws IOException, NoSuchIndexException {
        FileWriter fw = new FileWriter(this.filepath);
        String textFileMsg = "";
        for (int i = 0; i < transactionList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + transactionList.get(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". " +
                        transactionList.get(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }

}
