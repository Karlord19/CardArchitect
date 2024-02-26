package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

/**
 * LineStyle
 * 
 * A class that represents a line style.
 */
public class LineStyle {
    int width;
    PDColor color;

    /**
     * Constructor of LineStyle
     * @param width
     * @param color Can be in whatever color space you want.
     */
    public LineStyle(int width, PDColor color){
        this.width = width;
        this.color = color;
    }

    /**
     * Constructor of LineStyle, using RGB color space.
     * @param width
     * @param red
     * @param green
     * @param blue
     */
    public LineStyle(int width, float red, float green, float blue){
        this.width = width;
        this.color = new PDColor(new float[] {red, green, blue}, PDDeviceRGB.INSTANCE);
    }
}
