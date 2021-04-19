package com.journalisation.utils;

import com.journalisation.alert.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import static com.journalisation.Main.primarystage;
import static com.journalisation.Main.rbs;
import java.nio.file.Paths;

public class FileUtils {
    private long size;
    private String creator;
    private static FileUtils util;
    private File file;

    public File getFile() {
        return file;
    }
    

    private FileUtils(File file) {
        this.file = file;
        BasicFileAttributeView basicView =Files.getFileAttributeView(file.toPath(), BasicFileAttributeView.class);
        try {
            creator=Files.getOwner(file.toPath()).getName();
            BasicFileAttributes basicAttribs = basicView.readAttributes();
            size=basicAttribs.size();
        } catch (IOException e) {
            e.printStackTrace();
            Alert.error(primarystage, e.getMessage(), rbs);
        }
    }

    private FileUtils() {

    }

    public static synchronized FileUtils instance(File f){
        util=new FileUtils(f);
        return util;
    }

    public static synchronized FileUtils instance(){
        if(util==null)
            util=new FileUtils();
        return util;
    }
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String  renameToFile(String Oldpath) {
        Optional<String> new_name = Alert.showInputMessage(primarystage, "Saisissez le nom du fichier(omis l'extension)", rbs);

        String name=null;
        if (new_name.isPresent() && !new_name.get().isEmpty()) {
            System.out.println(new_name.get());
            name = new_name.get().replaceAll("[^\\w]", "") + getExt();
            System.out.println(name);
            String path = Oldpath + "\\"+ name;
            System.out.println(path);
            if(file.renameTo(new File(path))){
                System.out.println("name:"+name);
                return path;
            }
        }
        return null;
    }

    public String  renameToFile(String Oldpath,String new_name) {
        String name=null;
        if (!new_name.isEmpty()) {
            System.out.println(new_name);
            name = new_name;
            System.out.println(name);
            String path = Oldpath + "\\"+ name;
            System.out.println(path);
            if(file.renameTo(new File(path)))
                return name;
        }
        return null;
    }
    private String getExt(){
        System.out.println(file.getName().substring(file.getName().lastIndexOf(".")));
        return file.getName().substring(file.getName().lastIndexOf("."));
    }

    public boolean downloadFile(File f,String path){
        try {
            Files.createDirectories(Paths.get(path));
            Files.copy(f.toPath(),Paths.get(path,f.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            Alert.error(primarystage, e.getMessage(), rbs);
            return false;
        }
        return true;
    }

    public boolean deleteFile(String path){
        try {
            return Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            Alert.error(primarystage, e.getMessage(), rbs);
            return false;
        }
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    
}
