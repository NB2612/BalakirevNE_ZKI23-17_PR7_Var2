import java.util.ArrayList;
import java.util.Scanner;

public class menu {
    public static void display(Scanner input){
        ArrayList<Locality> addresses = new ArrayList<>();
        int choice;
        do {
            System.out.print("""
                    1 - Добавить пустой адрес
                    2 - Добавить адрес
                    3 - Редактировать
                    4 - Вывести все адреса
                    5 - Сортировать
                    0 - Выход
                    -->\s""");

            try{
                choice = input.nextInt();
            } catch(Exception e){
                choice = 0;
            }
            input.nextLine(); // Считываем остаток строки
            switch(choice) {
                case 1: //Добавление путсого адреса
                    addresses.add(new Locality());
                    System.out.println("Пустой адрес добавлен");
                    break;
                case 2:// Добавляем адрес с данными, введенными пользователем

                    String country, city, street, index;
                    int num_house, num_kv;

                    System.out.print("Введите страну: ");
                    country = input.nextLine();
                    if (!country.matches("^\\D[а-яА-Я]{3,}")){
                        Errors.printError(105);
                        break;
                    }
                    //input.nextLine(); // Считываем остаток строки после ввода

                    System.out.print("Введите город: ");
                    city = input.nextLine();
                    if(!city.matches("^\\D[а-яА-Я]{2,}")) {
                        Errors.printError(104);
                        break;
                    }
                    //input.nextLine();

                    System.out.print("Введите улицу: ");
                    street = input.nextLine();
                    if(!street.matches("^\\D[а-яА-Я].+")){
                        Errors.printError(103);
                        break;
                    }
                    //input.nextLine();

                    System.out.print("Введите почтовый индекс: ");
                    index = input.nextLine();
                    if(!index.matches("\\d{6}")) {
                        Errors.printError(102);
                        break;
                    }
                    //input.nextLine();

                    System.out.print("Введите номер дома: ");
                    if (input.hasNextInt()) num_house = input.nextInt();
                    else {
                        Errors.printError(107);
                        break;
                    }

                    input.nextLine();

                    System.out.print("Введите номер кваритиры: ");
                    if (input.hasNextInt()) num_kv = input.nextInt();
                    else {
                        Errors.printError(106);
                        break;
                    }
                    input.nextLine();

                    if (num_house>0 && num_kv>0){
                        addresses.add(new Locality(country, city, index, street, num_house, num_kv));
                    } else if (!(num_house>0)) Errors.printError(107);
                    else Errors.printError(106);
                    break;

                case 3: // Редактирование адресов
                    if (addresses.isEmpty()) { // Проверяем список адресов на наличие данных
                        Errors.printError(108);
                        break;
                    }
                    int choice_address;
                    do {
                        System.out.println("Выберите адрес для редактирования:");
                        for (int i = 0; i < addresses.size(); i++) {
                            System.out.println((i + 1) + ". " + addresses.get(i).formatAddress());
                        }
                        System.out.println("Для выхода введите 0.");
                        System.out.printf("Введите номер адреса (0-%s): ", addresses.size());
                        choice_address = input.nextInt();

                        input.nextLine(); // Считываем остаток строки после ввода
                        if (choice_address != 0 && choice_address <= addresses.size()){
                            Locality editableAddress = addresses.get(choice_address - 1);
                            do {
                                System.out.println("Адрес " + choice_address + ": " + addresses.get(choice_address - 1).formatAddress());
                                System.out.print("""
                                        Выберите поле для редактирования:
                                        1 - Почтовый индекс
                                        2 - Страна
                                        3 - Город
                                        4 - Улица
                                        5 - Номер дома
                                        6 - Номер квартиры
                                        0 - Вернуться к выбору адреса для редактирования
                                        -->\s""");
                                choice = input.nextInt();
                                input.nextLine();
                                switch (choice){
                                    case 1:
                                        System.out.print("Введите новый почтовый индекс: ");
                                        editableAddress.setPost_index(input.nextLine());
                                        break;
                                    case 2:
                                        System.out.print("Введите новую страну: ");
                                        editableAddress.setCountry(input.nextLine());
                                        break;
                                    case 3:
                                        System.out.print("Введите новый город: ");
                                        editableAddress.setCity(input.nextLine());
                                        break;
                                    case 4:
                                        System.out.print("Введите новую улицу: ");
                                        editableAddress.setStreet(input.nextLine());
                                        break;
                                    case 5:
                                        System.out.print("Введите новый номер дома: ");
                                        if (input.hasNextInt()) {
                                            editableAddress.setNum_house(input.nextInt());
                                            input.nextLine(); // Считываем остаток строки после ввода
                                        } else {
                                            Errors.printError(107);
                                            input.nextLine();
                                        }
                                        break;
                                    case 6:
                                        System.out.print("Введите новый номер квартиры: ");
                                        if (input.hasNextInt()) {
                                            editableAddress.setNum_kv(input.nextInt());
                                            input.nextLine(); // Считываем остаток строки после ввода
                                        } else {
                                            Errors.printError(106);
                                            input.nextLine();
                                        }
                                        break;
                                    case 7: break;

                                    default: Errors.printError(101); break;
                                }

                            } while (choice != 0);

                        } else Errors.printError(101);
                    } while (choice_address != 0);
                    break;

                case 4: // Вывод всех адресов
                    System.out.println("Информация о всех адресах:");
                    if (addresses.isEmpty()) {
                        Errors.printError(108);
                    } else {
                        for (int i = 0; i < addresses.size(); i++) {
                            System.out.println("Адрес " + i + ": " + addresses.get(i).formatAddress());
                        }
                    }
                    break;

                case 5: //Сортировка
                    if (addresses.isEmpty()) {
                        Errors.printError(108);
                        break;
                    }
                    System.out.print("""
                            Выберите поле для сортировки:
                            1 - Почтовый индекс
                            2 - Страна
                            3 - Город
                            4 - Улица
                            5 - Номер дома
                            6 - Номер квартиры
                            0 - Выход из меню сортировки
                            -->\s""");

                    int choice_sort = input.nextInt();
                    input.nextLine();

                    String field = null;
                    switch (choice_sort){
                        case 1:
                            field = "index";
                            break;
                        case 2:
                            field = "country";
                            break;
                        case 3:
                            field = "city";
                            break;
                        case 4:
                            field = "street";
                            break;
                        case 5:
                            field = "num_house";
                            break;
                        case 6:
                            field = "num_kv";
                            break;
                        case 0: break;
                        default:
                            Errors.printError(101);
                            break;
                    }

                    //Направление сортировки
                    System.out.print("""
                            Сортировать по:
                            1 - Возрастанию
                            2 - Убыванию(значение по умолчинию)
                            -->\s""");

                    choice_sort = input.nextInt();
                    input.nextLine();

                    boolean up_or_down = false;

                    switch (choice_sort){
                        case 1:
                            up_or_down = true;
                            break;
                        case 2: break;
                        default:
                            Errors.printError(101);
                            System.out.println("Будет выполнена сортировка по умолчанию");
                            break;
                    }

                    if (field != null) {
                        addresses.sort(Locality.sortBy(field, up_or_down));
                        System.out.println("Адреса отсортированы.");
                        for (int i = 0; i < addresses.size(); i++) {
                            System.out.println("Адрес " + i + ": " + addresses.get(i).formatAddress());
                        }
                    }
                    break;

                case 0: System.out.println("Завершение работы программы"); input.close(); return;
                default: Errors.printError(101); break;
            }
        } while (true);
    }
}
