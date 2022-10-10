public class Cross {
    Point mP;
    boolean mOk;

    Cross(){
        mP = new Point();
        mOk = false;
    }

    Cross (Point iP, boolean iOk) {
        mP = new Point(iP);
        mOk = iOk;
    }
}
