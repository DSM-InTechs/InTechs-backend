package InTechs.InTechs.repository.project;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class CustomProjectRepositoryImpl implements CustomProjectRepository{
    private final MongoTemplate mongoTemplate;
}
