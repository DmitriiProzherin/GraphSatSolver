package com.sat.graphsatsolver.solvers;

import com.sat.graphsatsolver.structures.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class DPLL extends Solver{
    String result;

    boolean status = false;

    @Override
    public boolean solve() {
        Deque<Function> stack = new ArrayDeque<>();
        stack.addFirst(function);
        while (!stack.isEmpty()) {
            var currentFunction = stack.removeFirst();
            currentFunction.unitPropagation();
            if (currentFunction.isEmpty()) {
                status = true;
                result = currentFunction.printVariablesAssignmentAsString();
                return true;
            }
            if (!currentFunction.hasEmptyClause()) {
                // Выбор литерала
                Literal literal = currentFunction.chooseLiteral();
                // Левая ветка
                Function leftChildFunction = currentFunction.copy().add(new Clause(literal));
                stack.addFirst(leftChildFunction);
                // Правая ветка с обратным литералом
                Function rightChildFunction = currentFunction.copy().add(new Clause(literal.reverse()));
                stack.addFirst(rightChildFunction);
            }
        }
        return false;
    }

    public String result(){
        return status ? result : "UNSAT";
    }
}
