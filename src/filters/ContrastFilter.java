package filters;

import Image.Pixel;
import Image.PixelImage;

/**
 * Created by olgasaliy on 28.04.16.
 * This class was created for represent the contrast filter.
 * It makes photo more contrast.
 */
public class ContrastFilter extends AbstractFilter {

    public ContrastFilter() {
        super("Contrast");
    }

    public String nameFile = "contrast.png";

    public String getFileName() {
        return  nameFile;
    }

    /**
     * * Method to modify the image by concrete filter
     * @param the_image The image.
     */
    @Override
    public void filter(PixelImage the_image) {

        Pixel pixels[][] = the_image.getPixelData();
        int blue, green, red;
        for (int v = 0; v < pixels.length; v++) {
            for (int u = 0; u < pixels[v].length; u++) {
                blue = (int) (pixels[v][u].getBlue() * 1.1 - 10);
                if (blue > 255)
                    blue = 255;
                else if (blue < 0)
                    blue = 0;
                pixels[v][u].setBlue(blue);
                red = (int) (pixels[v][u].getRed() * 1.1 - 10);
                if (red > 255)
                    red = 255;
                else if (red < 0)
                    red = 0;
                pixels[v][u].setRed(red);
                green = (int) (pixels[v][u].getGreen() * 1.1 - 10);
                if (green > 255)
                    green = 255;
                else if (green < 0)
                    green = 0;
                pixels[v][u].setGreen(green);
            }
        }
        the_image.setPixelData(pixels);
    }
}
