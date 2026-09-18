import java.awt.*;
import javax.swing.*;

public class PopupOverlay {
    private ImageIcon popup;
    private JLabel popupLabel;
    private JPanel popupOverlay;

    public PopupOverlay(){
        popupOverlay = new JPanel(null) {
        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(popup.getImage(), 750, 495, null);
    }
};

// allow mouse events (default GlassPane blocks them)
    popupOverlay.setOpaque(false);
    popupOverlay.setVisible(false);
    popupOverlay.setPreferredSize(new Dimension(
    popup.getIconWidth(),
    popup.getIconHeight()
    ));

// IMPORTANT: enable mouse input
    popupOverlay.setEnabled(true);
    popupOverlay.setFocusable(true);

        
}
public JPanel getPanel(){
    return popupOverlay;
}
public ImageIcon getImage(){
    return popup;
}
}