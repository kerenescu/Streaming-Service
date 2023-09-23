import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

public abstract class CSVPopulable {
    public void populateFrom(String path) {
        try {
            FileReader fr = new FileReader(path);
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(fr);
            Map<String, String> line;
            while((line = reader.readMap()) != null) {
                addItem(line);
            }
            reader.close();
            fr.close();
        } catch (CsvValidationException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected abstract void addItem(Map<String, String> data);
}
