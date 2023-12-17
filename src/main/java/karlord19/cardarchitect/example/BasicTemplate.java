package karlord19.cardarchitect.example;

import karlord19.cardarchitect.PDFManager;
import karlord19.cardarchitect.Picture;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Picture picture = new Picture();
        picture.add_picture("example/BasicTemplate/pics/cmrk.jpg");
        PDFManager pdf = new PDFManager("BasicTemplate.pdf");
        try {
            pdf.addPage();
        } catch (Exception e) {
            System.out.println("Failed to add page.");
            e.printStackTrace();
        }
        pdf.draw(picture, 100, 200, 300, 400, 0);
        try{
            pdf.close();
        } catch (Exception e) {
            System.out.println("Failed to close pdf.");
        }
    }
}
