package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label helloLabel;

    @FXML
    private void onHelloButtonClick() {
        helloLabel.setText("You clicked the button!");
    }
}
