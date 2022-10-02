import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class main {
    public static void main(String[] args) {
        LocalDateTime dataHoraNascimento = LocalDateTime.of(1996,11, 25, 13, 35);//Estava no horário de verão
        String localNascimento = "America/Sao_Paulo";
        imprimirInfos(dataHoraNascimento, localNascimento);

        dataHoraNascimento = LocalDateTime.of(1994,07, 25, 18, 30);
        localNascimento = "America/Recife";
        imprimirInfos(dataHoraNascimento, localNascimento);


        dataHoraNascimento = LocalDateTime.of(1988,01, 30, 10, 50);
        localNascimento = "America/Cuiaba";
        imprimirInfos(dataHoraNascimento, localNascimento);


        dataHoraNascimento = LocalDateTime.of(1991,12, 9, 1, 50);
        localNascimento = "America/New_York";
        imprimirInfos(dataHoraNascimento, localNascimento);
    }

    public static String calcularIdade(LocalDate dataNascimento){
        return Period.between(LocalDate.now(), dataNascimento).toString().substring(2);
    }

    public static String verificarAnoBissexto(Year ano) {
        boolean foiBissexto = ano.isLeap();

        if (foiBissexto) return "Sim";
        else return "Não";
    }

    public static String formatarData (LocalDateTime data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(data);
    }

    public static ZoneOffset verificarTimeZone(String local, LocalDateTime dataHora) {
        ZoneId zoneId= ZoneId.of(local);
        return zoneId.getRules().getOffset(dataHora);
    }

    public static String verificarSigno(MonthDay aniversario) {
        MonthDay leaoComecaEm = MonthDay.of(7,22);
        MonthDay leaoTerminaEm = MonthDay.of(8,23);

        MonthDay sagitarioComecaEm = MonthDay.of(11,21);
        MonthDay sagitarioTerminaEm = MonthDay.of(12,22);

        if (verificarSeEstaEntreDatas(aniversario, leaoComecaEm, leaoTerminaEm)) return "Leão";

        if (verificarSeEstaEntreDatas(aniversario, sagitarioComecaEm, sagitarioTerminaEm)) return "Sagitário";

        return "Ainda não foi cadastrado um signo para a data informada";
    }


    private static boolean verificarSeEstaEntreDatas(MonthDay dataParaVerificar, MonthDay dataInicio, MonthDay dataFim) {
        return !(dataParaVerificar.isBefore(dataInicio) || dataParaVerificar.isAfter(dataFim)) ;
    }

    public static String ascendente(String signo, LocalTime horarioDeNascimento) {
        if ("Leão".equalsIgnoreCase(signo)) {
            if (verificarSeEstaEntreHorarios(horarioDeNascimento, LocalTime.of(18, 0), LocalTime.of(19, 59))) return "Áries";
        }
        if ("Sagitário".equalsIgnoreCase(signo)) {
            if (verificarSeEstaEntreHorarios(horarioDeNascimento, LocalTime.of(12, 0), LocalTime.of(13, 59))) return "Peixes";
        }
        return "Ascendente não encontrado";
    }

    private static boolean verificarSeEstaEntreHorarios(LocalTime horaParaVerificar, LocalTime horaInicio, LocalTime horaFim) {
        return !(horaParaVerificar.isBefore(horaInicio) || horaParaVerificar.isAfter(horaFim)) ;
    }

    public static String signoLunar (String localNascimento, LocalTime horaNascimento){

        if (localNascimento == "America/Sao_Paulo") return "Gandalf";

        if (localNascimento == "America/Recife" && horaNascimento.isAfter(LocalTime.of(12, 0))) return "Casimiro";

        if (localNascimento == "America/Cuiaba" && horaNascimento.isBefore(LocalTime.of(12, 0))) return "Odin";

        return "Em construção";
    }

    private static void imprimirInfos(LocalDateTime dataNascimento, String localNascimento) {
        System.out.println("Data e hora de nascimento: " + formatarData(dataNascimento));
        System.out.println("Idade: " + calcularIdade(LocalDate.from(dataNascimento)));
        System.out.println("O ano foi bissexto? " + verificarAnoBissexto(Year.from(dataNascimento)));
        System.out.println("TZ: " + verificarTimeZone(localNascimento, dataNascimento));

        String signo = verificarSigno(MonthDay.from(dataNascimento));
        System.out.println("Signo: " + signo);
        System.out.println("Signo ascendente: " + ascendente(signo, LocalTime.from(dataNascimento)));
        System.out.println("Signo Lunar: " + signoLunar(localNascimento, LocalTime.from(dataNascimento)));

        System.out.println();
    }
}

