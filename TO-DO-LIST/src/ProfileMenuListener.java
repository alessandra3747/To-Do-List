import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class ProfileMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        JMenuItem myOption = (JMenuItem) e.getSource();

        switch (myOption.getText()) {

            case "Change Avatar":

                JFileChooser fc = new JFileChooser();

                int openResult = fc.showOpenDialog(null);

                if ( openResult == JFileChooser.APPROVE_OPTION ) {

                    File selectedFile = fc.getSelectedFile();

                    try {
                        BufferedImage avatarImage = ImageIO.read(selectedFile);

                        Image resizedAvatarImage = resizeImage(avatarImage, 40, 40);

                        Menu.updateProfileAvatar(new ImageIcon(resizedAvatarImage));
                    }
                    catch (IOException changeAvatarException) {
                        System.out.println("Error changing avatar");
                        changeAvatarException.printStackTrace();
                    }

                }

                break;

            case "Delete Avatar":

                Menu.updateProfileAvatar(new ImageIcon("././Assets/userProfilePic.png"));

                break;

            case "Change Username":

                SwingApp.setUsername();

                Menu.updateProfileUsername();

                break;

        }

    }

    private Image resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {

        Image resultingImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = outputImage.createGraphics();

        g2d.drawImage(resultingImage, 0, 0, null);

        g2d.dispose();

        return outputImage;
    }

}
