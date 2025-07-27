package homes.sinenko.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events", schema = "booking")
@Getter @Setter @ToString @Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
    @SequenceGenerator(name = "events_seq", sequenceName = "events_seq", allocationSize = 1)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @CreatedDate
    private LocalDateTime eventTime;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && eventType == event.eventType && Objects.equals(eventTime, event.eventTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventType, eventTime);
    }
}
