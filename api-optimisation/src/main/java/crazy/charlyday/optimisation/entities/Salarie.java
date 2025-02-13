package crazy.charlyday.optimisation.entities;

import java.util.Map;

public record Salarie(String name, Map<SkillType, Integer> competences) { }
