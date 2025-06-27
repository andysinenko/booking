package homes.sinenko.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "units",  schema = "booking")
@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "units_seq")
    @SequenceGenerator(name = "units_seq", sequenceName = "units_seq", allocationSize = 1)
    private Long id;

    private Integer rooms;

    @Enumerated(EnumType.STRING)
    private UnitType type;

    private Integer floor;

    private BigDecimal cost;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean available = true;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id) && Objects.equals(rooms, unit.rooms) && type == unit.type && Objects.equals(floor, unit.floor) && Objects.equals(cost, unit.cost) && Objects.equals(description, unit.description) && Objects.equals(available, unit.available) && Objects.equals(createdAt, unit.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rooms, floor, cost, description, available, createdAt);
    }
}
