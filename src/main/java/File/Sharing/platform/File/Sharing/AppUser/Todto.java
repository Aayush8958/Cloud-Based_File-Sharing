package File.Sharing.platform.File.Sharing.AppUser;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Todto {
    UserDto toUserDto(AppUser appUser);
}
