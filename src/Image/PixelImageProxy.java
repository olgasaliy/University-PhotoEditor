package Image;


import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by olgasaliy on 05.05.16.
 * PixelImageProxy is a proxy (or surrogate) for an PixelImage.
 * The proxy delays setting icon to label until load the real image.
 * While the the real image is loading, the proxy prints the message "Loading..."
 */
public class PixelImageProxy implements MyImage {

    private PixelImage real_image = null;
    private String filename;

    /**
     * Constructor
     * @param file - filename of real image
     */
    public PixelImageProxy (String file) {
        this.filename = file;
    }

    /**
     * Method to display the image
     * @param my_label - a label which displays a picture
     * @return PixelImage to set in GUI class my_image
     * @throws IOException
     */
    @Override
    public PixelImage display(JLabel my_label) throws IOException {
        if(real_image!=null) {
            // modify the image
            real_image = real_image.display(my_label);
        }
        else {
            my_label.setText("Loading...");
            synchronized(this) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            try {
                                // to delay setting icon
                                Thread.currentThread().sleep(2000);
                                my_label.setText(null);
                                my_label.setIcon(null);
                                my_label.setIcon(new ImageIcon(real_image));

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                    }
                });
            }
            //load the real image
            real_image = PixelImage.load(new File(filename));
            try {
                //to cause recursion
                display(my_label);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return real_image;
    }

}
