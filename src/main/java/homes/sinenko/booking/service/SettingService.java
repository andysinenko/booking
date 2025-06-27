package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.setting.CreateSettingRequest;
import homes.sinenko.booking.dto.setting.SettingsMapper;
import homes.sinenko.booking.dto.setting.SettingDto;
import homes.sinenko.booking.entity.Setting;
import homes.sinenko.booking.exception.SettingAlreadyExistsException;
import homes.sinenko.booking.exception.SettingNotExistsException;
import homes.sinenko.booking.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository repository;

    public SettingDto create(CreateSettingRequest createSettingRequest) {
        Setting setting = SettingsMapper.toEntity(createSettingRequest);
        if (repository.existsByKey(setting.getKey())) {
            throw new SettingAlreadyExistsException(String.format("Setting with this key %s already exists", setting.getKey()));
        }
        return SettingsMapper.toDto(repository.save(setting));
    }


    public SettingDto updateSetting(Long id, CreateSettingRequest createSettingRequest) {
        var setting = repository.findById(id).orElseThrow(() -> new SettingNotExistsException(String.format("Setting with id %s not found", id)));

        setting.setKey(createSettingRequest.key());
        setting.setValue(createSettingRequest.value());

        return SettingsMapper.toDto(repository.save(setting));
    }

    public void deleteSetting(Long id) {
        repository.deleteById(id);
    }

    public SettingDto getById(Long id) {
        return repository.findById(id)
                .map(e -> SettingsMapper.toDto(e))
                .orElseThrow(() -> new IllegalArgumentException("Setting not found"));
    }

    public List<SettingDto> getAllSettings() {
        return repository.findAll().stream()
                .map(e -> SettingsMapper.toDto(e))
                .toList();
    }

    public SettingDto getByKey(String key) {
        return repository.findByKey(key)
                .map(e -> SettingsMapper.toDto(e))
                .orElseThrow(() -> new SettingNotExistsException(String.format("Setting with key %s not found", key)));
    }
}
