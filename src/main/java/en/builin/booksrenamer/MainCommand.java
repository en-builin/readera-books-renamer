package en.builin.booksrenamer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
@Controller
@Command(name = "renamer", mixinStandardHelpOptions = true,
        version = "ReadEra Books Renamer v0.1")
public class MainCommand implements Callable<Integer> {
    
    private final LibraryService libraryService;
    
    @Option(names = {"-b", "--bak"}, description = "path to backup ReadEra file", required = true)
    private String bakFileName;
    
    @Override
    public Integer call() {
        libraryService.checkDeletedBooks(bakFileName);
        return 0;
    }
}
