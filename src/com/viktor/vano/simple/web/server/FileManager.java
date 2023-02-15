package com.viktor.vano.simple.web.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {
    public static String readOrCreateFile(String filename)
    {
        File file = new File(filename);

        try
        {
            //Create the file
            if (file.createNewFile())
            {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String data;
            data = reader.readLine();
            reader.close();
            System.out.println("Reading successful.");

            if(data==null && filename.equals("webServerPort.txt"))
            {
                data="9000";
                writeToFile(filename, data);
            }else if(data==null && filename.equals("web.html"))
            {
                data="<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>WEB Page</title></head><body><h1>EMPTY WEB PAGE</h1></body></html>";
                writeToFile(filename, data);
            }

            return data;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeToFile(String filename, String data)
    {
        File file = new File(filename);

        try
        {
            //Create the file
            if (file.createNewFile())
            {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            //Write Content
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
            System.out.println("File write successful.");
            return true;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return false;
        }
    }
}
