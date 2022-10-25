package edu.school21.restful.configs;

import edu.school21.restful.dto.LessonDto;
import edu.school21.restful.models.Lesson;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        setTypeMap(mapper);
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE)
                .setPropertyCondition(Conditions.isNotNull())
                .setAmbiguityIgnored(true);
        return mapper;
    }

    public void setTypeMap(ModelMapper modelMapper){
        modelMapper.createTypeMap(LessonDto.class,Lesson.class).addMappings(mapper -> mapper.skip(Lesson::setId));
    }


}
