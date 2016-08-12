package UI;

/**
 * Created by olgasaliy on 30.04.16.
 */
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class ImageTransferHandler extends TransferHandler {
    private static final DataFlavor FILE_FLAVOR = DataFlavor.javaFileListFlavor;

    private GUI GUI;

    public ImageTransferHandler (GUI gui) {
         GUI = gui;

    }
    /**
     * The method to handle the transfer between the source of data and the
     * destination, which is in our case the label in GUI class.
     */
    public boolean importData(JComponent c, Transferable t) {
        if (canImport(c, t.getTransferDataFlavors())) {
            if (transferFlavor(t.getTransferDataFlavors(), FILE_FLAVOR)) {
                try {
                    List<File> fileList = (List<File>) t.getTransferData(FILE_FLAVOR);
                    if (fileList != null && fileList.toArray() instanceof File[]) {
                        File[] files = (File[]) fileList.toArray();
                        GUI.addFiles(files);
                    }
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedFlavorException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }



    /**
     * Returns the type of transfer actions to be supported.
     */
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    /**
     * Specifies the actions to be performed after the data has been exported.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Returns true if the specified flavor is contained in the flavors array,
     * false otherwise.
     */
    private boolean transferFlavor(DataFlavor[] flavors, DataFlavor flavor) {
        boolean found = false;
        for (int i = 0; i < flavors.length && !found; i++) {
            found = flavors[i].equals(flavor);
        }
        return found;
    }

    /**
     * Returns true if the component can import the specified flavours, false
     * otherwise.
     */
    public boolean canImport(JComponent c, DataFlavor[] flavors) {
        for (int i = 0; i < flavors.length; i++) {
            if (FILE_FLAVOR.equals(flavors[i])) {
                return true;
            }
        }
        return false;
    }
}

