import java.util.*;

/**
 * Hexagon
 */
public class Hexagon {

    private int[] chromosome = new int[19];
    private double fitness = -1;
    private int[][] matrixHexagon;
    private int a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s;

    Hexagon(int[][] matrixHexagon) {
        this.matrixHexagon = matrixHexagon;
        init();
        initChromosome();
    }

    Hexagon(int[] chromosome) {
        int[][] model = {
                { 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0 } };

        int count = 0;
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                model[i][j] = chromosome[count];
                count+=1;
            }
        }
        this.chromosome = chromosome;
        matrixHexagon = model;
        init();
    }

    Hexagon() {
        Set<Integer> elementSet = new LinkedHashSet<Integer>(19);
        int[][] model = {
                { 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0 } };

        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                model[i][j] = (int) (Math.random() * 19 + 1);
                while (elementSet.contains((model[i][j]))) {
                    model[i][j] = (int) (Math.random() * 19 + 1);
                }
                elementSet.add(((model[i][j])));
            }
        }

        matrixHexagon = model.clone();
        init();
        initChromosome();
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int getChromosomeLength() {
        return chromosome.length;
    }

    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }

    public int getGene(int offset) {
        return chromosome[offset];

    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public int[][] getMatrixHexagon() {
        return matrixHexagon;
    }

    private void initChromosome() {
        int counter = 0;
        for (int i = 0; i < matrixHexagon.length; i++) {
            for (int j = 0; j < matrixHexagon[i].length; j++) {
                chromosome[counter] = matrixHexagon[i][j];
                counter += 1;
            }
        }

    }

    private void init() {
        a = matrixHexagon[0][0];
        b = matrixHexagon[0][1];
        c = matrixHexagon[0][2];
        d = matrixHexagon[1][0];
        e = matrixHexagon[1][1];
        f = matrixHexagon[1][2];
        g = matrixHexagon[1][3];
        h = matrixHexagon[2][0];
        i = matrixHexagon[2][1];
        j = matrixHexagon[2][2];
        k = matrixHexagon[2][3];
        l = matrixHexagon[2][4];
        m = matrixHexagon[3][0];
        n = matrixHexagon[3][1];
        o = matrixHexagon[3][2];
        p = matrixHexagon[3][3];
        q = matrixHexagon[4][0];
        r = matrixHexagon[4][1];
        s = matrixHexagon[4][2];
    }

    @Override
    public String toString() {
        String str = String.format(
                "\n    %3d %3d %3d\n  %3d %3d %3d %3d \n%3d %3d %3d %3d %3d\n  %3d %3d %3d %3d\n    %3d %3d %3d\n",
                a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s);
        return str;
    }

}
