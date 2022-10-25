package edu.school21.restful.mappers;

import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.dto.UserDto;
import edu.school21.restful.models.Lesson;
import edu.school21.restful.models.User;
import edu.school21.restful.repositories.LessonRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper mapper;



    public Lesson toEntity(LessonDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Lesson.class);
    }


    public LessonDto toDto(Lesson entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, LessonDto.class);
    }


}
