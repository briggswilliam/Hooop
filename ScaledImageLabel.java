import java.awt.*;
import javax.swing.*;

public class ScaledImageLabel extends JLabel {
    private final Image originalImage;

    public ScaledImageLabel(Image image) {
        this.originalImage = image;
        setLayout(null); // Allow absolute positioning of buttons
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (originalImage != null) {
            g.drawImage(originalImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
