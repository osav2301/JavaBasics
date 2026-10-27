package coursehub.practice3.task10;

public class Main {
    public static void main(String[] args)
    {
        // CompileError err = new CompileError();
        //./src/task10/CompileError.java:3: error: types Interface1 and Interface2 are incompatible;
        // public class CompileError implements Interface1, Interface2 {
        //     class CompileError inherits unrelated defaults for
        //         helloWorld() from types Interface1 and Interface2 1 error
        WorkingImplementation fine = new WorkingImplementation();
        fine.helloWorld();
    }
}
