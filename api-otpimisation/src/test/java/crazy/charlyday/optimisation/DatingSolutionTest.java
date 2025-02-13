package crazy.charlyday.optimisation;

import crazy.charlyday.optimisation.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatingSolutionTest {
    @Order(1)
    @DisplayName("DATING_SOLUTION_TEST_TO_CSV")
    @Test
    void toCsvTest() {
        Salarie salarie1 = new Salarie("Charlotte", Map.of(SkillType.IF, 1));
        Salarie salarie2 = new Salarie("Alice", Map.of(SkillType.MN, 1));
        Salarie salarie3 = new Salarie("Bernard", Map.of(SkillType.BR, 1));

        Client client1 = new Client("Cedric_C", List.of(new Besoin("Cedric_C", List.of(SkillType.IF))));
        Client client2 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of(SkillType.MN))));
        Client client3 = new Client("Antoine_C", List.of(new Besoin("Antoine_C", List.of(SkillType.BR))));

        LinkedHashMap<Salarie, Besoin> map = new LinkedHashMap<>();
        map.put(salarie1, client1.besoins().getFirst());
        map.put(salarie2, client2.besoins().getFirst());
        map.put(salarie3, client3.besoins().getFirst());

        DatingSolution solution = new DatingSolution(6, map);

        String csv = solution.toCsv();

        String expectedCsv = """
                6;
                Charlotte;IF;Cedric_C;
                Alice;MN;Antoine_C;
                Bernard;BR;Antoine_C;
                """;

        assertEquals(expectedCsv, csv);
    }

}
