package az.elvin.constructionAdmin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Method {

    private static final String url = "/opt/tomcat/webapps/images/";

    public static String fileWrite(MultipartFile image) throws IOException {

        String newFileImage = image.getOriginalFilename()
                .replace(image.getOriginalFilename()
                        .substring(0, image.getOriginalFilename()
                                .lastIndexOf(".")), UUID.randomUUID().toString());

        Files.write(Paths.get(url + newFileImage), image.getBytes());

        return newFileImage;

    }
}
