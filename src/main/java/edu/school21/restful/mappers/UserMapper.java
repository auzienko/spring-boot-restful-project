package edu.school21.restful.mappers;

import edu.school21.restful.dto.UserDto;
import edu.school21.restful.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private  ModelMapper mapper;


    public User toEntity(UserDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, User.class);
    }


    public  UserDto toDto(User entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, UserDto.class);
    }

//    @PostConstruct
//    public void setupMapper() {
//        mapper.createTypeMap(User.class, UserDto.class)
//                .addMappings(m -> m.skip(UserDto::setUnicornId)).setPostConverter(toDtoConverter());
//        mapper.createTypeMap(UserDto.class, User.class)
//                .addMappings(m -> m.skip(User::setUnicorn)).setPostConverter(toEntityConverter());
//    }
}
