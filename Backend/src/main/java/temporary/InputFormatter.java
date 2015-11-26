package temporary;

/* Header remover for csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import java.io.*;

public class InputFormatter {private String filePath;
                             private String fileName;
    InputFormatter(String filePath, String fileName ) {
        this.filePath = filePath;
        this.fileName = fileName;
        fullName = filePath + fileName + fileFormat;
    }
    private String fullName;
    private String fileFormat = ".csv";
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
    public void format() throws IOException{
        File file = new File(fullName);
        File tempFile = new File(filePath + "Formated_" + fileName + fileFormat);
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
            pw.close();
            br.close();
            System.out.println(amt1);
            System.out.println(amt2);
        }
    }

    public static void main(String[] args) {
        String fileDir  = "/home/guzel/Programming/SweetyReader/Backend/src/main/java/data/";
        String fileName ="BX-Book-Ratings" ;
        InputFormatter remover = new InputFormatter(fileDir , fileName );
        try {
            remover.format();
        } catch (IOException e) {
        System.err.println("Cannot find file");
        }
    }
}
