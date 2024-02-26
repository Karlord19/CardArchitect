package karlord19.cardarchitect;

/**
 * Area
 * 
 * Representation of an area
 * In micrometers.
 */
class Area {
    int width, height;
    public String toString() {
        return "area: (" + width + ", " + height + ")";
    }
    Area(int width, int height) {
        this.width = width;
        this.height = height;
    }
    Area() {
        this.width = 0;
        this.height = 0;
    }
}
