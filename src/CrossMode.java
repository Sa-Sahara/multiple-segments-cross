enum CrossMode {
    SINGLE,
    MULTIPLE;

    public static boolean contains(String iStr) {
        boolean bool = false;
        CrossMode[] modes = CrossMode.values();
        for (CrossMode mode : modes) {
            if (mode.name().equals(iStr)) {
                bool = true;
                break;
            }
        }
        return bool;
    }
}