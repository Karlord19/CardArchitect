package karlord19.cardarchitect.example;

import karlord19.cardarchitect.Picture;

/**
 * BasicTemplate
 */
public class BasicTemplate {

    public static void main(String[] args) {
        Picture picture = new Picture();
        picture.add_picture("example/BasicTemplate/pics/cmrk.jpg");
        try {
            picture.render(0, 0, 100, 100, 0, "BasicTemplate.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
