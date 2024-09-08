import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Properties;

public class Menu extends JPanel {

    private static Menu menuInstance = null;


    protected JMenuBar profileMenuBar;
    protected static JMenu profileMenu = null;

    protected JButton addTaskButton;
    protected JButton allTasksButton;
    protected JButton todayButton;


    private Menu() {

        this.setBackground(Color.WHITE);


        this.add(Box.createVerticalStrut(70));


        setProfileMenu();


        this.add(Box.createVerticalStrut(10));


        setAddTaskButton();


        this.add(Box.createVerticalStrut(10));


        setAllTasksButton();


        this.add(Box.createVerticalStrut(10));


        setTodayButton();

    }

    public static Menu getInstance() {

        if (menuInstance == null)
            menuInstance = new Menu();

        return menuInstance;

    }


    private void setProfileMenu() {

        profileMenuBar = new JMenuBar();

        profileMenu = new JMenu();

        setProperties(profileMenu, SwingApp.username + "'s Profile", Color.WHITE, Color.BLACK, "././Assets/userProfilePic.png");

        profileMenu.setPreferredSize(new Dimension(200, 40));


        JMenuItem changeAvatar = new JMenuItem("Change Avatar");
        changeAvatar.addActionListener(new ProfileMenuListener());

        JMenuItem deleteAvatar = new JMenuItem("Delete Avatar");
        deleteAvatar.addActionListener(new ProfileMenuListener());

        JMenuItem changeUsername = new JMenuItem("Change Username");
        changeUsername.addActionListener(new ProfileMenuListener());

        JMenuItem exitApp = new JMenuItem("Exit");
        exitApp.addActionListener(new ProfileMenuListener());


        profileMenu.add(changeAvatar);
        profileMenu.add(deleteAvatar);
        profileMenu.add(changeUsername);

        profileMenu.addSeparator();

        profileMenu.add(exitApp);


        profileMenuBar.add(profileMenu);
        this.add(profileMenuBar);

    }


    private void setAddTaskButton() {

        addTaskButton = new JButton();

        setProperties(addTaskButton, "Add Task", Color.WHITE, new Color(255, 87, 87), "././Assets/addTask.png");

        addTaskButton.setMargin(new Insets(0, 15, 0, 5));

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //TASK TEXT SECTION
                JPanel dialogPanel = new JPanel();
                dialogPanel.setPreferredSize(new Dimension(300, 100));
                dialogPanel.setLayout(new BorderLayout());
                dialogPanel.setBackground(Color.WHITE);


                JTextArea taskTextArea = new JTextArea(10, 20);
                taskTextArea.setLineWrap(true);
                taskTextArea.setWrapStyleWord(true);
                taskTextArea.setBackground(Color.WHITE);
                taskTextArea.setForeground(Color.BLACK);


                JScrollPane scrollPane = new JScrollPane(taskTextArea);
                scrollPane.setPreferredSize(new Dimension(250, 100));


                //CALENDAR SECTION
                UtilDateModel model = new UtilDateModel();

                Properties p = new Properties();
                p.put("text.year", "Year");
                p.put("text.month", "Month");
                p.put("text.today", "Today");

                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

                JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


                dialogPanel.add(scrollPane, BorderLayout.CENTER);
                dialogPanel.add(datePicker, BorderLayout.SOUTH);


                //DIALOG SECTION
                boolean validInput = false;

                while(!validInput) {

                    int result = JOptionPane.showConfirmDialog(null, dialogPanel, "Add Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {

                        String taskString = taskTextArea.getText();
                        Date taskDate = (Date) datePicker.getModel().getValue();

                        if (taskString.isEmpty() || taskDate == null) {
                            JOptionPane.showMessageDialog(null, "Fill out all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            validInput = true;

                            SwingApp.currentUser.getTaskLog().addTask(new Task(taskString, taskDate));
                            ContentPanel.getInstance().refreshTasks();
                        }

                    } else {
                        break;
                    }

                }

            }
        });

        this.add(addTaskButton);

    }


    private void setAllTasksButton() {

        allTasksButton = new JButton();

        setProperties(allTasksButton, "All Tasks", Color.WHITE, Color.BLACK, "././Assets/calendar.png");

        allTasksButton.setIconTextGap(10);

        allTasksButton.setMargin(new Insets(0, 20, 0, 5));

        allTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContentPanelState.setCurrentState(ContentPanelState.ALL_TASKS);
                ContentPanel.getInstance().refreshTasks();
                ContentPanel.getInstance().refreshTitle("YOUR TASKS");
            }
        });

        this.add(allTasksButton);
    }


    private void setTodayButton() {
        todayButton = new JButton();

        setProperties(todayButton, "Today", Color.WHITE, Color.BLACK, "././Assets/today.png");

        todayButton.setIconTextGap(-5);

        todayButton.setMargin(new Insets(0, 8, 0, 5));

        todayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ContentPanelState.setCurrentState(ContentPanelState.TODAY);
                ContentPanel.getInstance().refreshTasks();
            }
        });

        this.add(todayButton);

    }


    private void setProperties(AbstractButton obj, String text, Color background, Color foreground, String iconPath) {

        obj.setText(text);

        obj.setFont(new Font("Helvetica", Font.BOLD, 16));

        obj.setForeground(foreground);
        obj.setBackground(background);

        obj.setFocusPainted(false);
        obj.setBorderPainted(false);

        obj.setOpaque(true);

        obj.setHorizontalAlignment(SwingConstants.LEFT);

        obj.setPreferredSize(new Dimension(250, 40));
        obj.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        obj.setAlignmentX(Component.LEFT_ALIGNMENT);

        obj.setIcon(new ImageIcon(iconPath));

    }


    protected static void updateProfileAvatar(ImageIcon img) {
        profileMenu.setIcon(img);
    }

    protected static void updateProfileUsername() {
        profileMenu.setText(SwingApp.currentUser.getLogin() + "'s Profile");
    }

}
