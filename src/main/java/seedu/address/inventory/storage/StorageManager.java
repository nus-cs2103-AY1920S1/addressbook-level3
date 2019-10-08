package seedu.address.inventory.storage;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;
import seedu.address.transaction.model.exception.NoSuchIndexException;

import java.io.*;
import java.util.ArrayList;

public class StorageManager implements Storage {
    private String filepath;

    public StorageManager(String filepath) {
        this.filepath = filepath;
    }

    public InventoryList getInventoryList() throws Exception {
        ArrayList<Item> inventoryArrayList = new ArrayList<>();
        File f = new File(filepath);
        f.getParentFile().mkdirs();
        f.createNewFile();
        BufferedReader bfr = new BufferedReader(new FileReader(f));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            Item t = this.readInFileLine(line);
            inventoryArrayList.add(t);
        }
        return new InventoryList(inventoryArrayList);
    }

    public static Item readInFileLine(String line) {
        String[] stringArr = line.split(" [|] ", 0);
        String[] dateTimeArr = stringArr[0].split(" ");
        Item t = new Item(stringArr[0], stringArr[1], Integer.parseInt(stringArr[2]), Double.parseDouble(stringArr[3]), price);
        return t;
    }

    public void writeFile(InventoryList inventoryList) throws IOException, NoSuchIndexException, seedu.address.inventory.model.exception.NoSuchIndexException {
        FileWriter fw = new FileWriter(this.filepath);
        String textFileMsg = "";
        for (int i = 0; i < inventoryList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + inventoryList.get(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". " +
                        inventoryList.get(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }

}
