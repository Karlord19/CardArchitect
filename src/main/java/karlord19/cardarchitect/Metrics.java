package karlord19.cardarchitect;

public class Metrics {
    public static float m2p(int micrometers) {
        return ((float)(micrometers * 72)) / ((float)25400);
    }
    public static float[] m2p(int[] micrometers) {
        float[] points = new float[micrometers.length];
        for (int i = 0; i < micrometers.length; i++) {
            points[i] = m2p(micrometers[i]);
        }
        return points;
    }
    public static int p2m(float points) {
        return (int)(((float)(points * 25400)) / ((float)72));
    }
    public static int[] p2m(float[] points) {
        int[] micrometers = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            micrometers[i] = p2m(points[i]);
        }
        return micrometers;
    }
}
