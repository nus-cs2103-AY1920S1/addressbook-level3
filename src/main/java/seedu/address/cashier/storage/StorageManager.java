package seedu.address.cashier.storage;

import java.util.ArrayList;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.logic.Logic;
import seedu.address.inventory.model.Item;

import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;


/**
 * Manages storage of Inventory List and Transaction List data in local storage.
 */
public class StorageManager implements Storage {

    /*private final File iFile;
    private final File tFile;
    private final seedu.address.person.model.Model personModel;*/

    private Logic inventoryLogic;
    private seedu.address.transaction.logic.Logic transactionLogic;
    //private InventoryList inventoryList;
    //private TransactionList transactionList;

    public StorageManager(Logic inventoryLogic, seedu.address.transaction.logic.Logic transactionLogic)
            throws Exception {
        this.inventoryLogic = inventoryLogic;
        this.transactionLogic = transactionLogic;
    }

    
    /*public StorageManager(File iFile, File tFile, seedu.address.person.model.Model personModel) {
        this.iFile = iFile;
        this.tFile = tFile;
        this.personModel = personModel;
    }*/

    public InventoryList getInventoryList() throws Exception {
        ArrayList<Item> list = inventoryLogic.getInventoryListInArrayList();
        return new InventoryList(list);

        /*seedu.address.inventory.util.InventoryList iList = inventoryLogic.getInventoryList();
        System.out.println("up: " + iList.size());
        System.out.println("inside list 1");
        //seedu.address.inventory.util.InventoryList iList = inventoryLogic.getInventoryList();
        System.out.println(inventoryLogic.getInventoryListInArrayList().size());
        ArrayList<Item> arr = iList.getInventoryListInArrayList();
        this.inventoryList = new InventoryList(arr);
        //InventoryList inventoryList = new InventoryList(list);
        return inventoryList;*/



        /*ArrayList<Item> itemArrayList = new ArrayList<>();
        iFile.getAbsoluteFile().getParentFile().mkdirs();
        iFile.createNewFile();
        BufferedReader bfr = new BufferedReader(new FileReader(iFile));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            Item i = this.readInInventoryFileLine(line);
            itemArrayList.add(i);
        }
        return new InventoryList(itemArrayList);*/
    }


    /*@Override
    public InventoryList getInventoryList(seedu.address.inventory.util.InventoryList inventoryList) {

        ArrayList<Item> list = inventoryList.getInventoryListInArrayList();
        System.out.println("iniside storage: " + list.size());
        this.inventoryList = new InventoryList(list);
        System.out.println("size:   " + inventoryList.size());
        return this.inventoryList;
    } */


    //return inventoryList;
    /**
     * Reads in a single text file line and parses it to create the {@code Item} object.
     * @param line each line of text in the data file
     * @return the item created.
     */
    /*public Item readInInventoryFileLine(String line) {
        String[] stringArr = line.split(" [|] ");
        Item i = null;
        if (stringArr.length == 5) {
            i = new Item(stringArr[1], stringArr[2], Integer.parseInt(stringArr[3]),
                    Double.parseDouble(stringArr[4]), Integer.parseInt(stringArr[0]));
        } else if (stringArr.length == 6) {
            i = new Item(stringArr[1], stringArr[2], Integer.parseInt(stringArr[3]),
                    Double.parseDouble(stringArr[4]), Double.parseDouble(stringArr[5]),
                    Integer.parseInt(stringArr[0]));
        }
        return i;
    }*/

    /**
     * Writes the inventory list to the data file.
     * @param inventoryList the list to be written to file
     * @throws Exception if the input is invalid
     */
    public void writeToInventoryFile(InventoryList inventoryList) throws Exception {
        ArrayList<Item> list = inventoryList.getiArrayList();
        seedu.address.inventory.util.InventoryList inventoryList1 =
                new seedu.address.inventory.util.InventoryList(list);
        System.out.println("inside writing");
        inventoryLogic.resetAndWriteIntoInventoryFile(inventoryList1);
    }

    public TransactionList getTransactionList() throws Exception {
        return transactionLogic.getTransactionList();
    }
    /*ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    tFile.getAbsoluteFile().getParentFile().mkdirs();
    tFile.createNewFile();
    BufferedReader bfr = new BufferedReader(new FileReader(tFile));
    String line = null;
    while ((line = bfr.readLine()) != null) {
        Transaction t = this.readInTransactionFileLine(line, personModel);
        transactionArrayList.add(t);
    }
    return new TransactionList(transactionArrayList);*/

    /*
    public TransactionList getTransactionList(TransactionList list) throws Exception {
        return list;
    }*/


    /**
     * Reads in a single text file line and parses it to create the {@code Transaction} object.
     * @param line each line of text in the data file
     * @param personModel the model used to find person object
     * @return the transaction created.
     */
    /*public Transaction readInTransactionFileLine(String line, seedu.address.person.model.Model personModel) {
        String[] stringArr = line.split(" [|] ", 0);
        String[] dateTimeArr = stringArr[0].split(" ");
        Person person = personModel.getPersonByName(stringArr[4]);
        Transaction t = new Transaction(dateTimeArr[1], stringArr[1],
                stringArr[2], Double.parseDouble(stringArr[3]), person,
                Integer.parseInt(dateTimeArr[0].split("[.]")[0]), isReimbursed(stringArr[5]));
        return t;
    }

    private static boolean isReimbursed(String num) {
        return num.equals("1") ? true : false;
    }*/

    /**
     * Appends the specified transaction to the data file.
     * @param transaction the transaction to be written to file
     * @throws Exception if the input is invalid
     */
    public void appendToTransaction(Transaction transaction) throws Exception {
        transactionLogic.appendToTransactionFile(transaction);
    }
    /*FileWriter fw = new FileWriter(this.tFile, true);
    TransactionList transactionList = getTransactionList();
    String textFileMsg = "";
        if (transactionList.size() == 0) {
        textFileMsg = (transactionList.size() + 1) + ". " + transaction.toWriteIntoFile();
    } else {
        textFileMsg = System.lineSeparator() + (transactionList.size() + 1) + ". "
                + transaction.toWriteIntoFile();
    }
        fw.write(textFileMsg);
        fw.close();
    }*/

}
