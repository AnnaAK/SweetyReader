package DatasetFormatter;

/* Header remover for csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import java.io.*;

public class InputFormatter {
    public void setFileName (String str){
        fileName = str;
        fullName = filePath + fileName + fileFormat;
    }
    private String filePath  = "/home/guzel/" +
            "Programming/SweetyReader/Backend/src/main/java/data/";
    private String fileName ="" ;
    private String fileFormat =".csv";
    private String fullName = filePath + fileName + fileFormat;

    private void createFile(File f) {
        try {
            if (f.createNewFile()) {
                System.out.println("File named " + f
                        + " created successfully !");
            } else {
                System.out.println("File with name " + f
                        + " already exixts !");
            }
        } catch (IOException e) {
            System.out.println("Error in creating");
        }
    }
    private boolean notCorrect (String line){
        for (char e  : line.toCharArray()){
            if (! Character.isDigit(e) && (e != ';')
                    && (e != '"'))
                return true;
        }
        return false;
    }
    public void formatTestSet() throws IOException{
        if (fileName == "") {
            System.out.println("Set fileName");
            throw new IOException();
        }
        File file = new File(fullName);
        File tempFile = new File(filePath +
                "Formated_" + fileName + fileFormat);
        createFile(tempFile);

        BufferedReader br = null;
        PrintWriter pw = null;

        int amt1 = 0;
        int amt2 = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            pw = new PrintWriter(new FileWriter(tempFile));

            //skip first line (our database is big, so we sure that it's not empty)
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                amt1 ++;
                if (notCorrect(line)) {
                    amt2 ++;
                    continue;
                }
                for (char e  : line.toCharArray()){
                    switch (e){
                        case '"' : break;
                        case ';' : {
                            pw.print(',');
                            break;
                        }
                        default:{
                            pw.print(e);
                            break;
                        }
                    }
                }
                pw.println();
                pw.flush();
            }
        }catch (IOException e) {
                System.err.println("Cannot find file");
        } finally {
            fileName = "";
            pw.close();
            br.close();
            System.out.println(amt1);
            System.out.println(amt2);
        }
    }
    private String deleteQuotes(String str){
        String res = "";
        for (char e  : str.toCharArray()){
            if (e != '"'){
                res += e;
            }
        }
        return res;
    }
    private String parseLine (String line) throws IOException {
        String delims = ";";
        String[] tokens = line.split(delims);
        if (notCorrect(tokens[0])){
           return  "";
        }
        int n = tokens.length;
        String res = tokens[0] + ";";
        for (int i = 1; i < n - 6; i ++ ){
            res += tokens[i];
        }
        res += ";" + tokens[n - 6] + ";";
        for (int i = n - 3; i < n; i ++ ){
            res += tokens[i] + ";";
        }
        /*int amt = 0;
        for (char e : res.toCharArray() ){
            if (e == ';') amt ++;
        }*/
        /*if (amt != 6){
            System.out.println(line);
            System.out.println(amt);
            System.out.println(res);
            throw new IOException();
        }*/
        return deleteQuotes(res);
    }
    public void formatBookInformationSet() throws IOException{
        if (fileName == "") {
            System.out.println("Set fileName");
            throw new IOException();
        }
        File file = new File(fullName);
        File tempFile = new File(filePath +
                "Formated_" + fileName + fileFormat);
        createFile(tempFile);

        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            br = new BufferedReader(new FileReader(file));
            pw = new PrintWriter(new FileWriter(tempFile));

            //skip first line (our database is big,
            // so we sure that it's not empty)
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                line = parseLine(line);
                if (line == ""){
                    continue;
                }
                pw.println(line);
                pw.flush();
            }
        }catch (IOException e) {
            System.err.println("Cannot find file");
        } finally {
            fileName = "";
            pw.close();
            br.close();
        }
    }
    /*public static void main(String[] args) {
        String fileName ="BX-Book-Ratings" ;
        InputFormatter testSetFormatter =
                new InputFormatter();
        testSetFormatter.setFileName(fileName);
        try {
            testSetFormatter.formatTestSet();
        } catch (IOException e) {
        System.err.println("Cannot find file");
        }
    }*/
    public static void main(String[] args) {
        String fileName ="BX-Books" ;
        InputFormatter testSetFormatter =
                new InputFormatter();
        testSetFormatter.setFileName(fileName);
        try {
            testSetFormatter.formatBookInformationSet();
        } catch (IOException e) {
            System.err.println("Cannot find file");
        }
        /*String line = "dvdsv;  ,sfdffd;vdsvdvd";
        String delims = "[;]";
        String[] tokens = line.split(delims);
        for (String e : tokens){
            System.err.println(e);
        }*/
    }
}
