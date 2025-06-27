package homes.sinenko.booking.dto.setting;

import homes.sinenko.booking.entity.Setting;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SettingsMapperTest {

    @Test
    void toDto_shouldMapEntityToDto() {
        Setting setting = Setting.builder()
                .id(1L)
                .key("testKey")
                .value("testValue")
                .build();

        SettingDto dto = SettingsMapper.toDto(setting);

        assertEquals(setting.getId(), dto.id());
        assertEquals(setting.getKey(), dto.key());
        assertEquals(setting.getValue(), dto.value());
    }

    @Test
    void toDtoList_shouldMapEntityListToDtoList() {
        Setting setting1 = Setting.builder().id(1L).key("key1").value("val1").build();
        Setting setting2 = Setting.builder().id(2L).key("key2").value("val2").build();
        List<Setting> settings = List.of(setting1, setting2);

        List<SettingDto> dtos = SettingsMapper.toDtoList(settings);

        assertEquals(2, dtos.size());
        assertEquals("key1", dtos.get(0).key());
        assertEquals("key2", dtos.get(1).key());
    }

    @Test
    void toEntity_shouldMapRequestToEntity() {
        CreateSettingRequest request = new CreateSettingRequest("someKey", "someValue");

        Setting entity = SettingsMapper.toEntity(request);

        assertNull(entity.getId());
        assertEquals(request.key(), entity.getKey());
        assertEquals(request.value(), entity.getValue());
    }
}

