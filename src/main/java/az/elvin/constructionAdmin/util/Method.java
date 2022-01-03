package az.elvin.constructionAdmin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Method {

    private static final String UPLOAD_DIRECTORY = "upload";
  //  private static final String url = "C:\\Users\\DELL\\Desktop\\freeLance";

    private static final String url = "/home/k-ugur/media/";


    public static String fileWrite(MultipartFile image) throws IOException {

        String uploadPath = url + File.separator + UPLOAD_DIRECTORY;

        String newFileImage =  image.getOriginalFilename()
                .replace(image.getOriginalFilename()
                        .substring(0, image.getOriginalFilename()
                                .lastIndexOf(".")), UUID.randomUUID().toString());

        Files.write(Paths.get(uploadPath + File.separator + newFileImage), image.getBytes());

        return newFileImage;

    }
}
