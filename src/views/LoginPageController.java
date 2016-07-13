package views;

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
import utilities.ImageUtil;

public class LoginPageController implements Initializable {

    MainPageLauncherProxy mainPageLauncherProxy;

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
        
        mainPageLauncherProxy.setUserAccessInfo(username, password);
        if(!mainPageLauncherProxy.isUserAuthenticated()){
            error_label.setText("Invalid login credentials");
            password_textbox.setText("");
        }else{
            error_label.setText("");
            username_textbox.setText("");
            password_textbox.setText("");
        }        
        mainPageLauncherProxy.launch();  

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error_label.setText("");
        mainPageLauncherProxy = new MainPageLauncherProxy();
        login_imageview_banner.setImage(ImageUtil.getBanner());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                username_textbox.requestFocus();
            }
        });
    }

}
