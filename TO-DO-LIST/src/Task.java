import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Task extends JPanel {

    private static int counter = 1;

    private JLabel textLabel;
    private final int id;
    private JCheckBox isDoneCheckBox;
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


        ImageIcon icon = new ImageIcon("././Assets/Xpic.jpg");
        JButton deleteTask = new JButton(icon);
        deleteTask.setOpaque(false);
        deleteTask.setContentAreaFilled(false);
        deleteTask.setBorderPainted(false);
        deleteTask.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        deleteTask.addActionListener(e -> {
            try {
                TaskLog.getInstance().removeTaskById(this.id);
            } catch (NoTaskFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                ContentPanel.getInstance().refreshTasks();
            }
        });


        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        rightPanel.add(isDoneCheckBox);
        rightPanel.add(deleteTask);


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

}
