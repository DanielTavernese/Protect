package ca.dantav.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager {

    private List<Entity> entityList = new ArrayList<Entity>();

    public void register(Entity e) {
        entityList.add(e);
        e.register();
    }

    public void unregister(Entity e) {
        entityList.remove(e);
        e.unregister();
    }

    public List<Entity> getEntityList() {
        return Collections.unmodifiableList(new ArrayList<>(entityList));
    }

    public List<Attacker> getAttackers() {
        List<Attacker> attackers = new ArrayList<Attacker>();
        getEntityList().stream().filter((Entity e) -> e instanceof Attacker).forEach((Entity e) -> attackers.add((Attacker) e));
        return attackers;
    }

}
