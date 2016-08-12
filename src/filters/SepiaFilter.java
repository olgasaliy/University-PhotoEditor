package filters;

import Image.Pixel;
import Image.PixelImage;

import java.awt.image.BufferedImage;

/**
 * Created by olgasaliy on 05.05.16.
 * The filter makes an image look like an old brown photo
 */
public class SepiaFilter extends AbstractFilter {
    public SepiaFilter() {
        super("Sepia");
    }

    public String nameFile = "sepia.png";

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
        Pixel pixels[][] = the_image.getPixelData();
        int newR = 0, newG = 0, newB = 0;

        for (int v = 0; v < pixels.length; v++) {
            for (int u = 0; u < pixels[v].length; u++) {
                newR = (int)((pixels[v][u].getRed() * .393) + (pixels[v][u].getGreen() *.769) + (pixels[v][u].getBlue() * .189));
                if (newR > 255)
                    newR = 255;
                newG= (int)((pixels[v][u].getRed() * .349) + (pixels[v][u].getGreen() *.686) + (pixels[v][u].getBlue() * .168));
                if (newG > 255)
                    newG = 255;
                newB = (int)((pixels[v][u].getRed() * .272) + (pixels[v][u].getGreen() *.534) + (pixels[v][u].getBlue() * .131));
                if (newB > 255)
                    newB = 255;
                pixels[v][u].setBlue(newB);
                pixels[v][u].setGreen(newG);
                pixels[v][u].setRed(newR);

            }
        }
        the_image.setPixelData(pixels);

    }
}
