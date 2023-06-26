package com.sat.graphsatsolver.utils;

public class Text {

    // Описание КНФ
    public static final String CNF_TITLE = "Конъюнктивная нормальная форма (КНФ):";
    public static final String CNF_DESCRIPTION = "КНФ - это способ представления логической формулы " +
            "в виде конъюнкций дизъюнкций литералов. Литерал - булева переменная или её отрицание.";
    public static final String CNF_EXAMPLE_TITLE = "Пример КНФ:";
    public static final String CNF_EXAMPLE_DESCRIPTION = "(A ∨ B) ∧ (¬C ∨ D) ∧ (E ∨ F)";
    public static final String DIMACS_TITLE = "Формат DIMACS (DIMACS-CNF):";
    public static final String DIMACS_DESCRIPTION = "DIMACS CNF - это текстовое представление формулы в КНФ. " +
            "Файлы имеют расширение '.cnf'. " +
            "В этом формате положительные целые числа используются " +
            "для представления переменных и их отрицание для представления их отрицания.\n" +
            "Строка, начинающаяся с символа 'p' последовательно содержит информацию о типе формулы ('cnf'), " +
            "количестве уникальных переменных и количестве дизъюнктов. Строки, начинающиеся с символа 'c' содержат комментарии. " +
            "Все остальные строки содержат дизъюнкты формулы (1 строка - 1 дизъюнкт), в которых литералы разделены пробелом. " +
            "Строка заканчивается числом '0'.";
    public static final String DIMACS_EXAMPLE_TITLE = "Пример КНФ в формате DIMACS:";
    public static final String DIMACS_EXAMPLE_DESCRIPTION = """
            p cnf 6 3
            1 2 0
            -3 4 0
            5 6 0
            """;

    // Задача SAT
    public static final String SAT_TITLE = "Задача SAT:";
    public static final String SAT_DESCRIPTION = "";

}
