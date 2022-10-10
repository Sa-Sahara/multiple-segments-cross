public class coordsTransform {
    private float[] src;
    private float[] dst;

    public coordsTransform(float[] iInitial, float[] iTransformed) {
        int length = iInitial.length;

        src = new float[length];
        dst = new float[length];
        for (int i = 0; i < length; i++) {
            src[i] = iInitial[i];
            dst[i] = iTransformed[i];
        }
    }

    /*
    * Use a linear equation to scale a number from src range to dst.
    * f[x] = k * x + d;
    * Then substitute the src and dst into the function to find
    * k and d.
    * dst[1] == k * dst[0] + d,   src[1] == k * src[0] + d
    * d == (dst[0] * src[1] - src[0] * dst[1]) / (src[1] - src[0])
    * k == (dst[1] - dst[0]) / (src[1] - src[0])
     */

    public float fit(float iF) {
        return ((dst[1] - dst[0]) * iF + dst[0] * src[1] - src[0] * dst[1])
                / (src[1] - src[0]);
    }
}
