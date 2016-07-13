package database;

import exceptions.ChainBuilder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOTool {

    static boolean writeObject(Object object, String relativePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(relativePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            ChainBuilder chain = new ChainBuilder();
            chain.getHandler().handleRequest(e);
            return false;
        }
    }

    static Object readObject(String relativePath) {
        Object object = null;
        try {
            FileInputStream fileIn = new FileInputStream(relativePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            object = in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            ChainBuilder chain = new ChainBuilder();
            chain.getHandler().handleRequest(e);
        } finally {
            return object;
        }
    }
}
