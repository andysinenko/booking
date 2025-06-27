package homes.sinenko.booking.dto.setting;

import homes.sinenko.booking.entity.Setting;

import java.util.List;

public class SettingsMapper {
    public static SettingDto toDto(Setting setting) {
        return SettingDto.builder()
                .id(setting.getId())
                .key(setting.getKey())
                .value(setting.getValue())
                .build();
    }

    public static List<SettingDto> toDtoList(List<Setting> settings) {
        return settings.stream()
                .map(e -> SettingsMapper.toDto(e))
                .toList();
    }

    public static Setting toEntity(CreateSettingRequest request) {
        return Setting.builder()
                .key(request.key())
                .value(request.value())
                .build();
    }

}
