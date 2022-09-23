import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
    private Hexagon population[];
    private double popoulationFitness = -1;

    Population(int populationSize) {
        this.population = new Hexagon[populationSize];
        for (int hexCount = 0; hexCount < populationSize; hexCount++) {
            this.population[hexCount] = new Hexagon();
        }
    }

    public Hexagon[] getHexagons() {
        return this.population;
    }

    public Hexagon getFittest(int offset) {
        Arrays.sort(population, new Comparator<Hexagon>() {
            @Override
            public int compare(Hexagon o1, Hexagon o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return 1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return -1;
                }
                return 0;
            }

        });

        return population[offset];
    }

    public void setPopulationFitness(double fittnes) {
        this.popoulationFitness = fittnes;
    }

    public double getPopoulationFitness() {
        return popoulationFitness;
    }

    public int size() {
        return this.population.length;
    }

    public void setHexagon(int offset, Hexagon hexagon) {
        population[offset] = hexagon;
    }

    public Hexagon getHexagon(int offset) {
        return population[offset];
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Hexagon temp = population[index];
            population[index] = population[i];
            population[i] = temp;
        }
    }
}
