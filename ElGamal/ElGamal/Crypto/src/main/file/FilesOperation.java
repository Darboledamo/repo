package file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FilesOperation {

    public String readFile(String filePath){

        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    public ArrayList<String> readFileArray(String filename){

        ArrayList<String> result = new ArrayList<>();

        try (FileReader f = new FileReader(filename)) {

            StringBuffer sb = new StringBuffer();

            while (f.ready()) {
                char c = (char) f.read();
                if (c == '\n') {
                    result.add(sb.toString());
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }

            if (sb.length() > 0) {
                result.add(sb.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void  writeFile(String content, String path){

        try (FileWriter writer = new FileWriter(path);
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
