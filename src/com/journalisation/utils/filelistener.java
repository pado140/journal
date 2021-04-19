package com.journalisation.utils;

import javafx.application.Platform;
import com.journalisation.alert.Alert;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.*;
import static com.journalisation.Main.primarystage;

public class filelistener {
    private static filelistener fl=null;
    private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    private final boolean recursive;
    private boolean trace = false;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    public static synchronized filelistener instance(Path dir, boolean recursive){
        if(fl==null) {
            try {
                fl=new filelistener(dir, recursive);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fl;
    }

    /**
     * Register the given directory with the WatchService
     */
    public void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    public void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    private filelistener(Path dir, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;

        if (recursive) {
            System.out.format("Scanning %s ...\n", dir);
            registerAll(dir);
            System.out.println("Done.");
        } else {
            register(dir);
        }

        // enable trace after initial registration
        this.trace = true;
    }

    /**
     * Process all events for keys queued to the watcher
     */
    public void processEvents(String filename) {
        Thread t=new Thread(){
            @Override
            public void run() {
                for (;;) {

                    // wait for key to be signalled
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException x) {
                        return;
                    }

                    Path dir = keys.get(key);
                    if (dir == null) {
                        System.err.println("WatchKey not recognized!!");
                        continue;
                    }

                    for (WatchEvent<?> event: key.pollEvents()) {
                        WatchEvent.Kind kind = event.kind();

                        // TBD - provide example of how OVERFLOW event is handled
                        if (kind == OVERFLOW) {
                            continue;
                        }



                        // Context for directory entry event is the file name of entry
                        WatchEvent<Path> ev = cast(event);
                        Path name = ev.context();
                        Path child = dir.resolve(name);

                        if(kind==ENTRY_MODIFY){
                            if(child.toString().contains(filename))
                                Platform.runLater(()->{Alert.showInfo(primarystage,"alert",String.format("%s",child.toString()),null);});
                        }

                        // print out event
                        System.out.format("%s: %s\n", event.kind().name(), child);

                        // if directory is created, and watching recursively, then
                        // register it and its sub-directories
                        if (recursive && (kind == ENTRY_CREATE)) {
                            try {
                                if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                                    registerAll(child);
                                }
                            } catch (IOException x) {
                                x.printStackTrace();
                                //System.err.println(x);
                                // ignore to keep sample readbale
                            }
                        }
                    }

                    // reset key and remove from set if directory no longer accessible
                    boolean valid = key.reset();
                    if (!valid) {
                        keys.remove(key);

                        // all directories are inaccessible
                        if (keys.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    public static void usage() {
        System.err.println("usage: java filelistener [-r] dir");
        //System.exit(-1);
    }



}
