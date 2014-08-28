package ua.samosfator.gmm.mapcamp.lviv.gui;

import com.google.gdata.util.ServiceException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import ua.samosfator.gmm.mapcamp.lviv.CheckEditStatus;
import ua.samosfator.gmm.mapcamp.lviv.Config;
import ua.samosfator.gmm.mapcamp.lviv.SplitName;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class Controller implements Initializable {
    @FXML
    private TextField gmail;
    @FXML
    private TextField password;

    @FXML
    private TextField originalCSV;
    @FXML
    private TextField editedCSV;

    @FXML
    private TextField editLinkColumn;
    @FXML
    private TextField editStatusColumn;
    @FXML
    private TextField spreadsheetLink;
    @FXML
    private TextField fromRange;
    @FXML
    private TextField toRange;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private void saveCredentials() {
        Config.preferences.put("gmail", gmail.getText());
        Config.preferences.put("password", password.getText());
    }

    @FXML
    private void splitNames() throws IOException {
        String originalCsvName = originalCSV.getText();
        String editedCsvName = editedCSV.getText();
        Config.preferences.put("originalCSV", originalCsvName);
        Config.preferences.put("editedCSV", editedCsvName);
        SplitName.splitGasStationsList(originalCsvName, editedCsvName);
    }

    @FXML
    private void updateStatus() throws IOException, ServiceException {
        try {
            new CheckEditStatus().check();
        } catch (IOException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveInputs() {
        String sheetLinkBase = "https://spreadsheets.google.com/feeds/spreadsheets/";
        String sheetId = spreadsheetLink.getText().split("/")[5];
        Config.preferences.put("editLinkColumn", editLinkColumn.getText());
        Config.preferences.put("editStatusColumn", editStatusColumn.getText());
        Config.preferences.put("spreadsheetLink", sheetLinkBase + sheetId);
        Config.preferences.put("fromRange", fromRange.getText());
        Config.preferences.put("toRange", toRange.getText());
    }

    @FXML
    private void resetSettings() throws BackingStoreException {
        Config.preferences.clear();
        editLinkColumn.clear();
        editStatusColumn.clear();
        spreadsheetLink.clear();
        editedCSV.clear();
        originalCSV.clear();
        gmail.clear();
        password.clear();
        toRange.clear();
        fromRange.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restoreTextFields();
    }

    private void restoreTextFields() {
        gmail.setText(Config.preferences.get("gmail", ""));
        password.setText(Config.preferences.get("password", ""));
        originalCSV.setText(Config.preferences.get("originalCSV", ""));
        editedCSV.setText(Config.preferences.get("editedCSV", ""));
        editLinkColumn.setText(Config.preferences.get("editLinkColumn", ""));
        editStatusColumn.setText(Config.preferences.get("editStatusColumn", ""));
        spreadsheetLink.setText(Config.preferences.get("spreadsheetLink", ""));
        fromRange.setText(Config.preferences.get("fromRange", ""));
        toRange.setText(Config.preferences.get("toRange", ""));
    }
}
