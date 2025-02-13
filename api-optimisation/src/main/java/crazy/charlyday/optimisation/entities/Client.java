package crazy.charlyday.optimisation.entities;

import java.util.List;

public record Client(String name, List<Besoin> besoins) {
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Client client) {
            if(client.name().equals(name)) return true;
        }
        return false;
    }
}
