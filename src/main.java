import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RICVA on 07/12/2017.
 */
public class main {
    public static void main(String[] args) {
        parseFile(selectProblemFile());
    }

    public static void parseFile (File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File selectProblemFile() {
        System.out.println("List of problem files:");
        final File folder = new File("./problems");
        ArrayList<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(files.size()+") "+fileEntry.getName());
            files.add(fileEntry);
        }
        System.out.print("Please choose a number:");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        return (files.get(i));
    }


}
