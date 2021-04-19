/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.utils;

import com.journalisation.Main;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import com.journalisation.alert.Alert;
import com.journalisation.controller.ControllerManager;
import com.journalisation.dao.bean.ImplEntity;
import com.journalisation.resources.log.AppLog;
import com.journalisation.resources.view.ViewBehavior;
import java.io.File;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * @author Padovano
 */
public class AppUtils {

    public static final String BUTTON_OK="ok";
    public static final String SUCCESS_MODAL_TITLE="Succes";
    public static final String ERROR_MODAL_TITLE="Erreur";
    public static final String WARNING_MODAL_TITLE="warning";
    public static final String ASK_MODAL_TITLE="confirm";

    /**
     * @param 
     *  
     */
    public static String PATH_PROJET= Paths.get(System.getProperty("user.home"),"c3editions").toFile().getPath();
    public static String PATH_RES= Paths.get(System.getProperty("user.home"),"c3editions").toFile().getPath();
    public static String PROVIDER="",HOST="",PORT="",dbUSER="",dbPASSWORD="",dbNAME="",API="";

    public static Method loadMethod(Class<?> clazz,String methodName,Object... parameters){
         try {
             return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException | SecurityException | IllegalArgumentException ex) {
            Alert.error(null, ex.getMessage(), null);
        }
         return null;
    }

    public static String serializedParam(String deleimiter,String... param){
        return String.join(",",param);
    }

    public static Object[] queryParam(Map<String,Object> param){
        Object[] cle=new Object[0];
        if(param!=null)
            if(!param.isEmpty()) {
                cle = new Object[param.size()];
                int i = 0;
                for (String key : param.keySet()) {
                    cle[i] = param.get(key);
                    i++;
                }
            }
        return cle;
    }

    public static String serializedParam(Map<String,Object> param){
        String[] cle=new String[0];
        if(param!=null)
            if(!param.isEmpty()) {
                cle = new String[param.size()];
                int i = 0;
                for (String key : param.keySet()) {
                    cle[i] = key + "=?";
                    i++;
                }
            }
        return serializedParam("and",cle);
    }

    public static String[] PreparedExpression(Set<String> param){
        String[] cle=new String[0];
        if(param!=null)
            if(!param.isEmpty()) {
                cle = new String[param.size()];
                int i = 0;
                for (String key : param) {
                    cle[i] = key + "=?";
                    i++;
                }
            }
        return cle;
    }
    //
    
    public static boolean validate(String Value,InputValidation regex){
        Pattern pattern=Pattern.compile(regex.getRegex(),Pattern.CASE_INSENSITIVE );
        Matcher match=pattern.matcher(Value);
        return match.matches();
    }
    
    public static void switchNode(String title, String url, ImageView icon, TabPane main_view, boolean close){
        //System.out.println("parent:"+content.getParent());
        ViewBehavior vb=new ViewBehavior(url);
        Parent pane=vb.getparent();
        ControllerManager cm=(ControllerManager)vb.getController();
        //cm.setMain_view(main_view);
        render(title, pane, icon,main_view,close);
            
    }
    
    
    public static void render(String title,Node pane,ImageView icon,TabPane main_view,boolean closeable){
        CreateCustomTab openTab=createTab(title, pane, icon,closeable);
        if(main_view.getTabs().contains(openTab))
            main_view.getSelectionModel().select(openTab);
        else{
            main_view.getTabs().add(openTab);
            main_view.getSelectionModel().select(openTab);
        }
    }
    
    public static CreateCustomTab createTab(String title,Node pane,ImageView icon,boolean closable){
        CreateCustomTab newTab=new CreateCustomTab();
        
        newTab.setId(title);
        newTab.setValue(title);
        newTab.setClosable(closable);
        newTab.setTitle(title);
        newTab.setContent(pane);
        newTab.closeable();
        if(icon!=null)
            newTab.setIcon(icon);
        
        return newTab;
    }
    public static boolean valid(TextInputControl field){
        System.out.println("validation:"+field);
        field.setTooltip(null);
            if(field.getText().trim().isEmpty()){
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
            }else{
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                return true;
            }
        Tooltip tooltip=new Tooltip("Veuiller remplir ce champ");
        tooltip.setStyle("-fx-background-color:-color-red;-fx-text-fill:-color-white;");
        field.setTooltip(tooltip);
        return false;
                //Tomorrow modified css to add pseudo class like valid and invalid for class .text-field
    }
    public static boolean valid(TextInputControl field,TextInputControl field2){
        boolean check=false;
        if(valid(field)&&valid(field2))
            check=field.getText().equals(field2.getText());
        if(!check){
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
                field2.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field2.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
            }else{
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                field2.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field2.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                return true;
            }
            Tooltip tooltip=new Tooltip("les deux champs doivent etre identiques");
        tooltip.setStyle("-fx-background-color:-color-red;-fx-text-fill:-color-white;");
        field.setTooltip(tooltip);
        field2.setTooltip(tooltip);
        
        return check;
                //Tomorrow modified css to add pseudo class like valid and invalid for class .text-field
    }
    public static boolean valid(TextInputControl field,int lon){
        boolean check=valid(field)&&field.getText().trim().length()>=lon;
        if(!check){
            field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
            }else{
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                return true;
            }
            Tooltip tooltip=new Tooltip(String.format("Ce champ doit avoir au minimum %d caracteres",lon));
        tooltip.setStyle("-fx-background-color:-color-red;-fx-text-fill:-color-white;");
        field.setTooltip(tooltip);
        
        return check;
    }
    public static boolean valid(TextInputControl field,InputValidation regex){
        boolean check=valid(field)&&validate(field.getText().trim(),regex);
        if(!check){
            field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
            }else{
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                return true;
            }
            Tooltip tooltip=new Tooltip(String.format(regex.getErrorMessage()));
        tooltip.setStyle("-fx-background-color:-color-red;-fx-text-fill:-color-white;");
        field.setTooltip(tooltip);

        return check;
    }
    public static boolean valid(ComboBoxBase field){
        System.out.println("validation:"+field);
            if(field.getValue()==null){
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),true);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),false);
            }else{
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("invalid"),false);
                field.pseudoClassStateChanged(PseudoClass.getPseudoClass("valid"),true);
                return true;
            }
            Tooltip tooltip=new Tooltip("vous devez faire un choix");
        tooltip.setStyle("-fx-background-color:-color-red;-fx-text-fill:-color-white;");
        field.setTooltip(tooltip);
        return false;
    }

    public static boolean isexclude(Node n,Node... exclude){
        for(Node no:exclude){
            return no.equals(n);
        }
        return false;
    }
    public static boolean validation (AnchorPane block,Node... exclude){
        ObservableList<Node> input=block.getChildren();
        boolean valid=true;
        for(Node n:input){
            if(!isexclude(n,exclude)){
            if(n instanceof TextInputControl){
                if(!valid((TextInputControl)n))
                    valid=false;
            }
            if(n instanceof ComboBoxBase)
            {
                if(!valid((ComboBoxBase)n)){
                    valid=false;
                }
            }
            }
        }
        return valid;
    }
     public static String ucfirst(String a){
        return a.toUpperCase().charAt(0)+a.substring(1);
     }
     public static String allCaps(String a){
        return a.toUpperCase();
     }
    public static String getText(TextInputControl control){
        return control.getText().trim();
    }
    
    public static Object getValue(ComboBoxBase control){
        return control.getValue();
    }

    public static void translateable(Pane pane){
        pane.getChildren().parallelStream().forEach(node -> {
            if(node instanceof Labeled) {
                //if(((Labeled) node).getId()!=null)
//                    Platform.runLater(() -> {
//                        ((Labeled) node).setText(rbs.Translate(((Labeled) node).getId()));
//                    });
            }
            if(node instanceof TableView){
                ((TableView)node).getColumns().parallelStream().forEach(o -> {
                    //if(((TableColumn)o).getId()!=null)
                        //Platform.runLater(()->{((TableColumn)o).setText(rbs.Translate(((TableColumn)o).getId()));});
                });

            }
            if(node instanceof Pane) {
                translateable((Pane)node);
            }
        });
    }

    public static Object hydratedVal(Object objet,String name,Object value){
        Object object=null;
        try {
            Method method;
            if(name.contains("_")){
                String[] newname=name.split("_");
            String newName=newname[0];
            method=objet.getClass().getMethod("get"+Capitalize(newName));
            Object cla=method.invoke(objet);
            Class obj=null;
            if(cla==null){
                obj=Class.forName(method.getReturnType().getName());
                cla=obj.getDeclaredConstructor().newInstance();
            }else
                obj=cla.getClass();
            object=hydratedVal(cla, name.substring(newName.length()+1),value);
            method=obj.getMethod("set"+Capitalize(newname[1]),object.getClass().getCanonicalName().contains("Integer")?int.class:(object.getClass().getCanonicalName().contains("java.sql.Date")? LocalDate.class:(object.getClass().getCanonicalName().contains("java.sql.Timestamp")? LocalDateTime.class:object.getClass())));
            method.invoke(cla,object.getClass().getCanonicalName().contains("java.sql.Date")? ((java.sql.Date)value).toLocalDate():(object.getClass().getCanonicalName().contains("java.sql.Timestamp")? ((java.sql.Timestamp)object).toLocalDateTime():object));
                
            return cla;
        }
            String prefix="set";
            String methodname=prefix+Capitalize(name);
        
                Class obj=null;
                if(objet==null){
                    obj=objet.getClass();
                    objet=obj.getDeclaredConstructor().newInstance();
                }else
                    obj=objet.getClass();
                methodname=prefix+Capitalize(name);
                method=obj.getMethod(methodname,value.getClass().getCanonicalName().contains("Integer")?int.class:(value.getClass().getCanonicalName().contains("java.sql.Date")? LocalDate.class:(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? LocalDateTime.class:value.getClass())));
                method.invoke(objet,value.getClass().getCanonicalName().contains("java.sql.Date")? ((java.sql.Date)value).toLocalDate():(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? ((java.sql.Timestamp)value).toLocalDateTime():value));
                return value;
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException | InstantiationException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            AppLog.Log(AppUtils.class.getName(), Level.SEVERE, ex.getMessage(), ex);
            Alert.error(null, ex.getMessage(),null);
            ex.printStackTrace();
            return null;
        }
    }
    public static void hydrated(Object objet,String name,Object value){
        Class ob=objet.getClass();
        String prefix="set";
        String methodname=prefix+Capitalize(name);
        String newName=name;
//        System.out.println(methodname);
//        System.out.println(ob.getName()+" :"+(objet==null));
        Method method;
        try {
            if(name.contains("_")){
                newName=name.substring(0, name.indexOf("_"));
//                method=ob.getMethod("get"+Capitalize(name.split("_")[0]));
//                Object cla=method.invoke(objet);
//                Class obj=null;
//                if(cla==null){
//                    obj=Class.forName(method.getReturnType().getName());
//                    cla=obj.getDeclaredConstructor().newInstance();
//                }else
//                    obj=cla.getClass();
//                methodname=prefix+Capitalize(name.split("_")[1]);
//                method=obj.getMethod(methodname,value.getClass().getCanonicalName().contains("Integer")?int.class:(value.getClass().getCanonicalName().contains("java.sql.Date")? LocalDate.class:(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? LocalDateTime.class:value.getClass())));
//                method.invoke(cla,value.getClass().getCanonicalName().contains("java.sql.Date")? ((java.sql.Date)value).toLocalDate():(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? ((java.sql.Timestamp)value).toLocalDateTime():value));
//                methodname=prefix+Capitalize(name.split("_")[0]);
                value=hydratedVal(objet, name, value);
            }
        methodname=prefix+Capitalize(newName);

//            method=ob.getMethod(methodname,value.getClass().getCanonicalName().contains("Integer")?int.class:(value.getClass().getCanonicalName().contains("java.sql.Date")? LocalDate.class:value.getClass()));
//            method.invoke(objet,value.getClass().getCanonicalName().contains("java.sql.Date")? ((java.sql.Date)value).toLocalDate():value);
            method=ob.getMethod(methodname,value.getClass().getCanonicalName().contains("Integer")?int.class:(value.getClass().getCanonicalName().contains("java.sql.Date")? LocalDate.class:(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? LocalDateTime.class:value.getClass())));
            method.invoke(objet,value.getClass().getCanonicalName().contains("java.sql.Date")? ((java.sql.Date)value).toLocalDate():(value.getClass().getCanonicalName().contains("java.sql.Timestamp")? ((java.sql.Timestamp)value).toLocalDateTime():value));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            AppLog.Log(AppUtils.class.getName(), Level.SEVERE, ex.getMessage(), ex);
            Alert.error(null, ex.getMessage(),null);
            ex.printStackTrace();
        }

    }

    private static String Capitalize(String string){
        String text=string.trim().toLowerCase();
        String first=text.charAt(0)+"";
        StringBuilder st=new StringBuilder();
        st.append(first.toUpperCase());
        st.append(text.substring(1));
        return st.toString();
    }

    public static String buildQuery(Class clazz){

        Field[] fields=clazz.getDeclaredFields();

        System.out.println(fields.length);
        List<Field> ls= Arrays.asList(fields);


        System.out.println("size:"+ls.size());
        System.out.println("list:"+ls);
        String query="select id,";
        Iterator<Field> ff=ls.iterator();
        boolean empty=true;
        while(ff.hasNext()){
            Field f=ff.next();
            Class cl=getClassOf((ParameterizedType)f.getGenericType());
            Object o=null;
            try {
                o=cl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                o=null;
            }
            try{
                System.out.println(o.getClass()+"---->"+cl.isSynthetic()+"--->"+(o instanceof ImplEntity));

                if(!empty)
                    query+=",";
                if(o instanceof ImplEntity){
                    List<Field> ls1=Arrays.asList(cl.getDeclaredFields());
                    boolean empty1=true;
                    Iterator<Field> ff1=ls1.iterator();
                    while(ff1.hasNext()){

                        Field f1=ff1.next();
                        Class cl1=getClassOf((ParameterizedType)f1.getGenericType());
                        Object o1=null;
                        try {
                            o1=cl1.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
                            o1=null;
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        try{
                            if(o1 instanceof ImplEntity){

                            }else{
                                if(!empty1)
                                    query+=",";
                                query+=cl.getSimpleName()+"_"+f1.getName();
                            }
                        }catch(NullPointerException e){
                            System.err.println(e);
                        }
                        empty1=false;
                    }

                }else
                    query+=f.getName();
                empty=false;
            }catch(NullPointerException e){
                System.out.println(cl+"---->"+cl.isSynthetic());
            }
            System.out.println(f.getType());

        }
        System.out.println(query);
        return "";
    }

    private static Class getClassOf(ParameterizedType pt){
        return (Class)pt.getActualTypeArguments()[0];
    }
    
    public static File saveLocation(){
        DirectoryChooser directorychooser=new DirectoryChooser();
        directorychooser.setTitle("Selectionnez le chemin de sauvegarde de votre fichier");
        return directorychooser.showDialog(Main.primarystage);
    }
}
