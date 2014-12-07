package photos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.security.Principal;

@SpringBootApplication
@EnableEurekaClient
@EnableOAuth2Resource
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
@RequestMapping("/photo")
class PhotoRestController {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    ResponseEntity<?> set(Principal principal,
                          @RequestParam MultipartFile multipartFile,
                          UriComponentsBuilder uriBuilder) throws IOException {

        String userId = principal.getName();


        try (InputStream inputStream = multipartFile.getInputStream()) {
            this.gridFsTemplate.store(inputStream, userId);
        }
        URI uri = uriBuilder.path("/photo").buildAndExpand(userId).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<Resource> get(Principal principal) {
        String userId = principal.getName();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(
                this.gridFsTemplate.getResource(userId), httpHeaders, HttpStatus.OK);
    }
}

