package karlord19.cardarchitect;

public class Position {
    public int x, y;
    public String toString() {
        return "pos: (" + x + ", " + y + ")";
    }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
