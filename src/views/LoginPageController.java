package views;

import businesscontrollers.UserManagementController;
import businesscontrollers.UserManagementControllerImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import models.business.User;
import utilities.ImageUtil;

public class LoginPageController implements Initializable {

    UserManagementController userManagementController;

    @FXML
    private PasswordField password_textbox;

    @FXML
    private TextField username_textbox;

    @FXML
    private Label error_label;

    @FXML
    private ImageView login_imageview_banner;

    @FXML
    void loginButtonClicked(ActionEvent event) {
        String username = username_textbox.getText();
        String password = password_textbox.getText();
        User loginUser = userManagementController.authenticate(username, password);
        if (loginUser == null) {
            error_label.setText("Invalid login credentials");
            password_textbox.setText("");
        } else {
            error_label.setText("");
            username_textbox.setText("");
            password_textbox.setText("");
            UserManagementControllerImpl.loginUser = loginUser;
            MPPLibraryApplication.showMainPane();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error_label.setText("");
        userManagementController = new UserManagementControllerImpl();
        login_imageview_banner.setImage(ImageUtil.getBanner());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username_textbox.requestFocus();
            }
        });
    }

}
