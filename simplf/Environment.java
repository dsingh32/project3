package simplf; 

class Environment {
    AssocList values;
    Environment enclosing;

    Environment() {
        values = null;
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
        this.values = null;
    }

    void define(Token varToken, String name, Object value) {
        values = new AssocList(name, value, values);
    }

    void assign(Token name, Object value) {
        AssocList current = values;
        while (current != null) {
            if (current.name.equals(name.lexeme)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    Object get(Token name) {
        AssocList current = values;
        while (current != null) {
            if (current.name.equals(name.lexeme)) {
                return current.value;
            }
            current = current.next;
        }
        
        if (enclosing != null) {
            return enclosing.get(name);
        }
        
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }
}

