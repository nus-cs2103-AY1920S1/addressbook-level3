package seedu.address.model;

import seedu.address.model.item.VisualizeList;
import seedu.address.storage.Storage;

import java.io.*;

public class ElisaStateManager implements ElisaState, Serializable {
    private Storage storage;
    private VisualizeList visualizeList;

    public ElisaStateManager(Storage storage, VisualizeList visualizeList){
        this.storage = storage;
        this.visualizeList = visualizeList;
    }

    @Override
    public ElisaState deepCopy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        bos.close();
        byte[] byteData = bos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        Object object = new Object();
        object = (Object) new ObjectInputStream(bais).readObject();
        return (ElisaState) object;
    }
}
