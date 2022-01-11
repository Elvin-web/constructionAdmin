package az.cc103.doctorNurseDriver.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", schema = "callcenter103")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "hospital_id", referencedColumnName = "id")
    private HospitalEntity hospital;

    @Column(name = "profile_image")
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private byte[] profileImage;

    @CreationTimestamp
    @Column(name = "data_date")
    private LocalDateTime dataDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "active")
    private Integer active;
}
