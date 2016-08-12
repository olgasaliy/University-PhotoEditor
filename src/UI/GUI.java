package UI;

import java.awt.*;
import java.awt.event.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Command.ConcreteCommand;
import Command.Command;
import Command.Invoker;
import Image.PixelImage;
import Image.PixelImageProxy;
import Strategy.Context;
import Strategy.Filter;
import filters.ContrastFilter;
import filters.GrayFilter;
import filters.InvertFilter;
import filters.*;

/**
 * Created by olgasaliy on 01.05.16.
 * The main class of the whole project.
 */

public class GUI
{


    /**
     * Context for applying filters
     */
    private Context context = new Context();
    /**
     * A list of the filter buttons.
     */
    private final List<JButton> FILTER_BUTTONS = new ArrayList<JButton>();

    /**
     * Apply button
     */
    private JButton apply = new JButton("Apply");
    /**
     * Button "Original"
     */
    private JButton original = new JButton("Original");
    /**
     * The frame for this application's UI.
     */
    public static final JFrame my_frame = new JFrame();

    /**
     * The label for this application's UI that stores an image.
     */
    private final JLabel my_label = new JLabel("", JLabel.CENTER);

    /**
     * The file chooser this UI uses.
     */
    private final JFileChooser my_file_chooser = new JFileChooser("./images");
    /**
     * Array of menu items
     */
    private List<JMenuItem> items = new ArrayList<>();
    /**
     * Button to clean
     */
    private JMenuItem cleanMenuItem;
    /**
     * Button to open a photo
     */
    private JMenuItem openMenuItem;
    /**
     * Button to save the photo
     */
    private JMenuItem saveMenuItem;
    /**
     * Top part of the UI.
     */
    private final JPanel my_panel1 = new JPanel();
    /**
     * Bottom part of the UI.
     */
    private final JPanel my_panel2 = new JPanel();
    /**
     * The original image without any filters
     */
    private PixelImage main_image;
    /**
     * The main image that is selected and edited by the UI.
     */
    private PixelImage my_image;

    /**
     * MyImage which was cloned from my_image after each filter selection.
     */
    private PixelImage cloned;
    /**
     * The default icon
     */
    private BufferedImage defaultIcon;
    /**
     * The Menu Bar
     */
    private JMenuBar menuBar;
    /**
     * Array of commands
     */
    private List<Command> filtersCommands = new ArrayList<>();
    /**
     * Invoker
     */
    private Invoker invoker;
    /**
     * MyImage transfer
     */
    private ImageTransferHandler my_imgTransf;
    public PixelImage my_image1;
    /**
     * Sets up and displays the UI for this application.
     */
    public void start() throws CloneNotSupportedException
    {
        my_frame.setTitle("Photo Editor by SaliyPhoto");
        my_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        my_frame.setVisible(true);
        my_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        my_label.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Dimension size = my_label.getSize();
                if(e.getX()>my_image.getWidth()-20 && e.getX()>my_image.getHeight()-20 ) {
                    System.out.println("inside");
                    PixelImage temp = null;
                    try {
                       temp = (PixelImage) my_image1.Clone();
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    int width = (int)(size.getWidth() - temp.getWidth()), height = (int)(size.getHeight() - temp.getHeight());
                    my_image = my_image.resizeImage(temp, e.getX() - width/2, e.getY() - height/2);
                    my_label.setIcon(new ImageIcon(my_image));
                    my_label.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });



        addMenuBar();
        addButtonsTop();
        setDefaultIcon();
        addButtonsBottom();
        createButtonApply();
        imageTransfer();

        my_frame.pack();
    }

    /**
     * Add files by transfer handler
     */
    public void addFiles (File [] files) {
        if (my_label.getIcon()!=defaultIcon) {
            clearImage cl = new clearImage();
            cl.actionPerformed(null);
        }
        Image image = null;
        File f = null;
        for (File file : files) {
            try {
                image = ImageIO.read(file);
                f = file;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (image != null) {
            try {
                //my_image = PixelImage.load(f);
                setUp(f.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * MyImage transfer handler
     */
    public void imageTransfer () {
        my_imgTransf = new ImageTransferHandler(this);
        my_label.setTransferHandler(my_imgTransf);
    }

    /**
     * Initial method to set commands
     */
    public void setFiltersCommands () {

        filtersCommands.add(new ConcreteCommand(new EdgeDetectFilter()));
        filtersCommands.add(new ConcreteCommand(new EdgeHighlightFilter()));
        filtersCommands.add(new ConcreteCommand(new FlipHorizontalFilter()));
        filtersCommands.add(new ConcreteCommand(new FlipVerticalFilter()));
        filtersCommands.add(new ConcreteCommand(new BlackandwhiteFilter()));
        filtersCommands.add(new ConcreteCommand(new SharpenFilter()));
        filtersCommands.add(new ConcreteCommand(new SoftenFilter()));
        filtersCommands.add(new ConcreteCommand(new ContrastFilter()));
        filtersCommands.add(new ConcreteCommand(new GrayFilter()));
        filtersCommands.add(new ConcreteCommand(new InvertFilter()));
        filtersCommands.add(new ConcreteCommand(new SepiaFilter()));
    }

    /**
     * Method to set default icon at the beginning
     */
    public void setDefaultIcon () {
        try {
            defaultIcon = ImageIO.read(new File("Photoeditor.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        my_label.setIcon(new ImageIcon(defaultIcon));
        my_frame.add(my_label, BorderLayout.CENTER);
        my_frame.pack();
        my_label.setEnabled(true);
    }
    /**
     * This method creates menu bar and adds it to JFrame.
     */
    private void addMenuBar (){
        //create a menu bar
        menuBar = new JMenuBar();

        //create menus
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        //create menu items
        cleanMenuItem = new JMenuItem("Clean");
        cleanMenuItem.setAccelerator(KeyStroke.getKeyStroke('C',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        cleanMenuItem.setActionCommand("Clean");

        openMenuItem = new JMenuItem("Open");
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        openMenuItem.setActionCommand("Open");

        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        saveMenuItem.setActionCommand("Save");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke('E',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        exitMenuItem.setActionCommand("Exit");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        undoMenuItem.setActionCommand("Undo");

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke('Y',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        redoMenuItem.setActionCommand("Redo");

        JMenuItem rotateMenuItem = new JMenuItem("Rotate");
        rotateMenuItem.setAccelerator(KeyStroke.getKeyStroke('R',
                Toolkit.getDefaultToolkit(  ).getMenuShortcutKeyMask(  ), false));
        rotateMenuItem.setActionCommand("Rotate");




        items.add(cleanMenuItem);
        items.add(openMenuItem);
        items.add(saveMenuItem);
        items.add(exitMenuItem);
        items.add(undoMenuItem);
        items.add(redoMenuItem);
        items.add(rotateMenuItem);

        cleanMenuItem.addActionListener(new clearImage());
        openMenuItem.addActionListener(new openImage());
        saveMenuItem.addActionListener(new saveImage());
        exitMenuItem.addActionListener(new exitApp());
        undoMenuItem.addActionListener(new UndoEdit());
        redoMenuItem.addActionListener(new RedoEdit());
        rotateMenuItem.addActionListener(new RotateEdit());


        //add menu items to menus
        fileMenu.add(cleanMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.add(rotateMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        //add menubar to the frame
        my_frame.setJMenuBar(menuBar);

        cleanMenuItem.setEnabled(false);
        saveMenuItem.setEnabled(false);
        undoMenuItem.setEnabled(false);
        redoMenuItem.setEnabled(false);
        rotateMenuItem.setEnabled(false);

    }

    /**
     * Method to do rotate
     */
    class RotateEdit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PixelImage temp = null;
            try {
                temp= (PixelImage) my_image.Clone();
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }
            my_image = my_image.rotate(my_image.getWidth(),my_image.getHeight());
            my_image.rotated= my_image.rotated?false:true;
            my_label.setIcon(new ImageIcon(my_image));
            my_label.repaint();
        }

    }

    /**
     * Method to do undo step
     */
    class UndoEdit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            invoker.runToPrevious(my_image);
            if (my_label.getIcon()!=new ImageIcon(my_image))
                my_label.setIcon(new ImageIcon(my_image));
            my_label.repaint();
            apply.setEnabled(false);

            //check availability of undo and redo items
            if (!invoker.isPossibleRedo())
                items.get(5).setEnabled(false);
            else items.get(5).setEnabled(true);
            if (!invoker.isPossibleUndo())
                items.get(4).setEnabled(false);
            else items.get(4).setEnabled(true);

        }

    }

    /**
     * Method to do redo step
     */
    class RedoEdit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            invoker.runToNext(my_image);
            if (my_label.getIcon()!=new ImageIcon(my_image))
                my_label.setIcon(new ImageIcon(my_image));
            my_label.repaint();
            apply.setEnabled(false);

            //check availability of undo and redo items
            if (!invoker.isPossibleUndo())
                items.get(4).setEnabled(false);
            else items.get(4).setEnabled(true);
            if (!invoker.isPossibleRedo())
                items.get(5).setEnabled(false);
            else items.get(5).setEnabled(true);
        }

    }

    /**
     * Method to save the photo
     */
    class saveImage implements ActionListener {




        public void actionPerformed(ActionEvent e) {
            RotateEdit re = new RotateEdit();
            PixelImage temp = null;
            temp = invoker.doAll(temp);
            if (my_image.rotated==true)
                temp = temp.rotate(main_image.getWidth(),main_image.getHeight());
            my_file_chooser.showSaveDialog(null);
            try {
                temp.save(my_file_chooser.getSelectedFile());
            }
            catch (final IOException ex) {
                ex.getMessage();
            }
            clearImage cl = new clearImage();
            cl.actionPerformed(e);
        }

    }

    /**
     * Method to clean the photo
     */
    class clearImage implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            my_label.setText(null);

            for (int i = 0; i < FILTER_BUTTONS.size(); i++)
            {
                FILTER_BUTTONS.get(i).setEnabled(false);

            }
            original.setEnabled(false);
            items.get(0).setEnabled(false);
            items.get(2).setEnabled(false);
            items.get(4).setEnabled(false);
            items.get(5).setEnabled(false);
            items.get(6).setEnabled(false);
            apply.setEnabled(false);
            my_label.setIcon(null);
            my_label.setIcon(new ImageIcon(defaultIcon));

            filtersCommands.removeAll(filtersCommands);
            my_frame.pack();
            my_frame.repaint();
        }

    }

    class openImage implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (my_label.getIcon()!=defaultIcon) {
                clearImage cl = new clearImage();
                cl.actionPerformed(e);
            }
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
            my_file_chooser.setFileFilter(filter);
            my_file_chooser.setAcceptAllFileFilterUsed(false);
            my_file_chooser.showOpenDialog(null);
            try {
                setUp(my_file_chooser.getSelectedFile().toString());

            }
            catch (final IOException ex) {
                ex.getMessage();
            }



        }
    }

    /**
     * Method to set the proxy and to set needed buttons and menu items enabled.
     */
    public  void setUp(String file) throws IOException {

        PixelImageProxy temp = new PixelImageProxy(file);
        my_image = temp.display(my_label);

                items.get(0).setEnabled(true);
                items.get(2).setEnabled(true);
                items.get(6).setEnabled(true);

                original.setEnabled(true);
                for (int i = 0; i < FILTER_BUTTONS.size(); i++) {
                    FILTER_BUTTONS.get(i).setEnabled(true);
                }

                //getting original image for commands
                try {
                    my_image1 = (PixelImage) my_image.Clone();
                    main_image = (PixelImage) my_image.Clone();
                    invoker = new Invoker(main_image);
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
                //set the commands
                setFiltersCommands();





    }



    /**
     * Method to exit app
     */
    class exitApp implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }


    /**
     * This method creates the bottom buttons that go on the GUI.
     */
    private void addButtonsBottom()
    {
        my_panel2.add(apply);
        my_frame.add(my_panel2, BorderLayout.SOUTH);
        my_frame.pack();
    }
    /**
     * Method to create button apply
     */
    private void createButtonApply()
    {
        apply.addActionListener(new ActionListener()
        {
            /**
             * Handles an ActionEvent for the apply button.
             *
             * @param the_event The event.
             */
            public void actionPerformed(final ActionEvent the_event)
            {
                //apply
                my_image = cloned;
                cloned = null;
                my_label.setIcon(new ImageIcon(my_image));
                my_label.repaint();
                for (Command com: filtersCommands) {
                    if (com.getFilt().getClass() == context.getStrategy().getClass())
                        invoker.addCommand(com);
                }
                //check availability of undo and redo items
                if (!invoker.isPossibleRedo())
                    items.get(5).setEnabled(false);
                else items.get(5).setEnabled(true);
                if (!invoker.isPossibleUndo())
                    items.get(4).setEnabled(false);
                else items.get(4).setEnabled(true);
                apply.setEnabled(false);
            }
        });
        apply.setEnabled(false);
    }
    /**
     * This method creates the bottom buttons that go on the GUI.
     */
    private void addButtonsTop() throws CloneNotSupportedException
    {
        createButtonOriginal();
        createButtonFilter(new ContrastFilter());
        createButtonFilter(new GrayFilter());
        createButtonFilter(new FlipHorizontalFilter());
        createButtonFilter(new FlipVerticalFilter());
        createButtonFilter(new BlackandwhiteFilter());
        createButtonFilter(new SharpenFilter());
        createButtonFilter(new SoftenFilter());
        createButtonFilter(new EdgeDetectFilter());
        createButtonFilter(new EdgeHighlightFilter());
        createButtonFilter(new InvertFilter());
        createButtonFilter(new SepiaFilter());


        my_panel1.add(original);
        for (int i = 0; i < FILTER_BUTTONS.size(); i++)
        {
            my_panel1.add(FILTER_BUTTONS.get(i));
        }

        JScrollPane scroll = new JScrollPane(my_panel1);
        Dimension size = new Dimension();
        size.setSize(800, 120);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(size);
        my_frame.getContentPane().add(scroll,BorderLayout.NORTH);
        my_frame.pack();
    }

    private void createButtonOriginal() throws CloneNotSupportedException
    {

        PixelImage temp = null, temp1 = null;
        try {
            temp = PixelImage.load(new File("original.png"));// eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        temp1 = temp.resizeImage(temp,100,70);
        original.setIcon(new ImageIcon(temp1));
        original.setHorizontalTextPosition(SwingConstants.CENTER);
        original.setVerticalTextPosition(SwingConstants.TOP);

        //button.setSize(50,50);
        original.addActionListener(new ActionListener()
        {
            /**
             * Handles an ActionEvent for button Original.
             *
             * @param the_event The event.
             */
            public void actionPerformed(final ActionEvent the_event)
            {
                    apply.setEnabled(false);
                    my_label.setIcon(new ImageIcon(main_image));
                    my_label.repaint();
            }
        });
        original.setEnabled(false);
    }



    /**
     * This method creates the filters buttons for the program.
     *
     * @param the_object the filter object.
     */
    private void createButtonFilter(final Filter the_object) throws CloneNotSupportedException
    {
        JButton button = new JButton(the_object.getDescription());

        PixelImage temp = null, temp1 = null;
        try {
            temp = PixelImage.load(new File(the_object.getFileName()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        temp1 = temp.resizeImage(temp,100,70);
        button.setIcon(new ImageIcon(temp1));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.TOP);
        FILTER_BUTTONS.add(button);
        button.addActionListener(new ActionListener()
        {
            /**
             * Handles an ActionEvent for the filter buttons.
             *
             * @param the_event The event.
             */
            public void actionPerformed(final ActionEvent the_event)
            {
                try {
                    context.setStrategy(the_object);
                    cloned = (PixelImage) my_image.Clone();
                    context.execute(cloned);
                    my_label.setIcon(new ImageIcon(cloned));
                    my_label.repaint();
                    apply.setEnabled(true);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
        button.setEnabled(false);
    }


}
