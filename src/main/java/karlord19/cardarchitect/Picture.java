package karlord19.cardarchitect;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
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
public class Picture {

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
    
    /**
     * Render the picture at the given index onto a PDF at the specified position and size.
     * 
     * @param x      The x-coordinate of the top-left corner of the rendered picture.
     * @param y      The y-coordinate of the top-left corner of the rendered picture.
     * @param width  The width of the rendered picture.
     * @param height The height of the rendered picture.
     * @param index  The index of the picture to render.
     * @param pdfPath The path to the PDF file to generate.
     */
    public void render(int x, int y, int width, int height, int index, String pdfPath) throws IOException {
        System.out.println("Rendering picture " + index + " to " + pdfPath);
        
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDImageXObject image = PDImageXObject.createFromFile(pictures[index].getPath(), document);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(image, x, y, width, height);
        contentStream.close();

        document.save(pdfPath);
        document.close();
    }
}
