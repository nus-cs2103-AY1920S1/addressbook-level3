package seedu.address.diaryfeature.diarymain;

import java.awt.*;

/**
 *
 */
public class DiaryObject {
    String entry;

    /**
     *
     * @param y
     */
    public DiaryObject(String y) {
        entry = y;
    }
    @Override
    public String toString() {
        return this.entry;
    }

}
