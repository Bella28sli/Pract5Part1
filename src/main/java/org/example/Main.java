package org.example;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static double treasury = 0;
    static  int reputation = 100;
    static String[] royalFamily = new String[]{"Его Величество Эрик", "Её Величество Аврелия", "Её Высочество Лорсулия", "Её Высочество Азалия", "Её Высочество Седани"}; //массив семьи
    static ArrayList<Object> fairies = new ArrayList<>(); //
    static ArrayList<Object> guard = new ArrayList<>();//
    static ArrayList<Object> residents = new ArrayList<>();
    static ArrayList<Object> jail = new ArrayList<>();
    static String[] weather = new String[]{"солнечно.", "пасмурно.", "льёт как из ведра.", "ветер сильный", "снега намело", "жара невыносимая"}; //


    public static void main(String[] args) {
        Welcome();
    }

    static void Welcome() {
        if (reputation <=20 && treasury <=10){
            System.out.println("Народ бунтует, а казна пуста...\nВаше Величество, Вы не справились с управлением замком. Игра окончена.");
            System.exit(0);
        }
        Main main = new Main();
        String choice;
        Scanner sc = new Scanner(System.in); // подключаем сканер
        System.out.println("""
                
                Моё почтение, Ваше Величество. Какой указ вы отдатите?
                1. Созвать совет\s
                2. Поужинать с семьёй\s
                3. Исполнить желания народа\s
                4. Наказать за непослушание\s
                5. Пересчитать казну\s
                6. Заплатить феям за уборку\s
                7. Заплатить оркам за защиту замка""");

        choice = sc.nextLine();
        switch (choice) {
            case "1" -> main.Council();
            case "2" -> {
                try
                {
                    for (int i = 0; i < royalFamily.length; i++) {
                        if (i != 0) {
                            System.out.println("\n" + royalFamily[i] + " приглашена на ужин");
                        } else {
                            System.out.println("\n" + royalFamily[i] + " приглашён на ужин");
                        }
                        Thread.sleep(1000);
                    }
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                FamilyDinner();
            }
            case "3" -> main.Wishes();
            case "4" -> {
                System.out.println("\nКого наказать?\n1. Повара\n2. Фею\n3. Орка");
                choice = sc.nextLine();
                switch (choice) {
                    case "1" -> {
                        if(residents.contains("Повар")){
                            int id = residents.indexOf("Повар");
                            main.Punishment(id);
                        }
                        else{
                            System.out.println("\nВ Вашем замке нет ни одного повара");
                        }
                    }
                    case "2" -> {
                        if(residents.contains("Фея")){
                            int id = residents.indexOf("Фея");
                            main.Punishment(id);
                        }
                        else{
                            System.out.println("\nВ Вашем замке нет ни одной феи");
                        }
                    }
                    case "3" -> {
                        if(residents.contains("Орк")){
                            int id = residents.indexOf("Орк");
                            main.Punishment(id);
                        }
                        else{
                            System.out.println("\nВ Вашем замке нет ни одного орка");
                        }
                    }
                    case null, default -> {
                        System.out.println("\nТакого персонала нет.");
                        Welcome();
                    }
                }
            }
            case "5" -> {
                if (treasury <= 0) {
                    System.out.println("""
                            Ваше Величество, Вы в долгах...
                            Созовите совет для пополнения казны""");
                } else {
                    System.out.println("\n" + "С вычетом неприкасаемых Ваше состояние равняется " + ReculcTreasury(treasury) + " злат");
                }
            }
            case "6" -> {
                if (treasury <= 0) {
                    System.out.println("""
                            Ваше Величество, Вы в долгах...
                            Созовите совет для пополнения казны""");
                } else {
                    if (fairies.isEmpty()) {
                        System.out.println("\nВаше Величество, в Вашем замке нет ни одной феи...\nСозовите совет, чтобы нанять персонал");
                    } else {
                        treasury = ReculcFairies(treasury);
                        System.out.println("\n" + "Феи благодарны Вам! Выше текущее состояние равно " + treasury + " злат");
                    }
                }
            }
            case "7" -> {
                if (treasury <= 0) {
                    System.out.println("""
                            Ваше Величество, Вы в долгах...
                            Созовите совет для пополнения казны""");
                } else {
                    if (guard.isEmpty()) {
                        System.out.println("\nВаше Величество, в Вашем замке нет ни одного орка...\nСозовите совет, чтобы нанять персонал");
                    } else {
                        treasury = ReculcOrcs(treasury);
                        System.out.println("\n" + "Орки рады! Выше текущее состояние равно " + treasury + " злат");
                    }
                }
            }
            case null, default -> Welcome();
        }
        Welcome();
    }
    static double ReculcTreasury(double defaultTreasury){
        defaultTreasury = defaultTreasury-(defaultTreasury/100)*15;
        return defaultTreasury;
    }
    static  double ReculcFairies(double defaultTreasury){
        reputation +=5;
        defaultTreasury = (defaultTreasury-(defaultTreasury/100)*15) - fairies.size()*40;
        return defaultTreasury;
    }
    static  double ReculcOrcs(double defaultTreasury){
        reputation +=10;
        int orcs = guard.size();
        defaultTreasury = (defaultTreasury-(defaultTreasury/100)*15) - orcs*80 - ((double)orcs /100*10)*100;
        return defaultTreasury;
    }

    static void FamilyDinner(){
        if (residents.contains("Повар")){
            String choice;
            Scanner sc = new Scanner(System.in); // подключаем сканер
            System.out.println("""

                    Ваше Величество, что сегодня на ужин?
                    1. Щупальца заморские\s
                    2. Свиные рёбра\s
                    3. Птица в кляре\s
                    4. Бедро телячье\s
                    5. Треска под соусом""");
            choice = sc.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println("\nМладшая Седани вертит нос. Поменять основное блюдо?\nДа\nНет");
                    choice = sc.nextLine();
                    if (Objects.equals(choice, "Да")) {
                        FamilyDinner();
                        return;
                    } else if (Objects.equals(choice, "Нет")) {
                        System.out.println("\nСедани обижена");
                        treasury -= 75;
                    } else {
                        System.out.println("\nДавайте попробуем заново.");
                        FamilyDinner();
                        return;
                    }
                }
                case "2" -> {
                    treasury -= 45;
                    System.out.println("\nВсе довольны.");
                }
                case "3" -> {
                    treasury -= 30;
                    System.out.println("\nВсе довольны.");
                }
                case "4" -> {
                    treasury -= 60;
                    System.out.println("\nВсе довольны.");
                }
                case "5" -> {
                    System.out.println("\nМладшая Седани вертит нос. Поменять основное блюдо?\nДа\nНет");
                    choice = sc.nextLine();
                    if (Objects.equals(choice, "Да")) {
                        FamilyDinner();
                        return;
                    } else if (Objects.equals(choice, "Нет")) {
                        System.out.println("\nСедани обижена");
                        treasury -= 65;
                    } else {
                        System.out.println("\nДавайте попробуем заново.");
                        FamilyDinner();
                        return;
                    }
                }
                case null, default -> {
                    System.out.println("\nСожалеем, повар не знает такого блюда.\nНаказать?\nДа\nНет");
                    choice = sc.nextLine();
                    if (Objects.equals(choice, "Да")) {
                        Main main1 = new Main();
                        main1.Punishment(residents.indexOf("Повар"));
                    } else if (Objects.equals(choice, "Нет")) {
                        reputation += 5;
                        System.out.println("\nПовар благодарен за Ваше милосердие. Начнём ужин снова.");
                    } else {
                        System.out.println("\nДавайте попробуем заново.");
                    }
                    FamilyDinner();
                    return;
                }
            }
            System.out.println("""
                    Ваше Величество, что сегодня на гарнир?
                    1. Рис бурый\s
                    2. Гречка сельская\s
                    3. Картошка толчёная\s
                    4. Паста с сырами\s""");
            choice = sc.nextLine();
            switch (choice) {
                case "1" -> {
                    treasury -= 15;
                    System.out.println("\nВсе довольны.");
                }
                case "2" -> {
                    System.out.println("\nМладшая Седани вертит нос. Поменять?\nДа\nНет");
                    choice = sc.nextLine();
                    if (Objects.equals(choice, "Да")) {
                        FamilyDinner();
                        return;
                    } else if (Objects.equals(choice, "Нет")) {
                        System.out.println("\nСедани обижена");
                        treasury -= 10;
                    } else {
                        System.out.println("\nДавайте попробуем заново.");
                        FamilyDinner();
                        return;
                    }
                }
                case "3" -> {
                    treasury -= 20;
                    System.out.println("\nВсе довольны.");
                }
                case "4" -> {
                    treasury -= 30;
                    System.out.println("\nВсе довольны.");
                }
                case null, default -> {
                    System.out.println("\nСожалеем, повар не знает такого блюда.\nНаказать?\nДа\nНет");
                    choice = sc.nextLine();
                    if (Objects.equals(choice, "Да")) {
                        Main main1 = new Main();
                        main1.Punishment(residents.indexOf("Повар"));

                    } else if (Objects.equals(choice, "Нет")) {
                        reputation += 5;
                        System.out.println("\nПовар благодарен за Ваше милосердие. Начнём ужин снова.");
                    } else {
                        System.out.println("\nДавайте попробуем заново.");
                    }
                    FamilyDinner();
                }
            }
            System.out.println("\nУжин закончен.");
        }
        else{
            System.out.println("\nВаше Величество, в Вашем замке нет ни одного повара...\nСозовите совет, чтобы нанять персонал.");
        }
    }
    public void Punishment(int residentId){
        String choice;
        Scanner sc = new Scanner(System.in); // подключаем сканер
        System.out.println("\nВыберете наказание.\n1. Вычет из жалования \n2. За решётку! \n3. Казнить...");
        choice = sc.nextLine();
        switch (choice) {
            case "1" -> {
                treasury += 50;
                reputation -= 15;
                System.out.println("\nВ Вашей казне прибавилось злат!");
            }
            case "2" -> {
                reputation -= 20;
                if (residents.get(residentId) == "Фея") {
                    fairies.remove(residents.get(residentId));
                } else if (residents.get(residentId) == "Орк") {
                    guard.remove(residents.get(residentId));
                }
                jail.add(residents.get(residentId));
                residents.remove(residentId);
                System.out.println("\nРезидент в тюрьме. Созовите совет, чтобы помиловать.");
            }
            case "3" -> {
                reputation -= 40;
                if (residents.get(residentId) == "Фея") {
                    fairies.remove(residents.get(residentId));
                } else if (residents.get(residentId) == "Орк") {
                    guard.remove(residents.get(residentId));
                }
                residents.remove(residentId);
                System.out.println("\nРезидент мёртв.");
            }
            case null, default -> {
                System.out.println("Попробуем заново");
                Punishment(residentId);
            }
        }
    }
    public void Council(){
        String choice;
        Scanner sc = new Scanner(System.in); // подключаем сканер
        System.out.println("\nВаше Величество, что обсудим?\n1. Нанять персонал \n2. Выпустить заключённого \n3. Собрать налоги \n4. Обсудить погоду");
        choice = sc.nextLine();
        switch (choice) {
            case "1" -> {
                System.out.println("\nКого нанять?\n1. Повара \n2. Фею-уборщицу \n3. Орка-стражника");
                choice = sc.nextLine();
                switch (choice) {
                    case "1" -> {
                        System.out.println("\nПовар нанят.");
                        Object cooker = "Повар";
                        residents.add(cooker);
                    }
                    case "2" -> {
                        System.out.println("\nФея нанята.");
                        Object fairy = "Фея";
                        fairies.add(fairy);
                        residents.add(fairy);
                    }
                    case "3" -> {
                        System.out.println("\nОрк нанят.");
                        Object orc = "Орк";
                        guard.add(orc);
                        residents.add(orc);
                    }
                    case null, default -> {
                        System.out.println("\nСовет Вас не понял.");
                        Council();
                    }
                }
            }
            case "2" -> {
                if(jail.isEmpty()){
                    System.out.println("\nТюрьма пуста, выпускать некого.");
                }
                else {
                    System.out.println("\nКого выпустить?\n1. Повара \n2. Фею-уборщицу \n3. Орка-стражника");
                    choice = sc.nextLine();
                    switch (choice) {
                        case "1" -> {
                            if (jail.contains("Повар")){
                                System.out.println("\nПовар нанят обратно.");
                                reputation += 10;
                                jail.remove("Повар");
                                residents.add("Повар");
                            }
                            else {
                                System.out.println("\nПоваров в тюрьме нет.");
                            }
                        }
                        case "2" -> {
                            if (jail.contains("Фея")){
                                System.out.println("\nФея нанята обратно.");
                                reputation += 10;
                                jail.remove("Фея");
                                fairies.add("Фея");
                                residents.add("Фея");
                            }
                            else {
                                System.out.println("\nФей в тюрьме нет.");
                            }
                        }
                        case "3" -> {
                            if (jail.contains("Орк")){
                                System.out.println("\nОрк нанят обратно.");
                                reputation += 10;
                                jail.remove("Орк");
                                guard.add("Орк");
                                residents.add("Орк");
                            }
                            else {
                                System.out.println("\nОрков в тюрьме нет.");
                            }
                        }
                        case null, default -> {
                            System.out.println("\nСовет Вас не понял.");
                            Council();
                        }
                    }
                }
            }
            case "3" -> {
                if (reputation <= 20) {
                    System.out.println("\nНарод отказался платить...\nВыполните желания подданых, чтобы повысить репутацию.");
                } else {
                    treasury += 500;
                    System.out.println("\nВы собрали налоги с подданых. В Вашей казне прибавилось злат!");
                }
            }
            case "4" -> {
                Random random = new Random();
                int index = random.nextInt(weather.length);
                System.out.println("\n\"Да, господа, сегодня " + weather[index] + "\"");
            }
            case null, default -> {
                System.out.println("\nСовет Вас не понял.");
                Council();
            }
        }
    }
    public void Wishes(){
        String choice;
        Scanner sc = new Scanner(System.in); // подключаем сканер
        System.out.println("""
                Ваше Величество, какое желание исполнить?
                1. Раздать лишние златы
                2. Построить таверну
                3. Закупить заморских деликатесов""");
        choice = sc.nextLine();
        switch (choice) {
            case "1" -> {
                treasury = treasury - ((treasury * 10) / 100);
                System.out.println("\nВаша казна слегка опустела, но подданые рады!");
                reputation += (int)Math.round(((treasury * 10) / 100) / 10);
            }
            case "2" -> WishTavern();
            case "3" -> WishFood();
            case null, default -> {
                System.out.println("\nНарод Вас не понял.");
                Welcome();
            }
        }
    }
    public void WishTavern(){
        if (treasury >= 300)
        {
            try
            {
                for (int i = 1; i <= 7; i++) {
                    switch (i){
                        case 1 -> System.out.println("\nВыбираем участок земли...");
                        case 2 -> System.out.println("\nДоговарвиаемся с дровосеками...");
                        case 3 -> System.out.println("\nЖдём поступления дерева...");
                        case 4 -> System.out.println("\nСтроим...");
                        case 5 -> System.out.println("\nРазливаем вина по бочкам...");
                        case 6 -> System.out.println("\nПриглашаем официанток...");
                        case 7 -> System.out.println("\nОткрываем двери...");
                    }
                    System.out.println("\nЭтап " + i +"/7");
                    Thread.sleep(1500);
                    if (i == 7){
                        System.out.println("\nГотово!");
                    }
                }
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            treasury -= 300;
            reputation += 25;
            System.out.println("\nВаши подданые знатно набухались!");
        }
        else {
            System.out.println("""
                            Ваше Величество, в казне недостаточно злат...
                            Созовите совет для пополнения казны""");
        }
    }
    public void WishFood(){
        if (treasury >= 170)
        {
            try
            {
                for (int i = 1; i <= 5; i++) {
                    switch (i){
                        case 1 -> System.out.println("\nВыбираем продукты...");
                        case 2 -> System.out.println("\nДоговарвиаемся с заморскими купцами...");
                        case 3 -> System.out.println("\nЖдём возвращения мореплавателей...");
                        case 4 -> System.out.println("\nДегустируем...");
                        case 5 -> System.out.println("\nВыкладываем на прилавки...");
                    }
                    System.out.println("\nЭтап " + i +"/5");
                    Thread.sleep(1500);
                    if (i == 5){
                        System.out.println("\nГотово!");
                    }
                }
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            treasury -= 170;
            reputation += 15;
            System.out.println("\nВаши подданые вкусно поели!");
        }
        else {
            System.out.println("""
                            Ваше Величество, в казне недостаточно злат...
                            Созовите совет для пополнения казны""");
        }
    }
}