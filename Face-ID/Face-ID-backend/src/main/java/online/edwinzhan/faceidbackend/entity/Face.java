package online.edwinzhan.faceidbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Face {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long internalId;
    private String name;
    private String idHash;
    private Double euclideanDistance;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void setIdWithHashing(String id) {
        this.idHash = encoder.encode(id);
    }

}
