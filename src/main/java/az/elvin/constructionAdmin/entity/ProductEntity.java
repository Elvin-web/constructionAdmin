package az.elvin.constructionAdmin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product", schema = "pr")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_code", nullable = false)
    private int productCode;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "show_count", nullable = false)
    private int showCount;

    @Column(name = "having_status", nullable = false)
    private Integer havingStatus;

    @Column(name = "old_amount", nullable = false)
    private Double oldAmount;

    @Column(name = "new_amount", nullable = false)
    private Double newAmount;

    @ManyToOne
    @JoinColumn(name = "brend_id", referencedColumnName = "id")
    private BrendEntity brend;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "colour_id", referencedColumnName = "id")
    private ColourEntity colour;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "size_id", referencedColumnName = "id")
    private SizeEntity size;

    @CreationTimestamp
    @Column(name = "data_date")
    private LocalDateTime dataDate;

    @Column(name = "active")
    private Integer active;
}
