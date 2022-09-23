# Magic-Hexagon-with-Genetic-Algorithm--AI

A magic hexagon consists of numbers 1 to 19 arranged in a hexagonal pattern such that the sum of each row and diagonal(both directions) is equal to 38. The goal is to find this hexagon with the genetic algorithm.

![image](https://user-images.githubusercontent.com/84543584/191955753-fcd63b01-2d93-4e83-bb37-88496b6a3d86.png)


The genetic algorithm is modeled from 4 classes

  * A genetetic algorithm class, which has methods for selection, crossover, mutation, fitness evaluation and checking whether the termination condition is met.
  * An Individual class, which in our case it represents the hexagon,  models a magic hexagon from a given chromosome.
  * A population class, which represents the population or a generate of population, with methods such as shuffling or getting the fittest hexagon.
  * A class that has the main method, which runs the genetic algorithm
  
 
 ## Modeling a hexagon 
 
 The constructor of a hexagon takes a chromosome as an array of integer from 1 to 19 with which it builds the integer matrix shaped as the magic hexagon.
 
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
        }
  
  It is also possible  to create a hexagon with a random chromosome with numbers from 1-19. The set data structure was used since we are not allowed to have repetition of elements in the hexagon.
  
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
  
  ## Fitness calculation 
  
 The fitness of each hexagon was calculated as the distance from the number 38 of each sum of row and diagonal, and then these distances added up. If these distances are added up to zero, then we have the desired solution. So the smaller the fittness, the closer we are to the solution. In other words, if $r_i$ represents the sum of elements in the i-th row of hexagon, and $d_i$ represents the sum of elemenents in the i-th diagonal of the hexagon, then the fitness is calculated as 
 
 $$ f =  \sum_{i=1}^5  |38-r_i| + \sum_{i=1}^{10}  |38-d_i| $$
  
  ## Crossover 
  
A point to point crossover was not possible since a crossover like this could result in a hexagon with duplicate numbers, which is not allowed. Neither is an uniform crossover allowed, since a number higher than 19 or 0 can be obtained, also not allowed. This is why ordered crossover was used. First a random subset of the parent chromosome is chosen. This chromosome will be copied to the child chromosome in the same position. Next we iterate from the end position of that random substet in the second parent gene, and fill the child chromosome with elements in the second parent gene that aren't already in the child chromosome. 
  
  ![image](https://user-images.githubusercontent.com/84543584/191964556-4b97df46-c69e-4ccf-a6fc-867b98656540.png)


## Population mutation 

The mutation method takes an elitism parameter, which is a condition used to not mutate the fittest hexagons of the populaton. For example, if elitism is : 5, it means don't mutate the 5 fittest hexagons in the population. It also takes a mutationRate, which represents the chance of gene a hexagon in the population getting mutated.
So higher the mutationRate, the higher are the chances a hexagon getting mutated. A mutation was performed as simply swapping two random genes of the chromosones of the individiual (hexagon). The pseudo code for population mutation goes:
  
        1:  For  popluationIndex to population size
        3:          hexagon = population.getFittest(populationIndex);
        2:          If (populationIndex > elitism) then
        3:           For each gene of hexagon do 
        4:                  If(mutationRate > random()) then
        5:                                newGenePos = random()*chromosome.length;
        6:                                gene1 = hexagon.getGene(newGenePos)
        7:                               gene2 = hexagon.getGene(currentGene) 
        9:                               swap gene1 and gene2 of the hexagon
        9:                  End If;
        10:           End Loop;
        11:         End If;
        12:  End Loop;
        
## Running the genetic algorithm
 
First a genetic algorithm is insantiated with a population size, mutation rate, crossover rate, and eleitisim count. A population is initialized by the genetic algorithm which randomly creates hexagons. Each hexagon's fitness is evaluated and set in evalPopulation method. We enter a while loop until the we find the solution. While in the loop, we crossover, mutate and evaluate the population. We print the fittest hexagon in each iteration. 

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
                // print
                System.out.println(
                        "Best Solution" + population.getFittest(0) + "Fittnes " + population.getFittest(0).getFitness());
                generation++;
                System.out.println("new generation ---------------------------" + generation);
            }
         }
        
## Results and future work

The best hexagon with the best fittness that has been obtained so far is in the 272908-th generation (you read that right). This is because it seems that the algorithm is stuck in a local minima and that's why backtracking methods should be implemented.

![image](https://user-images.githubusercontent.com/84543584/191972457-0a39585b-0acd-443d-9ce1-53f130ff59e4.png)

Although the fitness is 8, it is a close solution since the sum of rows and diagonals is very close to 8, as demonstrated below:
![image](https://user-images.githubusercontent.com/84543584/191973105-5fd5b8f8-fde5-434e-9e44-0dfcd49f480f.png)

