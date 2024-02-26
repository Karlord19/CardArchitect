package karlord19.cardarchitect;

import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * WrapText
 * 
 * A class that represents a text that has automatic line wrapping.
 */
public class WrapText extends MultilineText {

    /**
     * Create a WrapText.
     */
    public WrapText() {
        super();
    }

    /**
     * Create a WrapText with a text.
     * @param text
     */
    public WrapText(String text) {
        super(text);
    }

    /**
     * Create a WrapText with texts.
     * @param texts
     */
    public WrapText(String[] texts) {
        super(texts);
    }

    @Override
    protected void draw(PDFManager.PDFPA boundingBox, int index, PDPageContentStream contentStream) {
        String[] words = texts.get(index).split(" ");
        String line = null;
        ArrayList<String> lines = new ArrayList<String>();
        for (String word : words) {
            String newLine = (line == null) ? word : line + " " + word;
            float newLineWidth = getWidth(newLine);
            if (newLineWidth > boundingBox.area.width) {
                lines.add(line);
                line = word;
            } else {
                line = newLine;
            }
        }
        if (line.length() > 0) {
            lines.add(line);
        }
        draw(lines.toArray(new String[0]), boundingBox, contentStream);
    }
}
