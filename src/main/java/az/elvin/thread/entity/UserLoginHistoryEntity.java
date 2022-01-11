package az.cc103.doctorNurseDriver.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_login_history", schema = "callcenter103")
@Data
public class UserLoginHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "fcm_token")
    private String fcmToken;

    @CreationTimestamp
    @Column(name = "login_date")
    private LocalDateTime loginDate;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "active")
    private Integer active;
}
