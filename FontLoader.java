import java.awt.Font;
import java.io.File;

public class FontLoader {

    public static Font load(String path, float size) {
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (Exception e) {
            font = new Font("SansSerif", Font.PLAIN, (int) size);
        }
        return font;
    }
}
