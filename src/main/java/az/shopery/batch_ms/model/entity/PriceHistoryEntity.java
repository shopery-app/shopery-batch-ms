package az.shopery.batch_ms.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "price_history")
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product;
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    BigDecimal price;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;
}
