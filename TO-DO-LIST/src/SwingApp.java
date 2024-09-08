import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SwingApp extends JFrame {

    private static SwingApp swingAppInstance = null;

    protected static User currentUser = null;

    protected static String username = "Guest";

    protected static String userPassword = null;



    private SwingApp() {

        SwingUtilities.invokeLater(this::runSwingApp);

    }


    public static SwingApp getInstance() {

        if (swingAppInstance == null)
            swingAppInstance = new SwingApp();

        return swingAppInstance;

    }


    private void runSwingApp() {

        setProfile();

        this.setTitle("WhatToDo");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new Dimension(1000,800));

        this.setResizable(false);


        SplitPane splitPane = new SplitPane();

        if(SwingApp.currentUser.getAvatar() != null){
            Menu.updateProfileAvatar(SwingApp.currentUser.getAvatar());
        }

        this.setContentPane(splitPane);


        this.pack();

        this.setLocationRelativeTo( null );

        this.setVisible( true );

        ContentPanelState.setCurrentState(ContentPanelState.ALL_TASKS);

    }

    protected void setProfile() {

        JTextField loginField = new JTextField();

        JPasswordField passwordField = new JPasswordField();

        JCheckBox showPasswordCheckBox = new JCheckBox("Show password");

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });


        Object[] message = {
                "Username:", loginField,
                "Password:", passwordField,
                showPasswordCheckBox
        };


        int option = JOptionPane.showConfirmDialog(null, message, "Enter your login and password", JOptionPane.OK_CANCEL_OPTION);


        if (option == JOptionPane.OK_OPTION) {

            Scanner scannerLogin = new Scanner( loginField.getText() );

            Scanner scannerPassword = new Scanner( new String( passwordField.getPassword() ) );

            boolean error = false;


            if (!scannerLogin.hasNext() || !scannerPassword.hasNext()) {
                error = true;
            }


            if (error) {

                JOptionPane.showMessageDialog(null, "<html><font color=red>Invalid login or password.</font></html>", "LoginError", JOptionPane.ERROR_MESSAGE);

                setProfile();

            } else {

                username = scannerLogin.next();

                userPassword = scannerPassword.next();


                try {

                    if (UserManager.doesUserExist(username) && UserManager.getUser(username).getPassword().equals(userPassword)) {
                        currentUser = UserManager.getUser(username);
                    }
                    else if (UserManager.doesUserExist(username) && !UserManager.getUser(username).getPassword().equals(userPassword)) {
                        JOptionPane.showMessageDialog(null, "<html><font color=red>Invalid login or password.</font></html>", "LoginTakenError", JOptionPane.ERROR_MESSAGE);
                        setProfile();
                    }
                    else {
                        currentUser = UserManager.getUser(username);
                    }

                } catch (NoUserFoundException e) {

                    System.out.println(e.getMessage());

                    currentUser = new User(username, userPassword);

                    UserManager.addUser(currentUser);

                }

                if(currentUser.getTaskLog() != null)
                    currentUser.getTaskLog().setTasksProperties();

                ContentPanel.getInstance().refreshTasks();
            }

        }

    }


}
