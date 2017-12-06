package view;

import database.Database;
import database.Sql;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Erd;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class GraphicsRootController {
    @FXML
    private Pane erPane;

    private Erd erd = new Erd("Noname");
    private Scene scene;

    private static final int OFFSET_X_SCREENSHOT = 20;
    private static final int OFFSET_Y_SCREENSHOT = 20;


    public GraphicsRootController() {

    }

    @FXML
    private void initialize() {
        erPane.setOnMouseClicked((e) -> {
            if(e.isShiftDown()) {
                if (e.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                    EntityER entityEr = new EntityER(erPane, erd);
                    entityEr.getGroup().relocate(e.getX(), e.getY());
                }
                if (e.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                    RelationshipER r = new RelationshipER(erPane, erd);
                    r.getGroup().relocate(e.getX(), e.getY());
                }
            }
            if(e.isAltDown()) {
                erd.getRelationships().stream().forEach(System.out::println);
                Database d = new Sql();
                long a = System.currentTimeMillis();
                d.generate(erd);
                System.out.println(d.getScript(erd));
                System.out.println(System.currentTimeMillis() - a);

            }
        });
    }
    @FXML
    private void exportImage() {
        FileChooser fileChooser = new FileChooser();

        //Imposta un filtro per la estensione
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Chiede all'utente di selezionare un file
        Optional.ofNullable(fileChooser.showSaveDialog(null))
                .ifPresent(file -> {
                    try {
                        WritableImage writableImage = new WritableImage((int)erPane.getWidth() + OFFSET_X_SCREENSHOT,
                                (int)erPane.getHeight() + OFFSET_Y_SCREENSHOT);
                        erPane.snapshot(null, writableImage);
                        // Scrive lo snapshot sul file impostato dall'utente
                        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null)
                                     , "png"
                                     , file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });


    }
    @FXML
    private void exportScript() {
        /*
        FileChooser fileChooser = new FileChooser();

        //Imposta un filtro per la estensione
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql files (*.sql)", "*.sql"));

        //Chiede all'utente di selezionare un file
        Optional.ofNullable(fileChooser.showSaveDialog(null))
                .ifPresent(file -> {

                    try {
                        if(file.canWrite()) {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            Database generator = new Sql();
                            generator.generate(erd);
                            fileOutputStream.write(generator.getScript(erd).getBytes());
                            fileOutputStream.close();
                        }

                        WritableImage writableImage = new WritableImage((int)erPane.getWidth() + OFFSET_X_SCREENSHOT,
                                (int)erPane.getHeight() + OFFSET_Y_SCREENSHOT);
                        erPane.snapshot(null, writableImage);
                        // Scrive lo snapshot sul file impostato dall'utente
                        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null)
                                , "png"
                                , file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
        */
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("sql files (*.sql)", "*.sql"));
        Optional.ofNullable(fileChooser.showSaveDialog(null))
                .ifPresent(file -> {
                    new Thread(() -> {
                        try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                Database generator = new Sql();
                                generator.generate(erd);
                                fileOutputStream.write(generator.getScript(erd).getBytes());
                                fileOutputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                });





    }


}
