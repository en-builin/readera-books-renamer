package en.builin.booksrenamer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;

@Component
@Slf4j
public class FileHolder {
    
    private final HashMap<String, File> storage = new HashMap<>();

    public boolean initiated() {
        return !storage.isEmpty();
    }

    public void clear() {
        storage.clear();
    }
    
    public void addFile(File file) {
        storage.put(file.getName(), file);
    }
    
    public File getFile(String fileName) throws IllegalArgumentException {
        if (storage.containsKey(fileName)) {
            log.info("Working with file {}", fileName);
            return storage.get(fileName); 
        } else {
            log.error("File not found: {}", fileName);
            throw new IllegalArgumentException("File not found: " + fileName);
        }
    }
}
