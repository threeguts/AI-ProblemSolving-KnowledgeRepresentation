public class Main {
    public static void main(String[] args){
        Schedule s = new Schedule();
        
        GeneticAlgorithm algorithm = new GeneticAlgorithm();
        Schedule solution = algorithm.run(300, 0.04, 1000, 7);
        solution.write();
    }
}
