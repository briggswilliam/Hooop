import java.awt.*;
import javax.swing.*;

public class ScreenScaler {

    private static final Dimension BASE_RESOLUTION = new Dimension(1920, 1080);

    public static void scaleFrame(JFrame frame) {
        if (frame == null) return;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double scaleX = screenSize.getWidth() / BASE_RESOLUTION.getWidth();
        double scaleY = screenSize.getHeight() / BASE_RESOLUTION.getHeight();

        scaleComponent(frame.getContentPane(), scaleX, scaleY);
        scaleFont(frame.getContentPane(), Math.min(scaleX, scaleY));

        int newWidth = (int) (frame.getWidth() * scaleX);
        int newHeight = (int) (frame.getHeight() * scaleY);
        frame.setSize(newWidth, newHeight);

        frame.revalidate();
        frame.repaint();
    }

    private static void scaleComponent(Component comp, double scaleX, double scaleY) {
        if (comp == null) return;

        if (comp instanceof Container container) {
            LayoutManager layout = container.getLayout();

            if (layout == null) {
                int newX = (int) (comp.getX() * scaleX);
                int newY = (int) (comp.getY() * scaleY);
                int newW = (int) (comp.getWidth() * scaleX);
                int newH = (int) (comp.getHeight() * scaleY);
                comp.setBounds(newX, newY, newW, newH);
            } else {
                int newW = (int) (comp.getWidth() * scaleX);
                int newH = (int) (comp.getHeight() * scaleY);
                comp.setPreferredSize(new Dimension(newW, newH));
            }

            for (Component child : container.getComponents()) {
                scaleComponent(child, scaleX, scaleY);
            }
        }

        // Scale JLabel background images
        if (comp instanceof JLabel lbl) {
            Icon icon = lbl.getIcon();
            if (icon instanceof ImageIcon imgIcon) {
                int w = lbl.getWidth();
                int h = lbl.getHeight();
                if (w > 0 && h > 0) {
                    Image scaledImg = imgIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    lbl.setIcon(new ImageIcon(scaledImg));
                }
            }
        }

        // Scale JButton icons (Frogs, Leaves, Bridges, ActionCards)
        if (comp instanceof JButton btn) {
            Icon icon = btn.getIcon();
            if (icon instanceof ImageIcon imgIcon) {
                int w = btn.getWidth();
                int h = btn.getHeight();
                if (w > 0 && h > 0) {
                    Image scaledImg = imgIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    btn.setIcon(new ImageIcon(scaledImg));
                }
            }
            // Scale disabled icon as well
            Icon disIcon = btn.getDisabledIcon();
            if (disIcon instanceof ImageIcon disImgIcon) {
                int w2 = btn.getWidth();
                int h2 = btn.getHeight();
                if (w2 > 0 && h2 > 0) {
                    Image scaledDisImg = disImgIcon.getImage().getScaledInstance(w2, h2, Image.SCALE_SMOOTH);
                    btn.setDisabledIcon(new ImageIcon(scaledDisImg));
                }
            }

        }
        
        

        // Special handling for JTextField, JTextArea, JComboBox (keep your existing logic)
        if (comp instanceof JTextField txtField) {
            scaleTextField(txtField, scaleX, scaleY);
        } else if (comp instanceof JTextArea txtArea) {
            scaleTextArea(txtArea, scaleX, scaleY);
        } else if (comp instanceof JComboBox<?> comboBox) {
            Font f = comboBox.getFont();
            if (f != null)
                comboBox.setFont(f.deriveFont((float) (f.getSize2D() * Math.min(scaleX, scaleY))));
            int newW = (int) (comboBox.getWidth() * scaleX);
            int newH = (int) (comboBox.getHeight() * scaleY);
            comboBox.setBounds((int) (comboBox.getX() * scaleX), (int) (comboBox.getY() * scaleY), newW, newH);
        }
    }

    private static void scaleTextField(JTextField txtField, double scaleX, double scaleY) {
        int newW = (int) (txtField.getWidth() * scaleX);
        int newH = (int) (txtField.getHeight() * scaleY);
        txtField.setBounds((int) (txtField.getX() * scaleX), (int) (txtField.getY() * scaleY), newW, newH);
        Font f = txtField.getFont();
        if (f != null) {
            float newSize = (float) (f.getSize2D() * Math.min(scaleX, scaleY));
            txtField.setFont(f.deriveFont(newSize));
        }
    }
    public static double getUniformScale() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    double scaleX = screenSize.getWidth()  / BASE_RESOLUTION.getWidth();
    double scaleY = screenSize.getHeight() / BASE_RESOLUTION.getHeight();

    // Use the smaller so the popup keeps its aspect ratio and fits on screen
    return Math.min(scaleX, scaleY);
}


    private static void scaleTextArea(JTextArea txtArea, double scaleX, double scaleY) {
        int newW = (int) (txtArea.getWidth() * scaleX);
        int newH = (int) (txtArea.getHeight() * scaleY);
        txtArea.setBounds((int) (txtArea.getX() * scaleX), (int) (txtArea.getY() * scaleY), newW, newH);
        Font f = txtArea.getFont();
        if (f != null) {
            float newSize = (float) (f.getSize2D() * Math.min(scaleX, scaleY));
            txtArea.setFont(f.deriveFont(newSize));
        }
    }

    private static void scaleFont(Component comp, double scale) {
        Font f = comp.getFont();
        if (f != null) {
            float newSize = (float) (f.getSize2D() * scale*1.05);
            comp.setFont(f.deriveFont(newSize));
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                scaleFont(child, scale);
            }
        }
    }
}
