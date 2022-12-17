package en.builin.booksrenamer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Document(
        String doc_sha1,
        String doc_md5,
        Integer doc_active,
        String user_title) {

}
