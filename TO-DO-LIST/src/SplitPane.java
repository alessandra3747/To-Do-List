import javax.swing.*;

public class SplitPane extends JSplitPane {

    public SplitPane() {

        super(JSplitPane.HORIZONTAL_SPLIT, Menu.getInstance(), ContentPanel.getInstance());

        this.setDividerSize(2);

        this.setContinuousLayout(true);

        this.setDividerLocation(200);
    }


}
