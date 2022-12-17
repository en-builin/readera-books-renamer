package en.builin.booksrenamer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibraryService {

    private final FileHolderService fileHolderService;
    
    private FileHolder fileHolder;
    
    private static final String LIBRARY_FILE_NAME = "library.json";
    
    public void checkDeletedBooks(String bakFileName) {
        
        fileHolder = fileHolderService.readBakFile(bakFileName);

        File libraryFile = fileHolder.getFile(LIBRARY_FILE_NAME);

        List<Document> documents = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(libraryFile);
            JsonNode docsNode = jsonNode.get("docs");
            for ( JsonNode elementNode : docsNode) {
                documents.add(objectMapper.convertValue(elementNode.get("data"), Document.class));
            } 
        } catch (IOException e) {
            log.error("Error reading JSON from file {}", libraryFile, e);
            throw new RuntimeException(e);
        }
        log.info("Read {} documents from library", documents.size());
    }
}
