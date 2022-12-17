package en.builin.booksrenamer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@RequiredArgsConstructor
@SpringBootApplication
public class ReaderaBooksRenamerApplication implements CommandLineRunner, ExitCodeGenerator {
    
    private final IFactory factory;
    private final MainCommand mainCommand;
    
    private int exitCode;

    @Override
    public void run(String... args) {
        exitCode = new CommandLine(mainCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(ReaderaBooksRenamerApplication.class, args)));
    }
}
