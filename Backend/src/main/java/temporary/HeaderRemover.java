package temporary;


/* Header remover for csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import java.io.*;

public class HeaderRemover {private String filePath;
                            private String fileName;
    HeaderRemover(String filePath,String fileName ) {
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
    public void removeHeader() throws IOException{
        File file = new File(fullName);
        File tempFile = new File(filePath + "Formated_" + fileName + fileFormat);
        createFile(tempFile);

        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            br = new BufferedReader(new FileReader(file));
            pw = new PrintWriter(new FileWriter(tempFile));

            //skip first line (our database big, so we sure that it's not empty)
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                pw.println(line);
                pw.flush();
            }
        }catch (IOException e) {
                System.err.println("Cannot find file");
        } finally {
            pw.close();
            br.close();
        }
    }

    public static void main(String[] args) {
        String fileDir  = "/home/guzel/Programming/SweetyReader/Backend/src/main/java/data/";
        String fileName ="BX-Book-Ratings" ;
        HeaderRemover remover = new HeaderRemover (fileDir , fileName );
        try {
            remover.removeHeader();
        } catch (IOException e) {
        System.err.println("Cannot find file");
        }

    }
}
