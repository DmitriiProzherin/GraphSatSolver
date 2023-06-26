package com.sat.graphsatsolver.utils;

public class Text {

    // Описание КНФ
    public static final String CNF_TITLE = "Конъюнктивная нормальная форма (КНФ)";
    public static final String CNF_DESCRIPTION = "  КНФ - это способ представления логической формулы " +
            "в виде конъюнкций дизъюнкций литералов. Литерал - булева переменная или её отрицание.";
    public static final String CNF_EXAMPLE_TITLE = "Пример КНФ";
    public static final String CNF_EXAMPLE_DESCRIPTION = "(A ∨ B) ∧ (¬C ∨ D) ∧ (E ∨ F)";
    public static final String DIMACS_TITLE = "Формат DIMACS (DIMACS-CNF)";
    public static final String DIMACS_DESCRIPTION = "   DIMACS CNF - это текстовое представление формулы в КНФ. " +
            "Файлы имеют расширение '.cnf'. " +
            "В этом формате положительные целые числа используются " +
            "для представления переменных и их отрицание для представления их отрицания.\n" +
            "   Строка, начинающаяся с символа 'p' последовательно содержит информацию о типе формулы ('cnf'), " +
            "количестве уникальных переменных и количестве дизъюнктов. Строки, начинающиеся с символа 'c' содержат комментарии. " +
            "Все остальные строки содержат дизъюнкты формулы (1 строка - 1 дизъюнкт), в которых литералы разделены пробелом. " +
            "Строка заканчивается числом '0'.";
    public static final String DIMACS_EXAMPLE_TITLE = "Пример КНФ в формате DIMACS";
    public static final String DIMACS_EXAMPLE_DESCRIPTION = """
            p cnf 6 3
            1 2 0
            -3 4 0
            5 6 0
            """;

    // Задача SAT
    public static final String SAT_TITLE = "Задача SAT";
    public static final String SAT_DESCRIPTION = "  Задача заключается в следующем: можно ли назначить всем переменным, " +
            "встречающимся в формуле, значения ложь и истина так, чтобы формула стала истинной.\n" +
            "   Программы, которые решают эту задачу называются решатели SAT или SAT-солверы. На вход подаётся формула в КНФ в формате DIMACS.";


    // Раскраска графа
    public static final String GRAPH_COLORING_TITLE = "Раскраска графа";
    public static final String GRAPH_COLORING_DESCRIPTION = "   Это теоретико-графовая конструкция, частный случай разметки графа." +
            "При раскраске элементам графа ставятся в соответствие метки с учётом определённых ограничений. " +
            "Эти метки традиционно называются «цветами».\n" +
            "   В простейшем случае такой способ окраски вершин графа, " +
            "при котором любым двум смежным вершинам соответствуют разные цвета, называется раскраской вершин.";

    // Гамильтонов путь
    public static final String HP_TITLE = "Гамильтонов путь";
    public static final String HP_DESCRIPTION = "   Гамильтонов путь - это простой путь (без петель), " +
            "проходящий через каждую вершину графа ровно один раз.\n" +
            "   Задача определения, существует ли гамильтонов путь в заданном графе, в общем случае, является NP-полной.";

    // DPLL
    public static final String DPLL_TITLE = "Алгоритм DPLL";
    public static final String DPLL_IO = "Входные данные: Набор дизъюнкт формулы Φ.\n" +
            "Выходные данные: Значение истинности";
    public static final String DPLL_DESCRIPTION = "function DPLL(Φ)\n" +
            "   если Φ - это набор выполняющихся дизъюнкт\n" +
            "       тогда return true;\n" +
            "   если Φ содержит пустую дизъюнкту\n" +
            "       then return false;\n" +
            "   для каждой дизъюнкты из одной переменной l в Φ\n" +
            "      Φ ← unit-propagate(l, Φ);\n" +
            "   для каждой переменной l которая встречается в чистом виде в Φ\n" +
            "      Φ ← pure-literal-assign(l, Φ);\n" +
            "   l ← choose-literal(Φ);\n" +
            "   return DPLL(Φ&l) or DPLL(Φ&not(l));";


    // CDCL
    public static final String CDCL_TITLE = "Алгоритм CDCL";
    public static final String CDCL_DESCRIPTION = "   Гамильтонов путь - это простой путь (без петель), " +
            "проходящий через каждую вершину графа ровно один раз.\n" +
            "   Задача определения, существует ли гамильтонов путь в заданном графе, в общем случае, является NP-полной.";



}
