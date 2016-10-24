package com;

import javafx.collections.ModifiableObservableListBase;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FileList extends ModifiableObservableListBase<GridPane> {

    private final ArrayList<GridPane> list = new ArrayList<GridPane>(); // Internal list. Do not modify directly
    private final FileSystemView fileSystem = FileSystemView.getFileSystemView();

    private enum GridPos {
        ICON,
        NAME,
        SIZE,
        DATE

    }

    public void update(File[] files) {
        this.clear();
        addFile(files);
    }

    public void addFile(File... files) {
        for (File file : files) {
            GridPane grid = new GridPane();
            grid.setHgap(10d);

            Label name = new Label();
            if (file.getName().isEmpty()) {
                name.setText(file.getAbsolutePath());
                name.setUserData(file.getAbsolutePath());
                Label size = new Label(String.valueOf((file.getFreeSpace()) / 1000000) + " Mb free");
                grid.add(size, GridPos.SIZE.ordinal(), 0);
            } else {
                name.setText(file.getName());
                name.setUserData(file.getAbsolutePath());
                Label size = new Label(String.valueOf((file.getTotalSpace() - file.getFreeSpace()) / 1000000) + " Mb");
                grid.add(size, GridPos.SIZE.ordinal(), 0);

            }
            grid.add(name, GridPos.NAME.ordinal(), 0);

            final Icon swingIcon = fileSystem.getSystemIcon(file);
            BufferedImage bufferedImage = new BufferedImage(swingIcon.getIconWidth(), swingIcon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            swingIcon.paintIcon(null, graphics, 0, 0);
            graphics.dispose();

            final Image systemIcon = SwingFXUtils.toFXImage(bufferedImage, null);
            grid.add(new ImageView(systemIcon), GridPos.ICON.ordinal(), 0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:s");
            Label date = new Label(dateFormat.format(new Date(file.lastModified())));
            grid.add(date, GridPos.DATE.ordinal(), 0);
            this.add(grid);
        }
    }

    public String getFilePath(GridPane pane) {
        Label name = (Label) pane.getChildren().get(1);
        return (String) name.getUserData();
    }

    @Override
    public GridPane get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void doAdd(int index, GridPane element) {
        list.add(index, element);
    }

    @Override
    protected GridPane doSet(int index, GridPane element) {
        return list.set(index, element);
    }

    @Override
    protected GridPane doRemove(int index) {
        return list.remove(index);
    }
}
