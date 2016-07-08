package utilities;

public class ExceptionHandler {

    public static void handleDatabaseIOException(Exception e){
       System.out.println("DBIOException Handled: " + e.getMessage()); 
    }
    
    public static void handleUILoaderException(Exception e){
       System.out.println("UILoaderException Handled: " + e.getMessage()); 
    }
}
