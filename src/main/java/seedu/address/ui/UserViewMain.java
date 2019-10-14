package seedu.address.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import seedu.address.MainApp;
import seedu.address.logic.Logic;

import java.io.IOException;

/**
 * Sets up the controller to dynamically change user views.
 * Does not inherit from UiPart as it defines its own controller.
 *
 */
public class UserViewMain {
   private static final String FXML = "UserViewMain.fxml";
   private UserViewController userViewController;
   private UserViewNavigator userViewNavigator;
   private Logic logic;

   public UserViewMain(Logic logic) throws IOException {
      this.logic = logic;
      FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/" + FXML));
      fxmlLoader.load();
      userViewController = fxmlLoader.getController();
      userViewNavigator = new UserViewNavigator();

      UserViewNavigator.setMainController(userViewController);

   }

   public Pane loadDashboard() {
      userViewNavigator.loadDashboard(logic);
      return userViewController.getCurrentView();
   }

   public Pane loadTasks() {
      userViewNavigator.loadTaskListView(logic);
      return userViewController.getCurrentView();
   }
}
