package karlord19.cardarchitect;

import java.util.logging.Logger;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.util.Map;
import java.util.HashMap;

/**
 * CsvLoader
 * 
 * A class that loads a CSV file into a set of Drawables.
 * Names of the columns should correspond to the header in the file.
 */
public class CsvLoader {
    private static final Logger logger = Logger.getLogger(CsvLoader.class.getName());
    private Map<String, Drawable> columns = new HashMap<String, Drawable>();

    /**
     * Create a CsvLoader.
     */
    public CsvLoader() {}

    /**
     * Add a column to the loader.
     * 
     * In case of same name, the last one added will be used.
     * @param name
     * @param drawable
     */
    public void addColumn(String name, Drawable drawable) {
        columns.put(name, drawable);
    }

    /**
     * Delete a column from the loader.
     * 
     * Good for reusing the same loader for different files.
     * @param name
     */
    public void deleteColumn(String name) {
        columns.remove(name);
    }

    /**
     * Load a file into the Drawables.
     * @param file
     */
    public void load(String file) {
        logger.info("Loading file " + file);
        try (Reader in = Files.newBufferedReader(Paths.get(file))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                for (Map.Entry<String, Drawable> entry : columns.entrySet()) {
                    entry.getValue().add(record.get(entry.getKey()));
                }
            }
        }
        catch (Exception e) {
            logger.warning("Failed to load file " + file);
            e.printStackTrace();
            return;
        }
    }
}
