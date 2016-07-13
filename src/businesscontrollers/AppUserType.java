package businesscontrollers;

import businessmodels.UserType;
import java.util.Arrays;
import java.util.List;

public class AppUserType extends UserType{
    //protected List<String> types;
    public AppUserType(){
        super();
        types.addAll(Arrays.asList("LIBRARIAN", "ADMIN", "BOTH"));
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String selectType(int index) {
        return types.get(index);
    }    
}
