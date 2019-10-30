package seedu.jarvis.model.viewstatus;

import java.util.List;

/**
 * The available view types in the Jarvis application.
 */
public enum ViewType {
    HOME_PAGE,
    LIST_CCA,
    LIST_FINANCE,
    LIST_ADDRESS,
    LIST_COURSE;



    public static ViewType getNextViewType(ViewType curr) {
        List<ViewType> list = List.of(LIST_CCA, LIST_COURSE, LIST_FINANCE);
        int index = list.indexOf(curr);
        index = (index == list.size() - 1)
            ? 0
            : index + 1;
        return list.get(index);
    }
}
