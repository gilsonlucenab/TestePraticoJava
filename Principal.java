import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    String nome;
    LocalDate dataNascimento;

    Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}

class Funcionario extends Pessoa {
    BigDecimal salario;
    String funcao;

    Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }
}

public class Principal {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(List.of(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.nome.equals("João"));

        funcionarios.forEach(f -> System.out.printf("%s - %s - R$ %,.2f - %s\n", f.nome, f.dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), f.salario, f.funcao));
        funcionarios.forEach(f -> f.salario = f.salario.multiply(BigDecimal.valueOf(1.1)));

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(f -> f.funcao));
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\n" + funcao);
            lista.forEach(f -> System.out.println("- " + f.nome));
        });

        System.out.println("\nAniversariantes de Outubro e Dezembro:");
        funcionarios.stream().filter(f -> f.dataNascimento.getMonthValue() == 10 || f.dataNascimento.getMonthValue() == 12).forEach(f -> System.out.println(f.nome));

        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(f -> f.dataNascimento));
        System.out.printf("\nFuncionário mais velho: %s, %d anos\n", maisVelho.nome, LocalDate.now().getYear() - maisVelho.dataNascimento.getYear());

        funcionarios.stream().sorted(Comparator.comparing(f -> f.nome)).forEach(f -> System.out.println(f.nome));

        BigDecimal totalSalarios = funcionarios.stream().map(f -> f.salario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal de salários: R$ %,.2f\n", totalSalarios);

        System.out.println("\nSalários em relação ao salário mínimo:");
        funcionarios.forEach(f -> System.out.printf("%s ganha %.2f salários mínimos\n", f.nome, f.salario.divide(BigDecimal.valueOf(1212), 2, BigDecimal.ROUND_HALF_UP)));
    }
}