package core.entities;

import org.neo4j.ogm.annotation.GraphId;

public abstract class Entity {
    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }
}
