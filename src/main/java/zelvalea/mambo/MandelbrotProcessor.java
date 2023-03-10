package zelvalea.mambo;

import java.util.Map;

import static java.lang.Math.fma;
import static java.lang.Math.pow;
import static java.util.Map.entry;

public final class MandelbrotProcessor extends FrameMaker {
    private static final int TARGET_INDEX_PONT = 5;
    @SuppressWarnings("unchecked")
    private static final Map.Entry<Double, Double>[] points = new Map.Entry[]{
            entry(-1.1900443, 0.3043895),
            entry(-0.10109636384562, 0.95628651080914),
            entry(-0.77568377, 0.13646737),
            entry(-0.7778078101931, 0.1316451080032),
            entry(-0.743643887037151, 0.13182590420533),
            entry(-1.2573680284665283, 0.3787308310286249),
            entry(-1.256640565451168862869, -0.382386428889165027247)

    };
    private static final double CENTER_X, CENTER_Y;
    static {
        Map.Entry<Double,Double> entry = points[TARGET_INDEX_PONT];

        CENTER_X = entry.getKey();
        CENTER_Y = entry.getValue();
    }


    private static final int MAX_ITERATIONS = 500;
    private static final Palette PALETTE = new Palette(
            0, 255,
            Math.toRadians(30), Math.toRadians(60), Math.toRadians(360),
            4,4,4
    );

    private final int half_width, half_height;

    private double scale = 1.75;


    public MandelbrotProcessor(int width, int height) {
        super(width, height);
        this.half_width  =  width >>> 1;
        this.half_height = height >>> 1;
    }

    @Override
    public void render(int[] data) {

        super.render(data);

        scale *= 0.97;

    }

    @Override
    public int renderAt(int x, int y) {

        double zoom = scale;

        double d1 = y - half_width, d2 = x - half_height;

        // a * b + c
        double real = fma(zoom, d1, CENTER_X);
        double imag = fma(zoom, d2, CENTER_Y);

        double x1 = real, y1 = imag;


        double x_pow = 0, y_pow = 0;

        int itr;

        for (itr = 0; x_pow + y_pow <= 4 && itr < MAX_ITERATIONS; ++itr) {
            x_pow = pow(x1, 2);
            y_pow = pow(y1, 2);

            y1 = 2 * x1 * y1 + imag;
            x1 = x_pow - y_pow + real;
        }
        return PALETTE.getColor(itr).getRGB();
    }
}