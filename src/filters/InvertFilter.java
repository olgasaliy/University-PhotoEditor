package filters;

import Image.Pixel;
import Image.PixelImage;


import java.awt.image.BufferedImage;

/**
 * Created by olgasaliy on 01.05.16.
 * This filter inverts all the pixels in an image, converting it into its photographic negative.
 */
public class InvertFilter extends AbstractFilter {

    public InvertFilter () {
        super("Invert");
    }

    public String nameFile = "invert.png";

    public String getFileName() {
        return  nameFile;
    }

    /**
     * Method to modify the image by concrete filter
     * @param the_image The image.
     */
    @Override
    public void filter(PixelImage the_image) {
        BufferedImage newImg = the_image;
        for (int v = 0; v < the_image.getWidth(); v++) {
            for (int u = 0; u < the_image.getHeight(); u++) {
                int a = the_image.getRGB(v,u) & 0xff000000;
                newImg.setRGB(v,u,a | (~the_image.getRGB(v,u) & 0x00ffffff));
            }
        }

    }
}
