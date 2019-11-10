package seedu.address.reimbursement.storage;

import static seedu.address.reimbursement.model.Reimbursement.DATE_TIME_FORMATTER;
import static seedu.address.transaction.storage.StorageManager.ERROR_READING_FILE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadException;

/**
 * Storage manager. Allows reimbursements to be stored and loaded from file.
 */
public class StorageManager implements Storage {
    private static final String VBSPLIT = " [|] ";
    private static final String DOTSPLIT = "[.] ";
    private final File fileReimbursement;

    public StorageManager(File fileReimbursement) {
        this.fileReimbursement = fileReimbursement;
    }

    /**
     * Reads in a line of the file and adds it to the map.
     *
     * @param map  the map to add the new record to.
     * @param line the current line being read.
     */
    private void readInFileLine(HashMap<String, LocalDate> map, String line) {
        String[] stringArr = line.split(VBSPLIT, 0);
        String[] nameArr = stringArr[0].split(DOTSPLIT);
        String personName = nameArr[1];
        LocalDate parsedDate = null;
        if (stringArr.length == 3) {
            String date = stringArr[2];
            parsedDate = LocalDate.parse(date, DATE_TIME_FORMATTER);
        }
        if (!map.containsKey(personName)) {
            map.put(personName, parsedDate);
        }
    }

    /**
     * Generates a hashmap which maps the person's name to the deadline date.
     *
     * @return the hashmap.
     */
    private HashMap<String, LocalDate> readReimbursementFile() throws FileReadException {
        try {
            HashMap<String, LocalDate> map = new HashMap<>();
            fileReimbursement.getAbsoluteFile().getParentFile().mkdirs();
            fileReimbursement.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(fileReimbursement));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                this.readInFileLine(map, line);
            }
            return map;
        } catch (IOException e) {
            //return new HashMap<>();
            throw new FileReadException(ERROR_READING_FILE);
        }
    }

    /**
     * Matches the previously-stored deadline to the corresponding reimbursement.
     *
     * @param newList the reimbursement list upon which matching will be performed.
     * @param map     a mapping of people and deadlines.
     * @throws Exception if an error occurs during matching.
     */
    private void matchDeadline(ReimbursementList newList, HashMap<String, LocalDate> map) {
        for (int i = 0; i < newList.size(); i++) {
            LocalDate date = null;
            Reimbursement rmb = newList.get(i);
            String rbPersonName = rmb.getPerson().getName().toString();
            if (map.containsKey(rbPersonName)) {
                date = map.get(rbPersonName);
            }
            rmb.addDeadline(date);
        }
    }

    @Override
    public ReimbursementList getReimbursementFromFile(TransactionList transList) throws FileReadException {
        HashMap<String, LocalDate> map = readReimbursementFile();
        ReimbursementList newList = new ReimbursementList(transList);
        matchDeadline(newList, map);
        return newList;

    }

    @Override
    public void writeFile(ReimbursementList reimbursementList) throws IOException {
        FileWriter fw = new FileWriter(this.fileReimbursement);
        String textFileMsg = "";
        for (int i = 0; i < reimbursementList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + reimbursementList.get(i).writeIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". "
                        + reimbursementList.get(i).writeIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }


}
