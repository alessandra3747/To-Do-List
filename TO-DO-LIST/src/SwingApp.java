import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class SwingApp extends JFrame {

    private static SwingApp swingAppInstance = null;

    protected static String username = "Guest";

    protected static TaskLog taskLog = TaskLog.getInstance();

    private SwingApp() {

        SwingUtilities.invokeLater(this::runSwingApp);

    }


    public static SwingApp getInstance() {

        if (swingAppInstance == null)
            swingAppInstance = new SwingApp();

        return swingAppInstance;

    }


    private void runSwingApp() {

        setUsername();

        this.setTitle("WhatToDo");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setPreferredSize(new Dimension(1000,800));

        this.setResizable(false);


        SplitPane splitPane = new SplitPane();

        this.setContentPane(splitPane);


        this.pack();

        this.setLocationRelativeTo( null );

        this.setVisible( true );

        ContentPanelState.setCurrentState(ContentPanelState.ALL_TASKS);

    }

    protected static void setUsername() {
        String input = "",
               msg = "Enter your username";

        while ((input = JOptionPane.showInputDialog(msg, input)) != null) {

            Scanner scanner = new Scanner(input);

            boolean error = false;

            if (!scanner.hasNext()) {
                error = true;
            }

            try {
                input = scanner.next();
            } catch (Exception exc) {
                error = true;
            }

            if (error)
                msg = "<html><font color=red>Invalid input.</font></html>";
            else
                break;

        }

        if( input != null )
            username = input;
    }


}
