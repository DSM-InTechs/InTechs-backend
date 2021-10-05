package InTechs.InTechs.comment.repository;

import InTechs.InTechs.comment.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
