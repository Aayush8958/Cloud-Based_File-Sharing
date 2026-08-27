package File.Sharing.platform.File.Sharing.AppUser;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface Todto {
    UserDto toUserDto(AppUser appUser);
}
