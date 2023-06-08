package com.sat.graphsatsolver.structures;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Clause implements Iterable<Literal> {

    private final ArrayList<Literal> literals;

    public Clause() {
        literals = new ArrayList<>();
    }

    public Clause(Literal literal) {
        literals = new ArrayList<>();
        literals.add(literal);
    }

    public Clause(ArrayList<Integer> list) {
        literals = new ArrayList<>();
        list.forEach(this::add);
    }

    public Clause copy() {
        Clause result = new Clause();
        this.literals.forEach(l -> result.add(l.copy()));
        return result;
    }

    public Literal get(int index) {
        return this.literals.get(index);
    }


    public boolean contains(Literal literal) {
        return this.literals.contains(literal);
    }

    public boolean containsAny(HashSet<Literal> set) {
        for (var literal : set) {
            if (this.contains(literal)) return true;
        }
        return false;
    }

    public int size() {
        return this.literals.size();
    }

    public boolean isEmpty() {
        return this.literals.isEmpty();
    }

    public void add(Literal literal) {
        literals.add(literal);
    }

    public void add(Integer integer) {
        assert integer != 0;
        literals.add(new Literal(integer));
    }


    @Override
    public Iterator<Literal> iterator() {
        return new ClauseIterator();
    }

    private class ClauseIterator implements Iterator<Literal> {

        int index;

        ClauseIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < literals.size();
        }

        @Override
        public Literal next() {
            return literals.get(index++);
        }

        @Override
        public void remove() {
            literals.remove(--index);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(literals);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Clause)) return false;

        return this.literals.equals(((Clause) obj).literals);
    }

    @Override
    public String toString() {
        return literals.stream().map(Literal::toString).collect(Collectors.joining( " "));
    }
}
