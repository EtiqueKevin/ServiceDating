package crazy.charlyday.optimisation.config;

import crazy.charlyday.optimisation.interfaces.Solver;
import crazy.charlyday.optimisation.interfaces.SolverFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolverConfig {
    @Bean
    public Solver solver() {
        return SolverFactory.getSolver();
    }
}
