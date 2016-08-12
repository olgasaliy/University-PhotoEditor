package Image;

/**
 * Created by olgasaliy on 30.04.16.
 * This interface declares an interface for cloning itself.
 * Involved in prototype pattern.
 */
public interface Prototype {
    Prototype Clone() throws CloneNotSupportedException;
}
