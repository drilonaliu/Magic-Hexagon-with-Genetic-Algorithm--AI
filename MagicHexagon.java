public class MagicHexagon {
    public static void main(String[] args) {

        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.5, 0.95, 13);
        Population population = ga.initPopulation();
        ga.evalPopulation(population);
        
        int generation = 1;
        while (!ga.isTerminationConditionMet(population)) {

            // Crossover
            population = ga.crossOverPopulation(population);
            // Mutation
            population = ga.mutatePopulation(population);
            // eval population
            ga.evalPopulation(population);

            System.out.println(
                    "Best Solution" + population.getFittest(0) + "Fittnes " + population.getFittest(0).getFitness());
            generation++;
            System.out.println("new genaration ---------------------------" + generation);

        }

        Hexagon hexfit = population.getFittest(0);
        System.out.println("Found solution in " + generation + "generations");
        System.out.println("Best solution: " + hexfit);
        System.out.println("With fitness:" + hexfit.getFitness());

    }

}
