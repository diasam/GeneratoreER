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
import java.io.IOException;

public class GraphicsRootController {
    @FXML
    private Pane erPane;

    @FXML

    private Erd erd = new Erd("Noname");
    private Scene scene;


    public GraphicsRootController() {

    }
    @FXML
    private void initialize() {
        //myPane = new EntityER();
        //Pane erPane = ((Pane)scene.lookup("#erPane"));
        //((Pane)scene.lookup("#erPane")).requestLayout();

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
                Database d = new Sql();
                long a = System.currentTimeMillis();
                d.generate(erd);
                System.out.println(d.getScript(erd));
                System.out.println(System.currentTimeMillis() - a);

            }
        });
    }
    @FXML
    private void export() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)erPane.getWidth() + 20,
                        (int)erPane.getHeight() + 20);
                erPane.snapshot(null, writableImage);

                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }


}
