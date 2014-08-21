import com.google.caliper.Benchmark;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yawkat
 */
public class Reflect {
    private static final Implementation IMPLEMENTATION = new Implementation();
    private static final Method METHOD;

    static {
        try {
            METHOD = IMPLEMENTATION.getClass().getMethod("test");

            int r = 0;
            for (int i = 0; i < 1000000; i++) {
                r |= (Integer) METHOD.invoke(IMPLEMENTATION);
            }
            System.out.println(r);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public int reflect(long reps) throws InvocationTargetException, IllegalAccessException {
        int r = 0;
        for (long i = 0; i < reps; i++) {
            r |= (Integer) METHOD.invoke(IMPLEMENTATION);
        }
        return r;
    }

    @Benchmark
    public int invokeI(long reps) {
        int r = 0;
        for (long i = 0; i < reps; i++) {
            r |= IMPLEMENTATION.test();
        }
        return r;
    }

    private static interface Interface {
        Integer test();
    }

    private static class Implementation implements Interface {
        @Override
        public Integer test() {
            return 0;
        }
    }
}
