package seedu.address.diaryfeature.diaryModel;

/**
 *
 */
public class DiaryObject {
    String title;
    String date;
    String place;
    String memory;
    String people;

    /**
     *
     * @param t
     * @param d
     * @param pl
     * @param m
     * @param pe
     */
    public DiaryObject(String t, String d, String pl, String pe, String m) {

        title = t;
        date = d;
        place = pl;
        memory = m;
        people = pe;
    }
    @Override
    public String toString() {
        return "Diary Entry: " + title + "\n"
                + "You did this on: " + date + "\n"
                + "You did this at: " + place + "\n"
                + "You did this with: " + people + "\n"
                + "Your favourite memory: " + memory;

    }

}
