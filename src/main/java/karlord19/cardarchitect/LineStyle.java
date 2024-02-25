package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

public class LineStyle {
    public int width;
    public PDColor color;
    public LineStyle(int width, PDColor color){
        this.width = width;
        this.color = color;
    }
    public LineStyle(int width, float red, float green, float blue){
        this.width = width;
        this.color = new PDColor(new float[] {red, green, blue}, PDDeviceRGB.INSTANCE);
    }
}
