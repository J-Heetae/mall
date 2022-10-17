package project.mall.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.mall.domain.UploadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String kind, String filename) {
        return fileDir + kind + "/" +  filename;
    }
    
    //파일 여러개 업로드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeItemImageFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    
    //파일 하나 업로드
    public UploadFile storeItemImageFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename); //uuid + 확장자
        multipartFile.transferTo(new File(getFullPath("products", storeFileName)));

        return UploadFile.create(originalFilename, storeFileName);

    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
