import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class User implements Serializable {

    private int userId;
    private String login;
    private String password;
    private TaskLog taskLog;
    private ImageIcon avatar = null;


    public User(String login, String password, TaskLog taskLog) {

        this.userId = defineId();

        this.login = login;

        this.password = password;

        this.taskLog = taskLog;

    }

    public User(String login, String password) {

        this.userId = defineId();

        this.login = login;

        this.password = password;

        this.taskLog = new TaskLog();

    }


    public String getPassword() {
        return password;
    }


    public String getLogin() {
        return login;
    }


    public int getUserId() {
        return userId;
    }


    public ImageIcon getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageIcon avatar) {
        this.avatar = avatar;
    }


    public TaskLog getTaskLog() {
        return taskLog;
    }


    public void setLogin(String newLogin) {
        this.login = newLogin;
        UserManager.saveUsers();
    }


    private int defineId(){

        int userIdCounter = 0;

        try (Scanner scanner = new Scanner(new File("./Assets/IdCount"))) {

            if (scanner.hasNextInt())
                userIdCounter = scanner.nextInt();

             else
                System.err.println("Błąd: plik nie zawiera liczby całkowitej.");


        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("./Assets/IdCount"))) {

            writer.println(userIdCounter+1);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return userIdCounter;

    }

}
