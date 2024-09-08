import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class ProfileMenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        JMenuItem myOption = (JMenuItem) e.getSource();

        switch (myOption.getText()) {

            case "Change Avatar":

                JFileChooser fc = new JFileChooser();

                int openResult = fc.showOpenDialog(null);

                if ( openResult == JFileChooser.APPROVE_OPTION ) {

                    //File selectedFile = fc.getSelectedFile();

                    changeAvatar(fc.getSelectedFile());

                    /*try {
                        BufferedImage avatarImage = ImageIO.read(selectedFile);

                        if (avatarImage != null) {
                            Image resizedAvatarImage = resizeImage(avatarImage, 40, 40);
                            Menu.updateProfileAvatar(new ImageIcon(resizedAvatarImage));

                            saveAvatarImage(avatarImage);
                        } else {
                            System.out.println("Failed to load image from the selected file.");
                        }
                    }
                    catch (IOException changeAvatarException) {
                        System.out.println("Error changing avatar");
                        changeAvatarException.printStackTrace();
                    }*/

                }

                break;

            case "Delete Avatar":

                Menu.updateProfileAvatar(new ImageIcon("././Assets/userProfilePic.png"));

                SwingApp.currentUser.setAvatar(null);

                break;

            case "Change Username":

                setUsername();

                Menu.updateProfileUsername();

                break;

            case "Exit":

                UserManager.saveUsers();

                if( SwingApp.currentUser.getAvatar() != null ) {
                    try {
                        saveAvatarImage();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                System.exit(0);

                break;

        }

    }


    private void changeAvatar(File file) {
        try {
            BufferedImage avatarImage = ImageIO.read(file);

            if (avatarImage != null) {

                Image resizedAvatarImage = resizeImage(avatarImage, 40, 40);

                ImageIcon imageIcon = new ImageIcon(resizedAvatarImage);

                Menu.updateProfileAvatar(imageIcon);
                SwingApp.currentUser.setAvatar(imageIcon);

            } else {
                System.out.println("Failed to load image from the selected file.");
            }
        }
        catch (IOException changeAvatarException) {
            System.out.println("Error changing avatar");
            changeAvatarException.printStackTrace();
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


    private void saveAvatarImage() throws IOException {

        ImageIcon avatarIcon = SwingApp.currentUser.getAvatar();
        Image image = avatarIcon.getImage();

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        File file = new File("././Assets/AvatarPic");
        ImageIO.write(bufferedImage, "png", file);
    }


    private void setUsername() {
        String input = "",
                msg = "Enter your new login";

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

        try {
            if (input != null && UserManager.doesUserExist(input)) {
                 JOptionPane.showMessageDialog(null, "<html><font color=red>This login is already taken.</font></html>", "LoginTakenError", JOptionPane.ERROR_MESSAGE);
                 setUsername();
            } else if (input != null){
                SwingApp.currentUser.setLogin(input);
            }
        } catch (NoUserFoundException e) {
            System.out.println(e.getMessage());
        }

    }


}
