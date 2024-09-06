import java.util.HashMap;

public class LoginInfo {

    private static final HashMap<String,String> loginInfo = new HashMap<>();

    public void addUser(String username, String password){
        loginInfo.put(username,password);
    }

    public String getPassword(String username){
        return loginInfo.get(username);
    }

}
