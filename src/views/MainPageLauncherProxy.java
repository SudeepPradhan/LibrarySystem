package views;

import controllers.UserManagementController;
import businesscontrollers.UserManagementControllerImpl;
import businessmodels.User;

public class MainPageLauncherProxy implements Launcher {
    private MainPageLauncher mainPageLauncher;
    private UserManagementController userManagementController;
    private String username;
    private String password;
    private User loginUser;
    
    public MainPageLauncherProxy(){
	userManagementController = new UserManagementControllerImpl();
	this.username = "";
	this.password = "";
	loginUser = null;
    }
    
    public void setUserAccessInfo(String username, String password){
	this.username = username;
	this.password = password;
    }
    
    public boolean isUserAuthenticated(){
	this.loginUser = userManagementController.authenticate(username, password);
	if (loginUser != null){
            return true;
        }else{
            return false;
	}
    }    
    
    @Override
    public void launch() {
        if (isUserAuthenticated()){
            UserManagementControllerImpl.loginUser = loginUser;
            mainPageLauncher = new MainPageLauncher();
            mainPageLauncher.launch();
	}
    }
    
}// end of class MainPageLauncherProxy
