public class coordsTransform {
    private float[] src = new float[2];
    private float[] dst = new float[2];

    public coordsTransform(float[] iInitial, float[] iTransformed) {
        src = iInitial;
        dst = iTransformed;
    }
    public float fit (float iF) {
        return ((dst[1] - dst[0]) * iF + dst[0] * src[1] - src[0] * dst[1])
                / (src[1] - src[0]);
    }
}
