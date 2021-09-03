package InTechs.InTechs.repository.project;

import InTechs.InTechs.entity.Image;
import InTechs.InTechs.entity.User;

import java.util.List;

public interface CustomProjectRepository {
    void projectUpdate(int projectId, String name);
    void projectUpdate(int projectId, Image image);
    void addProjectUser(int projectId, List<User> users);
}
