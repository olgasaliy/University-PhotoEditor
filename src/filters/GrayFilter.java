package filters;

import Image.Pixel;
import Image.PixelImage;
import filters.AbstractFilter;

import java.awt.image.BufferedImage;

/**
 * Created by olgasaliy on 28.04.16.
 * These point operations make colors of the image more gray.
 * But not black and white.
 */
public class GrayFilter extends AbstractFilter{

    public GrayFilter() {
        super("Gray");
    }

    public String nameFile = "gray.png";

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
                int r = (the_image.getRGB(v,u) >> 16) & 0xff;
                int g = (the_image.getRGB(v,u) >> 8) & 0xff;
                int b = the_image.getRGB(v,u) & 0xff;
                r = (r+255)/2;
                g = (g+255)/2;
                b = (b+255)/2;
                newImg.setRGB(v,u, a | (r << 16) | (g << 8) | b);

            }
        }

    }
}
