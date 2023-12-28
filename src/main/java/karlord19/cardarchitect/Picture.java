package karlord19.cardarchitect;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.Arrays;

/**
 * Picture
 * 
 * A class that represents a collection of pictures on one spot.
 */
public class Picture implements Drawable {

    private File[] pictures;
    private Fit fit;

    public Picture() {
        this.pictures = new File[0];
        this.fit = new Fit();
    }

    public void addPicture(String path) {
        System.out.println("Will add picture " + path);
        Path real_path;
        try {
            URL url = getClass().getResource(path);
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

    public void addPictures(String dirPath, String regex) {
        System.out.println("Will add pictures from " + dirPath + " with regex " + regex);
        File dir;
        try {
            URL url = getClass().getResource(dirPath);
            dir = new File(url.toURI());
        } catch (Exception e) {
            System.out.println("Failed to get path to directory.");
            e.printStackTrace();
            return;
        }
        File[] dirFiles = dir.listFiles();
        File[] newPictures = new File[this.pictures.length + dirFiles.length];
        System.arraycopy(this.pictures, 0, newPictures, 0, this.pictures.length);
        int i = this.pictures.length;
        for (File file : dir.listFiles()) {
            if (file.getName().matches(regex)) {
                newPictures[i] = file;
                i++;
            }
        }
        File[] croppedPictures = new File[i];
        System.arraycopy(newPictures, 0, croppedPictures, 0, i);
        Arrays.sort(croppedPictures, (File f1, File f2) -> f1.getName().compareTo(f2.getName()));
        this.pictures = croppedPictures;
    }

    public void setFit(Fit fit) {
        this.fit = fit;
    }
    
    public void draw(PositionedArea pa, int index, PDFManager pdf) {
        if (pictures.length == 0) {
            System.out.println("No pictures to draw.");
            return;
        }
        index = index % pictures.length;
        System.out.println("Rendering picture " + pictures[index].getName() + " at index " + index + " to " + pa);
        pdf.drawImage(pictures[index], pa, fit);
    }
}
