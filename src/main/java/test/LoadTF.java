package test;

import org.tensorflow.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liudan19 on 2018/3/23.
 */
public class LoadTF
{
    public static void main(String[] args) {
        String modelDir = "";
        //byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "mnist.pb"));
        byte[] graphDef = readAllBytesOrExit(Paths.get("D:\\Project\\WorkSpace\\webmodel\\src\\main\\java\\test","mnist2.pb"));
        ArrayList<Tensor> inputTensors = new ArrayList<Tensor> ();
        Tensor x = Tensor.create(2.0f);
        try {
            Graph g = new Graph();
            g.importGraphDef(graphDef);
            Session s = new Session(g);
            Tensor result = s.runner().feed("input_x", x).fetch("output").run().get(0);
            System.out.println(result.floatValue());
        }catch (Exception e){e.printStackTrace();}

    }
    private static byte[] readAllBytesOrExit(Path path) {
        System.out.println(path.toString());
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
