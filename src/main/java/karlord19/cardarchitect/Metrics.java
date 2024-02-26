package karlord19.cardarchitect;

/**
 * Metrics
 * 
 * A class that contains methods for converting between micrometers (m) and points (p).
 */
class Metrics {
    static float m2p(int micrometers) {
        return ((float)(micrometers * 72)) / ((float)25400);
    }
    static float[] m2p(int[] micrometers) {
        float[] points = new float[micrometers.length];
        for (int i = 0; i < micrometers.length; i++) {
            points[i] = m2p(micrometers[i]);
        }
        return points;
    }
    static int p2m(float points) {
        return (int)(((float)(points * 25400)) / ((float)72));
    }
    static int[] p2m(float[] points) {
        int[] micrometers = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            micrometers[i] = p2m(points[i]);
        }
        return micrometers;
    }
}
