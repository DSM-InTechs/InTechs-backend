package InTechs.InTechs.project.repository;

import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.user.entity.User;

import java.util.List;

public interface CustomProjectRepository {
    void projectUpdate(int projectId, String name);
    void projectUpdate(int projectId, Image image);
    void addProjectUser(int projectId, List<User> users);
}
