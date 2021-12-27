package ua.vitalik.hardwareinfoapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindowController {
    @FXML
    private Label cpu_info_label;

    @FXML
    private ComboBox<String> gpu_info_label;

    @FXML
    private Label ram_info_label;

    @FXML
    private Label windows_info_label;

    @FXML
    private ImageView ram_image;

    @FXML
    private ImageView windows_image;

    @FXML
    private ImageView cpu_image;

    @FXML
    private ImageView gpu_image;


    @FXML
    public void initialize() throws SQLException, IOException {

        String cpu_name = PCInfo.getCPUInfo();
        ArrayList<String> gpu_name = PCInfo.getGPUInfo();
        String ram_size = String.valueOf(PCInfo.getRAMInfo());
        String windows_ver = PCInfo.getWindowsVersion();

        ObservableList<String> obs_gpu_list = FXCollections.observableArrayList(gpu_name);

        cpu_info_label.setText(cpu_name);
        gpu_info_label.setItems(obs_gpu_list);
        gpu_info_label.getSelectionModel().select(0);
        ram_info_label.setText(ram_size + " GB");
        windows_info_label.setText(windows_ver);

        cpu_image.setImage(createNewImage("src/main/resources/images/cpu.png"));
        gpu_image.setImage(createNewImage("src/main/resources/images/graphics-card.png"));
        ram_image.setImage(createNewImage("src/main/resources/images/ram-memory.png"));
        windows_image.setImage(createNewImage("src/main/resources/images/windows.png"));

    }

    private Image createNewImage (String fileLocation) throws FileNotFoundException {
        return new Image(new FileInputStream(fileLocation));
    }

}