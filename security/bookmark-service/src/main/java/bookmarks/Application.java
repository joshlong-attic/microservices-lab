package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Bookmark findByUserIdAndId(String userId, Long id);

    List<Bookmark> findByUserId(String userId);
}

@SpringBootApplication
@EnableEurekaClient
@EnableOAuth2Resource
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(BookmarkRepository bookmarkRepository) {
        return args ->
                Arrays.asList("mstine", "jlong").forEach(n ->
                        bookmarkRepository.save(new Bookmark(n,
                                "http://some-other-host" + n + ".com/",
                                "A description for " + n + "'s link",
                                n)));
    }
}

@RestController
@RequestMapping("/bookmarks")
class BookmarkRestController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> getBookmarks(Principal principal) {
        String userId = principal.getName();
        return this.bookmarkRepository.findByUserId(userId);
    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    Bookmark getBookmark(Principal principal, @PathVariable Long bookmarkId) {
        String userId = principal.getName();
        return this.bookmarkRepository.findByUserIdAndId(userId, bookmarkId);
    }

    @RequestMapping(method = RequestMethod.POST)
    Bookmark createBookmark(Principal principal, @RequestBody Bookmark bookmark) {
        String userId = principal.getName();
        Bookmark bookmarkInstance = new Bookmark(userId, bookmark.getHref(),
                bookmark.getDescription(), bookmark.getLabel());
        return this.bookmarkRepository.save(bookmarkInstance);
    }

}

@Entity
class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String href;

    private String description;
    private String label;

    Bookmark() {
    }

    public Bookmark(String userId, String href,
                    String description, String label) {
        this.userId = userId;
        this.href = href;
        this.description = description;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public String getDescription() {
        return description;
    }
}