import com.google.caliper.Benchmark;
import java.util.Random;

public class BenchMath {
    private static float[] a = new float[65536];
    private static double[] b = new double[65536];

    private static final float[] randomFloats;
    private static final double[] randomDoubles;

    private static final int RANDOM_COUNT_BITS = 20;
    private static final int RANDOM_COUNT = 1 << RANDOM_COUNT_BITS;
    private static final int RANDOM_COUNT_MASK = RANDOM_COUNT - 1;

    static {
        System.out.println("Mem: " + Runtime.getRuntime().totalMemory());
        Random random = new Random();
        randomDoubles = new double[RANDOM_COUNT];
        randomFloats = new float[RANDOM_COUNT];
        for (int i = 0; i < RANDOM_COUNT; i++) {
            double v = random.nextDouble() * Math.PI * 2;
            randomFloats[i] = (float) (randomDoubles[i] = v);
        }
    }

    @Benchmark
    public float mcFloat(int reps) {
        float r = 0;
        for (int i = 0; i < reps; i++) {
            r += sin(randomFloats[i & RANDOM_COUNT_MASK]);
        }
        return r;
    }

    @Benchmark
    public float jdkFloat(int reps) {
        float r = 0;
        for (int i = 0; i < reps; i++) {
            r += (float) Math.sin(randomFloats[i & RANDOM_COUNT_MASK]);
        }
        return r;
    }

    @Benchmark
    public double mcDouble(int reps) {
        double r = 0;
        for (int i = 0; i < reps; i++) {
            r += sind(randomDoubles[i & RANDOM_COUNT_MASK]);
        }
        return r;
    }

    @Benchmark
    public double jdkDouble(int reps) {
        double r = 0;
        for (int i = 0; i < reps; i++) {
            r += Math.sin(randomDoubles[i & RANDOM_COUNT_MASK]);
        }
        return r;
    }

    public static float sin(float f) {
        return a[(int) (f * 10430.378F) & 0xFFFF];
    }

    public static double sind(double f) {
        return b[(int) (f * 10430.378F) & 0xFFFF];
    }

    static {
        for (int i = 0; i < 65536; ++i) {
            a[i] = (float) Math.sin((double) i * 3.141592653589793D * 2.0D / 65536.0D);
            b[i] = Math.sin((double) i * 3.141592653589793D * 2.0D / 65536.0D);
        }
    }
}