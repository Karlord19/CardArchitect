package karlord19.cardarchitect;

import java.util.logging.Logger;

import karlord19.cardarchitect.PDFManager.PDFArea;
import karlord19.cardarchitect.PDFManager.PDFPA;

/**
 * Fit
 * 
 * Class that represents the fit of an image in a bounding box.
 * Can be used for multiple Drawable objects at once.
 */
public class Fit {
    private static final Logger logger = Logger.getLogger(Fit.class.getName());

    /**
     * FitType
     * 
     * Enum for the type of fit to be used.
     * STRETCH will stretch the image to fit the bounding box without remainer.
     * SCALE will scale the image to fit the bounding box, keeping the aspect ratio.
     */
    public enum FitType {
        FIT_WIDTH,
        FIT_HEIGHT,
        SCALE,
        STRETCH,
        ORIGINAL
    };

    private FitType fitType;

    /**
     * Set the fit type.
     * @param fitType
     */
    public void setFitType(FitType fitType) {
        this.fitType = fitType;
    }

    /**
     * FitPositionX
     * 
     * Enum for the horizontal position of the image in the bounding box.
     */
    public enum FitPositionX {
        LEFT,
        CENTER,
        RIGHT
    };

    private FitPositionX fitPositionX;

    /**
     * Set the horizontal position of the image in the bounding box.
     * @param fitPositionX
     */
    public void setFitPositionX(FitPositionX fitPositionX) {
        this.fitPositionX = fitPositionX;
    }

    /**
     * FitPositionY
     * 
     * Enum for the vertical position of the image in the bounding box.
     */
    public enum FitPositionY {
        TOP,
        CENTER,
        BOTTOM
    };

    private FitPositionY fitPositionY;

    /**
     * Set the vertical position of the image in the bounding box.
     * @param fitPositionY
     */
    public void setFitPositionY(FitPositionY fitPositionY) {
        this.fitPositionY = fitPositionY;
    }

    /**
     * Fit
     * 
     * Constructor for the Fit class.
     * Sets the default values for the fit type and position.
     * Type is set to SCALE and position to CENTER.
     */
    public Fit() {
        fitType = FitType.SCALE;
        fitPositionX = FitPositionX.CENTER;
        fitPositionY = FitPositionY.CENTER;
    }

    private PDFArea giveArea(PDFArea imgArea, PDFArea boundingBox) {
        float width = 0;
        float height = 0;
        switch (fitType) {
            case STRETCH:
                width = boundingBox.width;
                height = boundingBox.height;
                break;
            case FIT_WIDTH:
                width = boundingBox.width;
                height = imgArea.height * boundingBox.width / imgArea.width;
                if (height > boundingBox.height) {
                    logger.warning("Fit width: image is taller by " + (height - boundingBox.height) + " than bounding box.");
                }
                break;
            case FIT_HEIGHT:
                height = boundingBox.height;
                width = imgArea.width * boundingBox.height / imgArea.height;
                if (width > boundingBox.width) {
                    logger.warning("Fit height: image is wider by " + (width - boundingBox.width) + " than bounding box.");
                }
                break;
            case SCALE:
                if (imgArea.width / imgArea.height > boundingBox.width / boundingBox.height) {
                    width = boundingBox.width;
                    height = imgArea.height * boundingBox.width / imgArea.width;
                } else {
                    height = boundingBox.height;
                    width = imgArea.width * boundingBox.height / imgArea.height;
                }
                break;
            case ORIGINAL:
                width = imgArea.width;
                height = imgArea.height;
                break;
            default:
                break;
        }
        return new PDFArea(width, height);
    }

    PDFPA givePositionedArea(PDFArea imgArea, PDFPA boundingBox) {
        float x = 0;
        float y = 0;
        PDFArea givenArea = giveArea(imgArea, boundingBox.area);
        switch (fitPositionX) {
            case LEFT:
                x = boundingBox.pos.x;
                break;
            case CENTER:
                x = boundingBox.pos.x + (boundingBox.area.width - givenArea.width) / 2;
                break;
            case RIGHT:
                x = boundingBox.pos.x + boundingBox.area.width - givenArea.width;
                break;
            default:
                break;
        }
        switch (fitPositionY) {
            case TOP:
                y = boundingBox.pos.y + boundingBox.area.height - givenArea.height;
                break;
            case CENTER:
                y = boundingBox.pos.y + (boundingBox.area.height - givenArea.height) / 2;
                break;
            case BOTTOM:
                y = boundingBox.pos.y;
                break;
            default:
                break;
        }
        return new PDFManager.PDFPA(x, y, givenArea.width, givenArea.height);
    }
}
