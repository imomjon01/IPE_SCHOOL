package ipe.school.ipe_school.models.dtos.req;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class UserReq {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    MultipartFile file;
}
