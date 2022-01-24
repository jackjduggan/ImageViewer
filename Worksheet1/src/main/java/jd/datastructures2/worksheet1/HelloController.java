package jd.datastructures2.worksheet1;

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
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    //Menu Bar
    @FXML private MenuBar menuBar;
    @FXML private Menu menuBarFile;
        @FXML private MenuItem menuBarFileOpenImage;
        @FXML private MenuItem menuBarFileCloseApplication;
    @FXML private Menu menuBarAbout;
    @FXML private Menu menuBarExit;

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
    enum ColorChannel {RED, GREEN, BLUE};
    @FXML private Slider hueSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuBarFileOpenImage.setOnAction(this::chooseFileAndDisplay);
    }

    public void chooseFileAndDisplay(ActionEvent event) {
        //Initialize FileChooser
        FileChooser openImageChooser = new FileChooser();
        openImageChooser.setTitle("Open Image");
        openImageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files (.png, .jpg, .gif)", "*.png", "*.jpg", "*.gif"));

        //Main image read/write method
        imageReadWrite(openImageChooser);
    }

    public void imageReadWrite(FileChooser openImageChooser) {
        File selectedFile = openImageChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileNameLabel.setText("File Name: " + selectedFile.getName());
            fileSizeLabel.setText("File Size: " + selectedFile.length() + " bytes");
            filePathLabel.setText("File Path: " + selectedFile.getPath());

            Image image = new Image(selectedFile.toURI().toString());
            myimageView.setImage(image);
            myimageView.setFitHeight(200);
            myimageView.setFitWidth(200);

            //Obtain PixelReader
            PixelReader pixelReader = myimageView.getImage().getPixelReader();
            System.out.println("Image Width: " + image.getWidth());
            System.out.println("Image Height: " + image.getHeight());
            //Loop over every column pixel, from x=0 to x=image.getWidth()-1
            pixelLooper(image, pixelReader);
            //Create WritableImage
            WritableImage writableImage = new WritableImage(
                    (int)image.getWidth(), (int)image.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
        }
    }


    private void pixelLooper(Image image, PixelReader pixelReader) {
        for (int y=0; y<image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);
                System.out.print("Pixel color at coordinates (x=" + x + ", y=" + y + ") ");
                System.out.print("R = " + color.getRed());
                System.out.print(", G = " + color.getGreen());
                System.out.println(", B = " + color.getBlue());
            }
        }
    }

    @FXML
    private void setColour(Image image, ColorChannel cc) {
        Image src = myimageView.getImage();
        PixelReader reader = src.getPixelReader();
        int width = (int)src.getWidth();
        int height = (int)src.getHeight();
        WritableImage dest = new WritableImage(width, height);
        PixelWriter writer = dest.getPixelWriter();
        for (int x=0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = null;
                if (cc == ColorChannel.RED) {
                    c = new Color(reader.getColor(x, y).getRed(),
                            0,
                            0,
                            reader.getColor(x, y).getOpacity());
                    writer.setColor(x, y, c);
                }
                if (cc == ColorChannel.GREEN) {
                    c = new Color(0,
                            reader.getColor(x, y).getGreen(),
                            0,
                            reader.getColor(x, y).getOpacity());
                    writer.setColor(x, y, c);
                }
                if (cc == ColorChannel.BLUE) {
                    c = new Color(0,
                            0,
                            reader.getColor(x, y).getBlue(),
                            reader.getColor(x, y).getOpacity());
                    writer.setColor(x, y, c);
                }
            }
        }
        myimageView.setImage(dest);
    }

    public void exitApplicationAction(ActionEvent event) {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    //Image Manipulation Classes
    @FXML
    private void imageSetGrayscale(ActionEvent event) {
        //Replace this non-data-structure method with PixelReader/Writable Image style method.
        //Do it above in the setColour. Average of R,G,B values.
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        myimageView.setEffect(grayscale);
    }

    public void imageSetRed(ActionEvent event) {
        setColour(myimageView.getImage(), ColorChannel.RED);
    }
    public void imageSetGreen(ActionEvent event) {
        setColour(myimageView.getImage(), ColorChannel.GREEN);
    }
    public void imageSetBlue(ActionEvent event) {
        setColour(myimageView.getImage(), ColorChannel.BLUE);
    }

    public void imageSetOriginal(ActionEvent event) {
        //Method doesn't yet work.
        myimageView.setEffect(null);
        myimageView.setImage(myimageView.getImage());
    }
}