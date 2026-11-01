package coursehub;

import java.util.List;

public class ModularCourse extends Course {
    private List<String> modules;

    public ModularCourse(long id, String title, int duration, List<String> modules){
        super(id, title, duration);
        this.modules = modules;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }
}