package ua.vitalik.hardwareinfoapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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
    private AnchorPane pane;

    @FXML
    public void initialize() throws SQLException, IOException {

        PCInfo information = new PCInfo();

        String cpu_name = information.getCPUInfo();
        ArrayList<String> gpu_name = information.getGPUInfo();
        String ram_size = String.valueOf(information.getRAMInfo());
        String windows_ver = information.getWindowsVersion();

        ObservableList<String> obs_gpu_list = FXCollections.observableArrayList(gpu_name);

        cpu_info_label.setText(cpu_name);
        gpu_info_label.setItems(obs_gpu_list);
        gpu_info_label.getSelectionModel().select(0);
        ram_info_label.setText(ram_size + " GB");
        windows_info_label.setText(windows_ver);

        cpu_image.setImage(new Image(new FileInputStream("src/main/resources/images/cpu.png")));
        gpu_image.setImage(new Image(new FileInputStream("src/main/resources/images/graphics-card.png")));
        ram_image.setImage(new Image(new FileInputStream("src/main/resources/images/ram-memory.png")));
        windows_image.setImage(new Image(new FileInputStream("src/main/resources/images/windows.png")));

    }

}