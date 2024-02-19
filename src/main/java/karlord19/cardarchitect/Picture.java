package karlord19.cardarchitect;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Picture
 * 
 * A class that represents a collection of pictures on one spot.
 */
public class Picture implements Drawable {

    private File[] pictures;
    private Fit fit;
    private Logger logger = Logger.getLogger(Picture.class.getName());

    public Picture() {
        this.pictures = new File[0];
        this.fit = new Fit();
    }

    public void addPicture(String path) {
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

    public void addPictures(String dirPath, String regex) {
        File dir;
        try {
            URL url = getClass().getResource(dirPath);
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
            if (file.getName().matches(regex)) {
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

    public void setFit(Fit fit) {
        this.fit = fit;
    }
    
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        if (pictures.length == 0) {
            logger.warning("No pictures to draw.");
            return;
        }
        index = index % pictures.length;
        logger.info("Drawing picture " + pictures[index].getName() + " at index " + index + " to " + pa);

        PDDocument document = pdf.getDocument();
        PDPageContentStream contentStream = pdf.getContentStream();
        try {
            PDImageXObject image = PDImageXObject.createFromFile(pictures[index].getPath(), document);
            PDFManager.PDFPA pdfpa = fit.givePositionedArea(new PDFManager.PDFArea(image.getWidth(), image.getHeight()), pdf.transform(pa));
            contentStream.saveGraphicsState();
            contentStream.addRect(pdfpa.pos.x, pdfpa.pos.y, pdfpa.area.width, pdfpa.area.height);
            contentStream.clip();
            contentStream.drawImage(image, pdfpa.pos.x, pdfpa.pos.y, pdfpa.area.width, pdfpa.area.height);
            contentStream.restoreGraphicsState();
        }
        catch (Exception e) {
            logger.warning("Failed to draw picture " + pictures[index].getName());
            e.printStackTrace();
            return;
        }
    }
}
