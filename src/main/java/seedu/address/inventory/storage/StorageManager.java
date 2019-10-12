package seedu.address.inventory.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;

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
        String[] stringArr = line.split(" [|] ");
        Item i = null;
        if (stringArr.length == 5) {
            i = new Item(stringArr[1], stringArr[2], Integer.parseInt(stringArr[3]),
                    Double.parseDouble(stringArr[4]), 0, Integer.parseInt(stringArr[0]));
        } else if (stringArr.length == 6) {
            i = new Item(stringArr[1], stringArr[2], Integer.parseInt(stringArr[3]),
                    Double.parseDouble(stringArr[4]), Double.parseDouble(stringArr[5]),
                    Integer.parseInt(stringArr[0]));
        }
        return i;
    }

    public void writeFile(InventoryList inventoryList) throws IOException, seedu.address.inventory.model.exception.NoSuchIndexException {
        FileWriter fw = new FileWriter(this.filepath);
        String textFileMsg = "";
        for (int i = 0; i < inventoryList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + inventoryList.getItemByIndex(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) +
                        inventoryList.getItemByIndex(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }
}
