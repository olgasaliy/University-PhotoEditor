package Command;

import Image.PixelImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Pack200;

/**
 * Created by olgasaliy on 30.04.16.
 */
public class Invoker {
    private final PixelImage original;

    private ArrayList<Command> commands = new ArrayList<>();

    private static int current;

    public Invoker (PixelImage orig){
        original = orig;
        current = -1;
    }

    public void addCommand (Command com) {
        current++;
        int s = commands.size();
        if (commands.size()!=0 && commands.size() - 1>=current)
            for (int i = s-1; i >= current; i--) {
                commands.remove(i);
            }
        commands.add(current, com);

    }

    public void runToPrevious (PixelImage image){
        image.setPixelData(original.getPixelData());
        try {
            if (!isPossibleUndo())
                throw new IndexOutOfBoundsException();
            for (int i = 0; i < current; i++) {
                commands.get(i).execute(image);
            }
            current--;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runToNext (PixelImage image) {
        image.setPixelData(original.getPixelData());
        try {
            if (!isPossibleRedo())
                throw new IndexOutOfBoundsException();
            for (int i = 0; i <= current + 1; i++) {
                commands.get(i).execute(image);
            }
            current++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PixelImage doAll (PixelImage image) {
        image = original;
        try {
            for (int i = 0; i < commands.size(); i++) {
                commands.get(i).execute(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public boolean isPossibleUndo () {
        return !(current < 0 || current >= commands.size());
    }

    public boolean isPossibleRedo () {
        return !(current >= commands.size() - 1 || current < -1);
    }

}
