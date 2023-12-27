package karlord19.cardarchitect;

public class Area {
    public float width, height;
    public String toString() {
        return "area: (" + width + ", " + height + ")";
    }
    public Area(float width, float height) {
        this.width = width;
        this.height = height;
    }
    public Area() {
        this.width = 0;
        this.height = 0;
    }
}
