package autolog;


public interface Logged {
    public default String getPath() {return "";};
}
