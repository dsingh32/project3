package simplf;
 
import java.util.List;

class SimplfFunction implements SimplfCallable {
    Stmt.Function declaration;
    Environment closure;

    SimplfFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    public void setClosure(Environment environment) {
        this.closure = environment;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            Token param = declaration.params.get(i);
            environment.define(param, param.lexeme, args.get(i));
        }
        
        Environment previous = interpreter.environment;
        interpreter.environment = environment;
        Object result = null;
        try {
            for (Stmt stmt : declaration.body) {
                Object val = interpreter.execute(stmt);
                if (val != null) {
                    result = val;
                }
            }
        } finally {
            interpreter.environment = previous;
        }
        return result;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + ">";
    }

}