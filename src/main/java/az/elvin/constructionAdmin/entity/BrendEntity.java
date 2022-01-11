package az.elvin.constructionAdmin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "brend", schema = "pr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "data_date")
    private LocalDateTime dataDate;

    @Column(name = "active")
    private Integer active;
}
