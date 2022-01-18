package jd.datastructures2.worksheet1;

import eu.hansolo.tilesfx.tools.Pixel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private Button exitApplicationButton;
    @FXML private ImageView myimageView;
    @FXML private Button fileChoiceBoxSubmitButton;
    @FXML private Label fileOptionSelectedLabel;
    @FXML private Label infoLabel;
    @FXML private Label fileLabel;
    @FXML private ChoiceBox<String> fileChoiceBox;

    //Image Details
    @FXML private Label fileNameLabel;
    @FXML private Label fileSizeLabel;
    @FXML private Label filePathLabel;

    //Colour Manipulation
    @FXML private Slider hueSlider;

    private String[] fileOptions = {"Open", "Load", "Exit"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fileChoiceBox.getItems().addAll(fileOptions);
        fileChoiceBox.setOnAction(this::getFileOption); //this:: is a method reference operator
        fileChoiceBoxSubmitButton.setOnAction(this::chooseFileAndDisplay);

    }

    public void getFileOption(ActionEvent event) {
        String fileOption = fileChoiceBox.getValue();
        fileOptionSelectedLabel.setText("You Selected: " + fileOption);
    }

    public void chooseFileAndDisplay(ActionEvent event) {
        FileChooser openImageChooser = new FileChooser();
        openImageChooser.setTitle("Open Image");
//        openImageChooser.showOpenDialog(fileChoiceBoxSubmitButton.getScene().getWindow());

        openImageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files (.png, .jpg, .gif)", "*.png", "*.jpg", "*.gif"));

//        File selectedFile = openImageChooser.showOpenDialog(null);
        File selectedFile = openImageChooser.showOpenDialog(null);

        if (selectedFile != null) {
            //I currently have it that it displays the file name, but I want the actual image to display in the imageView
//            myImageView.setImage(selectedFile);
            fileNameLabel.setText("File Name: " + selectedFile.getName());
            fileSizeLabel.setText("File Size: " + selectedFile.length() + " bytes");
            filePathLabel.setText("File Path: " + selectedFile.getPath());

            Image image = new Image(selectedFile.toURI().toString());
            myimageView.setImage(image);
            myimageView.setFitHeight(200);
            myimageView.setFitWidth(200);
        }
    }

    @FXML
    public void exitApplicationAction(ActionEvent event) {
        Stage stage = (Stage) exitApplicationButton.getScene().getWindow();
        stage.close();
    }

    //Image Manipulation Classes
    @FXML public void imageSetGrayscale(ActionEvent event) {
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        myimageView.setEffect(grayscale);
    }

    @FXML public void imageSetRed(ActionEvent event) {
        Color red = Color.hsb(255,0,0);
//        red
//        myimageView.setEffect(red);
    }

    @FXML public void imageSetOriginal(ActionEvent event) {
        myimageView.setEffect(null);
    }

    public void exampleImageAdjust() {
        Image image = new Image("icon.png");
//        PixelReader pr = myimageView.getImage().getPixelReader();
//        int width = (int)myimageView.getImage().getWidth();
//        int height = (int)myimageView.getImage().getHeight();
        PixelReader pr = image.getPixelReader();
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        byte[] buffer = new byte[width * height * 4];
        pr.getPixels(
                0,
                0,
                width,
                height,PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                width *4
        );

        System.out.println(pr);


    }



}