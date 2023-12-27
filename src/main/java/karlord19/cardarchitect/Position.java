package karlord19.cardarchitect;

public class Position {
    public float x, y;
    public String toString() {
        return "pos: (" + x + ", " + y + ")";
    }
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
