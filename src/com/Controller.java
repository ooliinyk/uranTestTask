package com;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public ListView<GridPane> fileList;
    public Button back;
    public TextField addressBar;
    private Explorer explorer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        explorer = new Explorer(fileList, addressBar);
        fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    GridPane grid = fileList.getSelectionModel().getSelectedItem();
                    explorer.onClick(grid);
                }
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                explorer.onBack();
            }
        });

        addressBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                explorer.openFolder(addressBar.getText());
            }
        });
    }
}
