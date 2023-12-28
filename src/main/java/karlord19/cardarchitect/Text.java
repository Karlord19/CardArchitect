package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.font.*;

public class Text implements Drawable {
    private String[] texts;
    private PDFont font = PDType1Font.HELVETICA;
    private int size = 30;
    public enum Type {
        OneLine,
        MultiLine,
        Crop
    }
    private Type type = Type.OneLine;
    public Text(String text) {
        texts = new String[] { text };
    }
    public Text(String[] texts) {
        this.texts = texts;
    }
    public Text() {
        texts = new String[] {};
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void addText(String text) {
        System.out.println("Will add text " + text);
        String[] new_texts = new String[this.texts.length + 1];
        System.arraycopy(this.texts, 0, new_texts, 0, this.texts.length);
        new_texts[this.texts.length] = text;
        this.texts = new_texts;
    }
    public void setFont(PDFont font, int size) {
        this.font = font;
        this.size = size;
    }
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        if (texts.length == 0) {
            System.out.println("No texts to draw.");
            return;
        }
        index = index % texts.length;
        System.out.println("Rendering text " + texts[index] + " at index " + index + " to " + pa);
        switch (type) {
            case Type.OneLine:
                pdf.drawOneLineText(texts[index], pa, font, size);            
                break;
            case Type.MultiLine:
                pdf.drawMultiLineText(texts[index], pa, font, size);
                break;
            case Type.Crop:
                pdf.drawCropText(texts[index], pa, font, size);
                break;
            default:
                break;
        }
    }
}
