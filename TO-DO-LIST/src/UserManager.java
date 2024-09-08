import java.io.*;
import java.util.HashMap;

public class UserManager {

    private static final String filePath = "./Assets/UserData";
    private static HashMap<Integer, User> usersLog = new HashMap<>();


    public static void loadUsers() {

        try ( ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath)) ) {

            usersLog = (HashMap<Integer, User>) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No user data found.");
        } catch (EOFException e){
            System.out.println("Empty UserData file.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void saveUsers() {

        try ( ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath)) ) {

            out.writeObject(usersLog);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void addUser(User user) {

        loadUsers();

        if (!usersLog.containsKey(user.getUserId())) {

            usersLog.put(user.getUserId(), user);

            saveUsers();

        }

    }


    public static User getUser(String username) throws NoUserFoundException {

        loadUsers();

        if (usersLog.isEmpty())
            throw new NoUserFoundException("No user found with username: " + username + " -log is empty");


        User wantedUser = null;


        for (User user : usersLog.values()) {

            if (user.getLogin().equals(username))
                wantedUser = user;

        }

        if (wantedUser != null) {
            return wantedUser;
        } else
            throw new NoUserFoundException("No user found with username: " + username);

    }


    public static boolean doesUserExist(String username) throws NoUserFoundException {

        loadUsers();

        if (usersLog.isEmpty())
            throw new NoUserFoundException("No user found with username: " + username + " -log is empty");


        for (User user : usersLog.values()) {

            if (user.getLogin().equals(username))
                return true;

        }

        return false;

    }


}
