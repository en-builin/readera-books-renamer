package en.builin.booksrenamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileHolderService {

    private final FileHolder fileHolder;

    public FileHolder readBakFile(String bakFileName) {
        
        log.debug("Invoke unpackBakFile");

        Path tmpdir;
        try {
            tmpdir = Files.createTempDirectory("renamer");
            log.info("Created temp directory: {}", tmpdir);
        } catch (IOException e) {
            log.error("Can't create temp directory", e);
            throw new RuntimeException(e);
        }
        
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(bakFileName))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    File newFile = newFile(tmpdir.toFile(), zipEntry);
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    log.info("Unpacked: {}", newFile);
                    fileHolder.addFile(newFile);
                }
                zipEntry = zis.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            log.error("Backup file not found", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Error unpacking backup file", e);
            throw new RuntimeException(e);
        }
        return fileHolder;
    }
    
    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
