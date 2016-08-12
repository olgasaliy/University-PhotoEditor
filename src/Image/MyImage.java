package Image;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by olgasaliy on 05.05.16.
 * Interface implemented by the PixelImage and representing its services.
 * The interface must be implemented by the proxy - PixelImageProxy..
 */
public interface MyImage {
    PixelImage display(JLabel my_label) throws IOException;
}
