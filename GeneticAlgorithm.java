
import java.util.Arrays;

public class GeneticAlgorithm {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    // constructor
    GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.populationSize = populationSize;

    }

    // population
    public Population initPopulation() {
        Population population = new Population(populationSize);
        return population;
    }

    // fittness
    public static double calcFittness(Hexagon hexagon) {

        int[][] hex = hexagon.getMatrixHexagon();

        // ROWS
        int[] rows = new int[5];
        int sum = 0;
        for (int i = 0; i < hex.length; i++) {
            sum = 0;
            for (int j = 0; j < hex[i].length; j++) {
                sum = sum + hex[i][j];
            }
            rows[i] = sum;
        }

        // Left Diagonals
        int[] left_diagonal = new int[5];
        left_diagonal[0] = (hex[0][0] + hex[1][0] + hex[2][0]);
        left_diagonal[1] = (hex[0][1] + hex[1][1] + hex[2][1] + hex[3][0]);
        left_diagonal[2] = (hex[0][2] + hex[1][2] + hex[2][2] + hex[3][1] + hex[4][0]);
        left_diagonal[3] = (hex[1][3] + hex[2][3] + hex[3][2] + hex[4][1]);
        left_diagonal[4] = (hex[2][4] + hex[3][3] + hex[4][2]);

        // Right Diagonals
        int[] right_diagonal = new int[5];
        right_diagonal[0] = (hex[2][0] + hex[3][0] + hex[4][0]);
        right_diagonal[1] = (hex[1][0] + hex[2][1] + hex[3][1] + hex[4][1]);
        right_diagonal[2] = (hex[0][0] + hex[1][1] + hex[2][2] + hex[3][2] + hex[4][2]);
        right_diagonal[3] = (hex[0][1] + hex[1][2] + hex[2][3] + hex[3][3]);
        right_diagonal[4] = (hex[0][2] + hex[1][3] + hex[2][4]);

        int n1 = 0;
        int n2 = 0;
        int n3 = 0;

        // Calculating fittness
        for (int i = 0; i < 5; i++) {
            n1 = n1 + Math.abs(rows[i] - 38);
            n2 = n2 + Math.abs(left_diagonal[i] - 38);
            n3 = n3 + Math.abs(right_diagonal[i] - 38);
        }

        int fittnes = n1 + n2 + n3;
        hexagon.setFitness(fittnes);
        return fittnes;

    }

    public void evalPopulation(Population population) {
        double popoulationFittness = 0;
        for (Hexagon hexagon : population.getHexagons()) {
            popoulationFittness += calcFittness(hexagon);
        }
        population.setPopulationFitness(popoulationFittness);
    }

    // Do we have a solution?
    public boolean isTerminationConditionMet(Population population) {
        for (Hexagon hexagon : population.getHexagons()) {
            if (hexagon.getFitness() == 0) {
                return true;
            }
        }
        return false;
    }

    public Hexagon selectParent(Population population) {

        //spin wheeel
        Hexagon[] hexagons = population.getHexagons();
        double popoulationFitness = population.getPopoulationFitness();
        double rouleteWheelPos = Math.random() * popoulationFitness;
        double spinWheel = 0;
        for (Hexagon hexagon : hexagons) {
            spinWheel += hexagon.getFitness(); /// original >
            if (spinWheel <= rouleteWheelPos) {
                return hexagon;
            }
        }

        return hexagons[hexagons.length - 1];

        // // Tournament
        // int tournamentSize = 5;
        // Population tournament = new Population(tournamentSize);
        // // Add random individuals to the tournament

        // population.shuffle();
        // for (int i = 0; i < tournamentSize; i++) {

        // Hexagon tournamentIndividual = population.getHexagon(i);
        // tournament.setHexagon(i, tournamentIndividual);
        // }
        // // Return the best
        // return tournament.getFittest(0);
    }

    //Crossover
    public Population crossOverPopulation(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {

            Hexagon parent1 = population.getFittest(populationIndex);

            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {

                Hexagon parent2 = selectParent(population);
                int[] newChromosome = new int[19];
                Arrays.fill(newChromosome, -1);

                int substrPos1 = (int) (Math.random() * 19);
                int substrPos2 = (int) (Math.random() * 19);

                int startSubstr = Math.min(substrPos1, substrPos2);
                int endSubstr = Math.max(substrPos1, substrPos2);

                for (int i = startSubstr; i < endSubstr; i++) {
                    newChromosome[i] = parent1.getGene(i);
                }

                for (int i = 0; i < newChromosome.length; i++) {
                    int parent2Gene = i + endSubstr;
                    if (parent2Gene >= 19) {
                        parent2Gene -= 19;
                    }

                    if (!containsGene(newChromosome, parent2.getGene(parent2Gene))) {
                        for (int j = 0; j < newChromosome.length; j++) {
                            if (newChromosome[j] == -1) {
                                newChromosome[j] = parent2.getGene(parent2Gene);
                                break;
                            }
                        }
                    }

                }

                Hexagon offspring = new Hexagon(newChromosome);
                newPopulation.setHexagon(populationIndex, offspring);
            }

            else {
                newPopulation.setHexagon(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    //mutation
    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(populationSize);

        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            Hexagon hexagon = population.getFittest(populationIndex);
            if (populationIndex >= elitismCount) {
                for (int geneIndex = 0; geneIndex < hexagon.getChromosomeLength(); geneIndex++) {
                    if (mutationRate > Math.random()) {
                        int newGenePos = (int) (Math.random() * hexagon.getChromosomeLength());

                        int gene1 = hexagon.getGene(newGenePos);
                        int gene2 = hexagon.getGene(geneIndex);

                        hexagon.setGene(geneIndex, gene1);
                        hexagon.setGene(newGenePos, gene2);
                    }
                }

            }

            newPopulation.setHexagon(populationIndex, hexagon);
        }

        return newPopulation;
    }

    public boolean containsGene(int[] chromosome, int gene) {
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == gene) {
                return true;
            }

        }
        return false;
    }
}
