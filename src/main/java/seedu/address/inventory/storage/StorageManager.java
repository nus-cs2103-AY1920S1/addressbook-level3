package seedu.address.inventory.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;

/**
 * Manages storage of inventory data in local storage.
 */
public class StorageManager implements Storage {
    private File file;

    public StorageManager(File file) {
        this.file = file;
    }

    public InventoryList getInventoryList() throws IOException {
        ArrayList<Item> inventoryArrayList = new ArrayList<>();
        file.getAbsoluteFile().getParentFile().mkdirs();
        file.createNewFile();
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            Item t = this.readInFileLine(line);
            inventoryArrayList.add(t);
        }
        return new InventoryList(inventoryArrayList);
    }

    /**
     * Reads in a single text file line and parses it to create the {@code Item} object.
     * @param line Line of text.
     * @return Item created.
     */
    public static Item readInFileLine(String line) {
        String[] stringArr = line.split(" [|] ", 0);
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
    }

    /**
     * Writes the list stored by the {@code InventoryList} object to File.
     * @param inventoryList the InventoryList being used to store inventory data.
     * @throws IOException if the File does not exist.
     * @throws seedu.address.inventory.model.exception.NoSuchIndexException if the index is beyond the list size.
     */
    public void writeFile(InventoryList inventoryList) throws IOException,
            seedu.address.inventory.model.exception.NoSuchIndexException {
        FileWriter fw = new FileWriter(file);
        String textFileMsg = "";
        for (int i = 0; i < inventoryList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + inventoryList.getItemByIndex(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1)
                        + inventoryList.getItemByIndex(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }
}
