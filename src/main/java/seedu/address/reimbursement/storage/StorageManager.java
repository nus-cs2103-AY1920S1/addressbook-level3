package seedu.address.reimbursement.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.util.TransactionList;

/**
 * Storage manager. Allows reimbursements to be stored and loaded from file.
 */
public class StorageManager implements Storage {
    private static final String VBSPLIT = " [|] ";
    private static final String DOTSPLIT = "[.] ";
    private final String filepathReimbursement;
    private final seedu.address.transaction.storage.StorageManager transactionStorageManager;

    public StorageManager(String filepathReimbursement,
                          seedu.address.transaction.storage.StorageManager transactionStorageManager) {
        this.filepathReimbursement = filepathReimbursement;
        this.transactionStorageManager = transactionStorageManager;
    }

    public static void readInFileLine(HashMap<String, String> map, String line) {
        String[] stringArr = line.split(VBSPLIT, 0);
        String[] nameArr = stringArr[0].split(DOTSPLIT);
        String personName = nameArr[1];
        String parsedDate = "";
        if(stringArr.length == 3) {
            String date = stringArr[2];
            //remove dash in deadline date
            parsedDate = date.substring(0, 4) + date.substring(5, 7) + date.substring(8);
        }
        if (!map.containsKey(personName)) {
            map.put(personName, parsedDate);
        }
    }

    @Override
    public ReimbursementList readReimbursementList() {
        try {
            //this map maps person's name with the reimbursement deadline.
            HashMap<String, String> map = new HashMap<>();
            File f = new File(filepathReimbursement);
            f.getParentFile().mkdirs();
            f.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                this.readInFileLine(map, line);
            }
            //read transaction list from transaction storage
            TransactionList transList = transactionStorageManager.readTransactionList();
            //generate reimbursement list from transaction list
            ReimbursementList newList = new ReimbursementList(transList);
            this.matchDeadline(newList, map);
            return newList;
        } catch (IOException e) {
            return new ReimbursementList();
        }
    }

    @Override
    public void writeFile(ReimbursementList reimbursementList) throws IOException {
        FileWriter fw = new FileWriter(this.filepathReimbursement);
        String textFileMsg = "";
        for (int i = 0; i < reimbursementList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + reimbursementList.get(i).WriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". "
                        + reimbursementList.get(i).WriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }

    /**
     * Matches the previously-stored deadline to the corresponding reimbursement.
     *
     * @param newList the reimbursement list upon which matching will be performed.
     * @param map     a mapping of people and deadlines.
     * @throws Exception if an error occurs during matching.
     */
    private void matchDeadline(ReimbursementList newList, HashMap<String, String> map) {
        for (int i = 0; i < newList.size(); i++) {
            String date = "";
            Reimbursement rmb = newList.get(i);
            String rbPersonName = rmb.getPerson().getName().toString();
            if(map.containsKey(rbPersonName)) {
                date = map.get(rbPersonName);
            }
            rmb.matchDeadline(date);
        }
    }


}
