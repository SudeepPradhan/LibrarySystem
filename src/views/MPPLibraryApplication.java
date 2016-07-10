package views;

import businesscontrollers.UserManagementController;
import businesscontrollers.UserManagementControllerImpl;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import businessmodels.User;
import exceptions.ExceptionHandler;

public class MPPLibraryApplication extends Application {

    private static Stage loginStage = null;
    private static Stage mainStage = null;
    private static StackPane loginPane, mainPane;
    private static Tab userTab, libMemberTab, bookTab, authorTab, circulationTab;

    @Override
    public void start(Stage primaryStage) {
        try {
            loginPane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            mainPane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            loginStage = primaryStage;
            mainStage = new Stage();
            setupStages();
            readTabs();
            showLoginPane();
        } catch (IOException e) {
            ExceptionHandler.handleUILoaderException(e);
        }
    }

    private static void readTabs() {
        ObservableList<Tab> tabList = getTabList();
        userTab = tabList.get(0);
        libMemberTab = tabList.get(1);
        bookTab = tabList.get(2);
        authorTab = tabList.get(3);
        circulationTab = tabList.get(4);
    }

    private void setupStages() {
        Scene scene = new Scene(loginPane, 450, 400);
        loginStage.setTitle("MPP Library System - Login");
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
        loginStage.centerOnScreen();

        Scene mainScene = new Scene(mainPane, 810, 700);
        mainStage.setTitle("MPP Library System");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.centerOnScreen();
    }

    private static ObservableList<Tab> getTabList() {
        TabPane tabs = (TabPane) (((BorderPane) mainPane.getChildren().get(0)).getChildren().get(1));
        return tabs.getTabs();

    }

    public static void showLoginPane() {
        mainStage.close();
        loginStage.show();
    }

    public static void showMainPane() {
        User loginUser = UserManagementControllerImpl.loginUser;
        if (loginUser == null) {
            showLoginPane();
            return;
        }

        ObservableList<Tab> tabList = getTabList();
        tabList.removeAll(tabList);
        if (loginUser.getUserType() == User.UserType.LIBRARIAN) {
            tabList.add(circulationTab);
        } else {
            tabList.add(userTab);
            tabList.add(libMemberTab);
            tabList.add(bookTab);
            tabList.add(authorTab);
            if (loginUser.getUserType() == User.UserType.BOTH) {
                tabList.add(circulationTab);
            }
        }
        loginStage.close();
        mainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        addDefaultUser();
        launch(args);
    }

    private static void addDefaultUser() {
        UserManagementController controller = new UserManagementControllerImpl();
        List<User> users = controller.getUsers();
        boolean hasAdminUser = false;
        for (User user : users) {
            if (user.getUserType() != User.UserType.LIBRARIAN) {
                hasAdminUser = true;
            }
        }
        if (!hasAdminUser) {
            controller.createUser("superuser", "superuser", User.UserType.BOTH);
        }
    }
}
