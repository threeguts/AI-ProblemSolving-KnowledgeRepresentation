class Term {
    String name;
    boolean isVariable;

    Term(String name, boolean isVariable) {
        this.name = name;
        this.isVariable = isVariable;
    }

    Term(String name) {
        this.name = name;
        if (name.length() == 1) {
            // The string is made up of one character...
            this.isVariable = true;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}