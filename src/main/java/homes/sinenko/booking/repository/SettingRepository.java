package homes.sinenko.booking.repository;

import homes.sinenko.booking.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByKey(String key);
    boolean existsByKey(String key);
}
