package com;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class Explorer {
    private final FileSystemView fileSystem;
    private final TextField addressBar;
    private String lastDir;
    private String curDir;
    private final FileList fileList;

    public Explorer(ListView<GridPane> fileListView, TextField addressBar) {
        this.addressBar = addressBar;
        fileSystem = FileSystemView.getFileSystemView();
        fileList = new FileList();
        fileListView.setItems(fileList);
        displayRoot();
    }

    public void displayRoot() {
        lastDir = curDir;
        curDir = "Root";

        addressBar.setText("Root");
        fileList.update(File.listRoots());
    }

    public void openFolder(String dir) {
        lastDir = curDir;
        curDir = dir;

        updateAddressBar();
        fileList.update(getFiles(dir));
    }

    public void onBack() {
        final String text = addressBar.getText();
        final String[] split = text.split("\\\\");
        String path = text.substring(0, text.length() - split[split.length - 1].length());
        if (path.endsWith("\\")) path = path.substring(0, path.length() - 1);

        if (split.length <= 1) {
            displayRoot();
        } else {
            openFolder(path);
        }
    }

    public void onClick(GridPane pane) {
        openFolder(fileList.getFilePath(pane));
    }

    private File[] getFiles(String dir) {
        return fileSystem.getFiles(new File(dir), false);
    }

    private void updateAddressBar() {
        addressBar.setText(curDir);
    }
}
