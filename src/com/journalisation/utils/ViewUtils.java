package com.journalisation.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.journalisation.resources.view.ViewBehavior;

public class ViewUtils {
    private static double x=0,y=0;
    public static Stage ModalFrame(Parent p, Stage parent){
        Stage dialog=new Stage(StageStyle.UNDECORATED);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parent);

        dialog.initStyle(StageStyle.TRANSPARENT);
        p.setOnMousePressed((MouseEvent)->{
            x=MouseEvent.getSceneX();
            y=MouseEvent.getSceneY();
        });
        p.setOnMouseDragged((event)-> {
            dialog.setX(event.getScreenX()-x);
            dialog.setY(event.getScreenY()-y);

        });
        Scene scene=new Scene(p);
        scene.setFill(null);
        dialog.setScene(scene);

        return dialog;
    }

    public static Stage changePassword(String url,Stage parent){
        ViewBehavior vb=new ViewBehavior(url);
        Parent p=vb.getparent();
        return ModalFrame(p,parent);
    }

    public static Stage Modal(String url,Stage parent){
        ViewBehavior vb=new ViewBehavior(url);
        Parent p=vb.getparent();
        return ModalFrame(p,parent);
    }
}
