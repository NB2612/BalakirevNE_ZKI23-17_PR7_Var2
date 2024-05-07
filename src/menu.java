import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class menu {

  public static void display(Scanner input) {
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

      try {
        choice = input.nextInt();
      } catch (Exception e) {
        throw new RuntimeException("Ошибка ввода пункта меню: " + e);
      }
      input.nextLine(); // Считываем остаток строки
      switch (choice) {
        case 1: //Добавление путсого адреса
          addresses.add(new Locality());
          System.out.println("Пустой адрес добавлен");
          break;
        case 2:// Добавляем адрес с данными, введенными пользователем

          String country, city, street, index;
          int houseNumber, kvNum;

          /*
            1. Пользователь вводит страну, город, улицу, почтовый индекс, номер дома и номер квартиры.
            2. Для каждого введенного значения проверяется его корректность с помощью регулярных
          выражений или условий.
            3. Если введенные данные не соответствуют ожидаемому формату или условиям,
          генерируется исключение с сообщением об ошибке.
            4. сли номер дома или квартиры не являются положительными числами,
          также генерируется исключение.
            5. Если данные введены корректно, объект Locality, представляющий адрес,
          создается и добавляется в коллекцию addresses.
          */

          try (input) {
            System.out.print("Введите страну: ");
            country = input.nextLine();
            if (!country.matches("^\\D[а-яА-Я]{3,}")) {
              throw new IllegalArgumentException("Ошибка: некорректная страна.");
            }

            System.out.print("Введите город: ");
            city = input.nextLine();
            if (!city.matches("^\\D[а-яА-Я]{2,}")) {
              throw new IllegalArgumentException("Ошибка: некорректный город.");
            }

            System.out.print("Введите улицу: ");
            street = input.nextLine();
            if (!street.matches("^\\D[а-яА-Я].+")) {
              throw new IllegalArgumentException("Ошибка: некорректная улица.");
            }

            System.out.print("Введите почтовый индекс: ");
            index = input.nextLine();
            if (!index.matches("\\d{6}")) {
              throw new IllegalArgumentException("Ошибка: некорректный почтовый индекс.");
            }

            System.out.print("Введите номер дома: ");
            houseNumber = input.nextInt();
            input.nextLine();

            System.out.print("Введите номер квартиры: ");
            kvNum = input.nextInt();
            input.nextLine();

            if (houseNumber <= 0 || kvNum <= 0) {
              throw new IllegalArgumentException("Ошибка: номер дома или квартиры не могут быть "
                  + "меньше или равны нулю.");
            }

            // Если мы дошли до этой точки, значит, данные введены корректно
            addresses.add(new Locality(country, city, index, street, houseNumber, kvNum));

          } catch (InputMismatchException e) {
            System.out.println("Ошибка: ожидалось целое число для номера дома или квартиры.");
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }
          break;

        case 3: // Редактирование адресов
          if (addresses.isEmpty()) { // Проверяем список адресов на наличие данных
            Errors.printError(108);
            break;
          }
          int choiceAddress;
          do {
            System.out.println("Выберите адрес для редактирования:");
            for (int i = 0; i < addresses.size(); i++) {
              System.out.println((i + 1) + ". " + addresses.get(i).formatAddress());
            }
            System.out.println("Для выхода введите 0.");
            System.out.printf("Введите номер адреса (0-%s): ", addresses.size());
            choiceAddress = input.nextInt();

            input.nextLine(); // Считываем остаток строки после ввода
            if (choiceAddress != 0 && choiceAddress <= addresses.size()) {
              Locality editableAddress = addresses.get(choiceAddress - 1);
              do {
                System.out.println("Адрес " + choiceAddress + ": "
                    + addresses.get(choiceAddress - 1).formatAddress());
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
                switch (choice) {
                  case 1:
                    System.out.print("Введите новый почтовый индекс: ");
                    editableAddress.setpostIndex(input.nextLine());
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
                      editableAddress.sethouseNumber(input.nextInt());
                      input.nextLine(); // Считываем остаток строки после ввода
                    } else {
                      Errors.printError(107);
                      input.nextLine();
                    }
                    break;
                  case 6:
                    System.out.print("Введите новый номер квартиры: ");
                    if (input.hasNextInt()) {
                      editableAddress.setkvNum(input.nextInt());
                      input.nextLine(); // Считываем остаток строки после ввода
                    } else {
                      Errors.printError(106);
                      input.nextLine();
                    }
                    break;
                  case 7:
                    break;

                  default:
                    Errors.printError(101);
                    break;
                }

              } while (choice != 0);

            } else {
              Errors.printError(101);
            }
          } while (choiceAddress != 0);
          break;

        case 4: // Вывод всех адресов
          System.out.println("Информация о всех адресах:");
          try {
            if (addresses.isEmpty()) {
              throw new IllegalStateException("Ошибка: список адресов пуст.");
            } else {
              for (int i = 0; i < addresses.size(); i++) {
                try {
                  System.out.println("Адрес " + i + ": " +
                      addresses.get(i).formatAddress());
                } catch (IndexOutOfBoundsException ex) {
                  System.err.println("Ошибка: возникла проблема "
                      + "при доступе к адресу " + i);
                }
              }
            }
          } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
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

          int choiceSort = input.nextInt();
          input.nextLine();

          String field = null;
          switch (choiceSort) {
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
              field = "houseNumber";
              break;
            case 6:
              field = "kvNum";
              break;
            case 0:
              break;
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

          choiceSort = input.nextInt();
          input.nextLine();

          boolean upOrDown = false;

          switch (choiceSort) {
            case 1:
              upOrDown = true;
              break;
            case 2:
              break;
            default:
              Errors.printError(101);
              System.out.println("Будет выполнена сортировка по умолчанию");
              break;
          }

          if (field != null) {
            addresses.sort(Locality.sortBy(field, upOrDown));
            System.out.println("Адреса отсортированы.");
            for (int i = 0; i < addresses.size(); i++) {
              System.out.println("Адрес " + i + ": " + addresses.get(i).formatAddress());
            }
          }
          break;
        case 0:
          System.out.println("Завершение работы программы");
          input.close();
          return;
        default:
          Errors.printError(101);
          break;
      }
    } while (true);
  }
}
