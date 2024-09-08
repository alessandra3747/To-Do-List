import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Task extends JPanel implements Serializable {

    private static int counter = 1;

    private final JLabel textLabel;
    private final int id;
    private final JCheckBox isDoneCheckBox;
    private final JButton deleteTaskButton;
    private Date date;

    public Task(String text, Date date) {

        this.setLayout(new BorderLayout());
        this.setOpaque(false);

        String htmlText = "<html>" + text.replace("\n", "<br>") + "</html>";
        this.textLabel = new JLabel(htmlText);
        this.textLabel.setFont(new Font("Arial", Font.PLAIN, 15));


        this.id = counter++;
        this.date = date;


        this.isDoneCheckBox = new JCheckBox();
        this.isDoneCheckBox.setOpaque(false);
        this.isDoneCheckBox.addActionListener(e -> ContentPanel.getInstance().refreshTasks());


        deleteTaskButton = getDeleteButton();

        JLabel formattedDateLabel = getFormattedDateLabel();

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));


        rightPanel.add(formattedDateLabel);
        rightPanel.add(isDoneCheckBox);
        rightPanel.add(deleteTaskButton);


        this.add(textLabel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);

    }


    public String getText() {
        return this.textLabel.getText();
    }

    public int getId() {
        return this.id;
    }

    public boolean isDone() {
        return this.isDoneCheckBox.isSelected();
    }

    public Date getDate() {
        return this.date;
    }


    public void setText(String text) {
        this.textLabel.setText(text);
    }


    public void setDate(Date date) {
        this.date = date;
    }



    private JButton getDeleteButton() {

        ImageIcon icon = new ImageIcon("././Assets/Xpic.jpg");

        JButton deleteButton = new JButton(icon);

        deleteButton.setOpaque(false);

        deleteButton.setContentAreaFilled(false);

        deleteButton.setBorderPainted(false);

        deleteButton.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        deleteButton.addActionListener(e -> {
            try {
                SwingApp.currentUser.getTaskLog().removeTaskById(this.id);
            } catch (NoTaskFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                ContentPanel.getInstance().refreshTasks();
            }
        });

        return deleteButton;
    }



    private JLabel getFormattedDateLabel() {

        DateLabelFormatter formatter = new DateLabelFormatter();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);

        String formattedDate = formatter.valueToString(calendar);

        JLabel formattedDateLabel = new JLabel(formattedDate);

        if(isTaskBeforeToday(this))
            formattedDateLabel.setForeground(Color.RED);

        return formattedDateLabel;

    }


    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public boolean isTaskBeforeToday(Task task) {
        Date todayWithoutTime = getDateWithoutTime(new Date());
        Date taskDateWithoutTime = getDateWithoutTime(task.getDate());
        return taskDateWithoutTime.before(todayWithoutTime);
    }

    protected void addTaskListeners(){

        this.isDoneCheckBox.addActionListener(e -> ContentPanel.getInstance().refreshTasks());

        this.deleteTaskButton.addActionListener(e -> {
            try {
                SwingApp.currentUser.getTaskLog().removeTaskById(this.id);
            } catch (NoTaskFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                ContentPanel.getInstance().refreshTasks();
            }
        });
    }

}
