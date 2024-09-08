import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class ContentPanel extends JPanel {

    private static ContentPanel contentPanelInstance = null;

    protected static JLabel mainTaskLabel = new JLabel("YOUR TASKS");


    public static ContentPanel getInstance() {

        if (contentPanelInstance == null)
            contentPanelInstance = new ContentPanel();

        return contentPanelInstance;

    }


    private ContentPanel() {

        this.setContentPanel();

    }

    private void setContentPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createEmptyBorder(5, 35, 5, 35));

        mainTaskLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        mainTaskLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(mainTaskLabel);


        this.add(Box.createVerticalStrut(50));

    }

    public void refreshTasks() {

        if (contentPanelInstance == null)
            return;

        this.removeAll();

        this.setContentPanel();

        SwingApp.currentUser.getTaskLog().sortTasks();


        switch(ContentPanelState.getCurrentState()) {
            case ContentPanelState.ALL_TASKS:
                showAllTasks();
                break;

            case ContentPanelState.TODAY:
                showTodaysTasks();
                break;

        }

        this.revalidate();
        this.repaint();

    }


    public void refreshTitle(String newTitle){
        mainTaskLabel.setText(newTitle);

        this.revalidate();
        this.repaint();
    }


    private void showTodaysTasks() {

        SwingApp.currentUser.getTaskLog().stream()
                .filter(task -> {
                    LocalDate taskDate = task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate today = LocalDate.now();
                    return taskDate.equals(today);
                })
                .forEach(t -> {
                    this.add(new JSeparator(SwingConstants.HORIZONTAL));
                    t.setMaximumSize(new Dimension(Integer.MAX_VALUE, t.getPreferredSize().height));
                    this.add(t);
                    this.add(Box.createVerticalStrut(10));
                });

    }


    private void showAllTasks() {

        for (Task t : SwingApp.currentUser.getTaskLog()) {
            this.add(new JSeparator(SwingConstants.HORIZONTAL));
            t.setMaximumSize(new Dimension(Integer.MAX_VALUE, t.getPreferredSize().height));
            this.add(t);
            this.add(Box.createVerticalStrut(10));
        }

        mainTaskLabel = new JLabel("YOUR TASKS");
        this.revalidate();
        this.repaint();

    }

}
