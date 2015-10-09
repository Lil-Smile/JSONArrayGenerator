package com.lilsmile;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static final String integerType = "integer";
    private static final String stringType = "string";
    private static final String booleanType = "boolean";


    public static void main(String[] args) {
        System.out.println("Standard:");
        System.out.println("First line - enum of types (like String integer String boolean) with only one space");
        System.out.println("Second line - names of objects (like 'phone' 'name' 'song')");
        System.out.println("Other lines - objects, one line - one object");
        System.out.println("It makes file in same directory with name 'nameOfFIle.json'");
        System.out.println("Write the path of your *.txt file");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String path="";
        try {
            path = in.readLine();
        } catch (IOException e) {
            System.out.println("Smth went wrong, reopen program and try again");
            System.exit(1);
        }

        File file = new File(path);
        StringBuilder pathOuterFile= new StringBuilder();
        int i = file.getName().length()-1;
        while (file.getName().charAt(i)!='.'){i--;}
        pathOuterFile.append(file.getName().substring(0,i));
        pathOuterFile.append(".json");
        StringBuilder absolutePath = new StringBuilder();
        absolutePath.append(file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-file.getName().length()));
        String pathOuterFileString = absolutePath+pathOuterFile.toString();

        boolean flag = formNewFile(file, pathOuterFileString);
        if (flag)
        {
           System.out.println("Everything is ok!");
        } else
        {
            System.out.println("Smth went wrong, reopen program and try again");
        }
        return;

    }

    private static boolean formNewFile(File file, String path)
    {
        String[] typesArray;
        String[] namesArray;
        File outerFile = new File(path);
        FileWriter writer;
        try {
            writer = new FileWriter(outerFile);
        } catch (IOException e) {
            return false;
        }
        try {
            Scanner fileReader = new Scanner(file);
            writer.write("[\n");
            String types = fileReader.nextLine();
            types = types.toLowerCase();
            typesArray = types.split(" ");
            String names = fileReader.nextLine();
            namesArray = names.split(" ");
            String newLine=null;
            while (fileReader.hasNext())
            {
                newLine = fileReader.nextLine();
                StringBuilder lineToWrite = new StringBuilder();
                lineToWrite.append("{\n");
                String[] objects = newLine.split(" ");
                for (int i = 0; i<objects.length; i++)
                {

                    lineToWrite.append("\""+namesArray[i]+"\":");
                    switch (typesArray[i])
                    {
                        case integerType:
                        {
                            lineToWrite.append(objects[i]);
                            break;
                        }
                        case stringType:
                        {
                            lineToWrite.append("\""+objects[i]+"\"");
                            break;
                        }
                        case booleanType:
                        {
                            lineToWrite.append(objects[i]);
                            break;
                        }
                    }
                    if (i!=objects.length-1)
                    {
                        lineToWrite.append(",\n");
                    }
                }
                if (fileReader.hasNext())
                {
                    lineToWrite.append("\n},\n");
                } else {
                    lineToWrite.append("\n}\n");
                }
                writer.write(lineToWrite.toString());

            }
            writer.write("]");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

}
