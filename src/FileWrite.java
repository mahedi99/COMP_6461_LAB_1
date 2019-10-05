import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {

        /**
     * Use FileWriter when number of write operations are less
     * @param data
     */
    public static void writeUsingFileWriter(String data, String locationToWrite) {
        String location= new File("").getAbsolutePath() + "\\" + locationToWrite;
		File file = new File(location);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}

}
