package project.mall.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue
    @Column(name = "upload_file_id")
    private long id;

    @OneToOne(mappedBy = "imageFile", fetch = FetchType.LAZY)
    private Item item;

    private String uploadFileName;
    private String storeFileName;

    //==생성 메서드==//
    private UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public static UploadFile create(String uploadFileName, String storeFileName) {
        return new UploadFile(uploadFileName, storeFileName);
    }
}
