package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

import dao.Grupos;

public class Data
{
    @FXML
    private HBox hBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;

    public Data()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Grupos grupo)
    {
        label1.setText(grupo.getNombre());
        label2.setText(grupo.getDesc());
    }

    public HBox getBox()
    {
        return hBox;
    }
}
