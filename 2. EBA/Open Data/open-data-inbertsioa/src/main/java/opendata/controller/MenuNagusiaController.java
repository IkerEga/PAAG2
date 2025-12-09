package opendata.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import opendata.App;

public class MenuNagusiaController {

    @FXML
    private void joanInflaziora() throws IOException {
        App.setRoot("inflazioa");
    }

    @FXML
    private void joanInbertsiora() throws IOException {
        App.setRoot("inbertsioa");
    }
}
