package karlord19.cardarchitect;

import java.util.logging.Logger;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.util.Map;
import java.util.HashMap;

public class CsvLoader {
    private String file;
    private Logger logger = Logger.getLogger(CsvLoader.class.getName());
    private Map<String, Drawable> columns = new HashMap<String, Drawable>();
    public CsvLoader(String file) {
        this.file = file;
    }
    public void addColumn(String name, Drawable drawable) {
        columns.put(name, drawable);
    }
    public void load() {
        logger.info("Loading file " + file);
        try (Reader in = Files.newBufferedReader(Paths.get(file))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                logger.info("Loading record " + record);
                for (Map.Entry<String, Drawable> entry : columns.entrySet()) {
                    entry.getValue().add(record.get(entry.getKey()));
                    logger.info("Adding " + record.get(entry.getKey()) + " to " + entry.getKey());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
