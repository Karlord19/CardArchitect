package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

/**
 * Picture
 * 
 * A class that represents a collection of pictures on one spot.
 */
public class Picture implements Drawable {

    private File[] pictures;

    public Picture() {
        this.pictures = new File[0];
    }

    public void add_picture(String path) {
        System.out.println("Will add picture " + path);
        URL url;
        Path real_path;
        try {
            url = getClass().getResource(path);
            real_path = Paths.get(url.toURI());
        } catch (Exception e) {
            System.out.println("Failed to get path to picture.");
            e.printStackTrace();
            return;
        }
        File[] new_pictures = new File[this.pictures.length + 1];
        System.arraycopy(this.pictures, 0, new_pictures, 0, this.pictures.length);
        new_pictures[this.pictures.length] = real_path.toFile();
        this.pictures = new_pictures;
    }
    
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        System.out.println("Rendering picture " + pictures[index].getName() + " at index " + index + " to " + pa);
        try {
            PDImageXObject image = PDImageXObject.createFromFile(pictures[index].getPath(), pdf.getDocument());
            pdf.getContentStream().drawImage(image, pa.pos.x, pa.pos.y, pa.area.width, pa.area.height);
        } catch (IOException e) {
            System.out.println("Failed to render picture " + pictures[index].getName() + " at index " + index + " to " + pa);
            e.printStackTrace();
        }
    }
}
