package InTechs.InTechs.repository.project;

import InTechs.InTechs.entity.Image;

public interface CustomProjectRepository {
    void projectUpdate(int projectId, String name);
    void projectUpdate(int projectId, Image image);
}
