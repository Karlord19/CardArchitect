package karlord19.cardarchitect;

public class Area {
    public int width, height;
    public String toString() {
        return "area: (" + width + ", " + height + ")";
    }
    public Area(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public Area() {
        this.width = 0;
        this.height = 0;
    }
}
