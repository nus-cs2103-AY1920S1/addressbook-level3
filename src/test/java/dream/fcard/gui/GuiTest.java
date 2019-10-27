//@@ author nattanyz
package dream.fcard.gui;

import dream.fcard.model.cards.FrontBackCard;

public class GuiTest {
    public static void Gui_displayFlashCardWithLongText_ShowFScrollBar() {
        String longStringForTesting = "Lorem ipsum dolor sit amet, "
            + "consectetur adipscing elit. Aliquam lacinia, nunc quis dictum consectetur, "
            + "erat nulla molestie turpis, quis finibus justo ipsum a justo. Cras quis ullamcorper "
            + "nulla. Duis nec volutpat nibh. Praesent ut justo vestibulum, lacinia tortor mattis, "
            + "euismod eros. Maecenas viverra erat ac eros consequat feugiat. Praesent vehicula non "
            + "turpis tempor elementum. Suspendisse ac lacus congue, blandit nisl eget, suscipit "
            + "eros. Donec aliquet, tellus eu consectetur ornare, odio ipsum lacinia ipsum, id "
            + "ullamcorper magna nibh quis nulla. Nulla sagittis, quam vel condimentum commodo, "
            + "arcu ligula lobortis erat, non consequat tortor mi non libero. Etiam orci purus, "
            + "maximus sed suscipit ut, consectetur sed arcu.";
        FrontBackCard flashCardForTesting = new FrontBackCard(longStringForTesting,
            "Praesent ut est justo. Cras urna risus, ultricies posuere pharetra in, "
                + "fringilla ac dolor. Donec a porttitor tellus, vitae ullamcorper risus.");
        Gui.renderFront(flashCardForTesting);
    }

    public static void Gui_addNodeToScrollablePane() {

    }

    public static void Gui_replaceNodesInScrollablePane() {

    }
}
