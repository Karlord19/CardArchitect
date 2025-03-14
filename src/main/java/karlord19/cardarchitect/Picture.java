package karlord19.cardarchitect;

import com.twelvemonkeys.imageio.plugins.webp.WebPImageReaderSpi;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Picture
 * 
 * A class that represents a collection of pictures on one spot.
 */
public class Picture extends AbstractDrawable {

    static {
        IIORegistry.getDefaultInstance().registerServiceProvider(new WebPImageReaderSpi());
    }

    private File[] pictures = new File[0];
    private Fit fit = new Fit();
    private static final Logger logger = Logger.getLogger(Picture.class.getName());

    /**
     * Create a Picture.
     */
    public Picture() {}

    /**
     * Create a Picture with a picture.
     * @param path
     */
    public Picture(String path) {
        add(path);
    }

    /**
     * Add a picture to the collection.
     * @param path
     */
    @Override
    public void add(String path) {
        Path real_path;
        try {
            URL url = getClass().getResource(path);
            real_path = Paths.get(url.toURI());
        } catch (Exception e) {
            logger.warning("Failed to get path to picture " + path);
            e.printStackTrace();
            return;
        }
        File[] new_pictures = new File[this.pictures.length + 1];
        System.arraycopy(this.pictures, 0, new_pictures, 0, this.pictures.length);
        new_pictures[this.pictures.length] = real_path.toFile();
        this.pictures = new_pictures;
    }

    /**
     * Add all pictures from a directory that match the regex to the collection.
     * @param dirPath
     * @param regex The regex, or null to match all files.
     */
    public void addDir(String dirPath, String regex) {
        File dir;
        try {
            URL url = getClass().getResource(dirPath);
            if (url == null) throw new Exception();
            dir = new File(url.toURI());
        } catch (Exception e) {
            logger.warning("Failed to get path to picture directory " + dirPath);
            e.printStackTrace();
            return;
        }
        File[] dirFiles = dir.listFiles();
        File[] matching = new File[dirFiles.length];
        int i = 0;
        for (File file : dir.listFiles()) {
            if (regex == null || file.getName().matches(regex)) {
                matching[i] = file;
                i++;
            }
        }
        File[] croppedMatching = new File[i];
        System.arraycopy(matching, 0, croppedMatching, 0, i);
        Arrays.sort(croppedMatching, (File f1, File f2) -> f1.getName().compareTo(f2.getName()));
        File[] new_pictures = new File[this.pictures.length + croppedMatching.length];
        System.arraycopy(this.pictures, 0, new_pictures, 0, this.pictures.length);
        System.arraycopy(croppedMatching, 0, new_pictures, this.pictures.length, croppedMatching.length);
        this.pictures = new_pictures;
    }

    /**
     * Set the Fit of the pictures.
     * @param fit
     */
    public void setFit(Fit fit) {
        this.fit = fit;
    }
    
    void draw(PositionedArea pa, int index, PDFManager pdf) {
        if (pictures.length == 0) {
            logger.warning("No pictures to draw.");
            return;
        }
        index = index % pictures.length;
        logger.info("Drawing picture " + pictures[index].getName() + " at index " + index + " to " + pa);

        pa.area.height += 5; pa.area.width += 5; // avoid blank space between pictures
        PDDocument document = pdf.getDocument();
        PDPageContentStream contentStream = pdf.getContentStream();
        try {
            BufferedImage bufferedImage = ImageIO.read(pictures[index]);
            PDImageXObject image = LosslessFactory.createFromImage(document, bufferedImage);
            PDFManager.PDFPA boundingBox = pdf.transform(pa);
            PDFManager.PDFPA imageBox = fit.givePositionedArea(new PDFManager.PDFArea(image.getWidth(), image.getHeight()), boundingBox);
            contentStream.saveGraphicsState();
            contentStream.addRect(boundingBox.pos.x, boundingBox.pos.y, boundingBox.area.width, boundingBox.area.height);
            contentStream.clip();
            contentStream.drawImage(image, imageBox.pos.x, imageBox.pos.y, imageBox.area.width, imageBox.area.height);
            contentStream.restoreGraphicsState();
        }
        catch (Exception e) {
            logger.warning("Failed to draw picture " + pictures[index].getName());
            e.printStackTrace();
            return;
        }
    }
}
