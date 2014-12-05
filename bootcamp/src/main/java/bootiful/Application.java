package bootiful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Bean
    HealthIndicator healthIndicator() {
        return () -> Health.status("I <3 RWX & Ft. Lauderdale").build();
    }

    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
        return args -> {
            Arrays.asList("mstine", "jlong").forEach(s -> reservationRepository.save(new Reservation(s)));
            reservationRepository.findAll().forEach(System.out::println);
        };
    }

//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
}

@Controller
class ReservationMvcController {

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping("/reservations.php")
    String string(Model model) {
        model.addAttribute("reservations", this.reservationRepository.findAll());
        return "reservations"; // src/main/resources/templates + $X + .html
    }

    @RequestMapping("/die.php")
    void die() {
        System.exit(1);
    }
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Collection<Reservation> findByReservationName(String rn);
}

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    private String reservationName;

    Reservation() { // why JPA why?
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationName='" + reservationName + '\'' +
                '}';
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public Long getId() {

        return id;
    }

    public String getReservationName() {
        return reservationName;
    }
}