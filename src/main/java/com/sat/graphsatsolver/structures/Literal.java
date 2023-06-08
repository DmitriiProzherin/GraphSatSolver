package com.sat.graphsatsolver.structures;

import java.util.Objects;

public final class Literal {
    private final Variable variable;
    private final boolean sign;

    public Literal(Variable variable, boolean sign) {
        this.variable = variable;
        this.sign = sign;
    }

    public Literal(Integer integer) {
        assert integer != 0 : "Ноль не может быть литералом";
        this.variable = new Variable(Math.abs(integer));
        this.sign = Integer.signum(integer) == 1;
    }

    public Literal reverse() {
        return new Literal(this.variable, (!this.sign));
    }

    public Variable getVariable() {
        return variable;
    }


    public boolean getSign() {
        return sign;
    }

    public Literal copy() {
        return new Literal(this.variable.copy(), this.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, sign);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Literal)) return false;

        return ((Literal) obj).sign == this.sign
                && ((Literal) obj).variable.equals(this.variable);
    }

    @Override
    public String toString() {
        return sign ? variable.toString() : "-" + variable.toString();
    }
}
