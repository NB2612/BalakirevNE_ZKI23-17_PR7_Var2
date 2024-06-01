import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class menu {

  private static final Scanner input = new Scanner(System.in);
  protected static ArrayList<Locality> addresses = new ArrayList<>();
  protected static int choice;

  public static void display() {
    /* Начальные данные
    addresses.add(new Locality("Россия", "Красноярск", "660111",
        "Тельмана", 10, 5));
    addresses.add(new Locality("Россия", "Красноярск", "660182",
        "Краснодарская", 8, 11));
    addresses.add(new Locality("Россия", "Красноярск", "660152",
        "Вильского", 10, 1));
    addresses.add(new Locality("Россия", "Красноярск", "660009",
        "Рокосовского", 5, 152));*/
    //addresses.add(new Locality("Россия","Красноярск","660","",0,0));

    do {
      System.out.print("""
          1 - Добавить пустой адрес
          2 - Добавить адрес
          3 - Редактировать
          4 - Вывести все адреса
          5 - Сортировать
          6 - Прочее
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

          System.out.print("Введите страну: ");
          country = input.nextLine();

          System.out.print("Введите город: ");
          city = input.nextLine();

          System.out.print("Введите улицу: ");
          street = input.nextLine();

          System.out.print("Введите почтовый индекс: ");
          index = input.nextLine();

          System.out.print("Введите номер дома: ");
          houseNumber = input.nextInt();
          input.nextLine();

          System.out.print("Введите номер квартиры: ");
          kvNum = input.nextInt();
          input.nextLine();

          // Если мы дошли до этой точки, значит, данные введены корректно
          addresses.add(new Locality(country, city, index, street, houseNumber, kvNum));
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
              System.out.println((i + 1) + ". " + addresses.get(i));
            }
            System.out.println("Для выхода введите 0.");
            System.out.printf("Введите номер адреса (0-%s): ", addresses.size());
            choiceAddress = input.nextInt();

            input.nextLine(); // Считываем остаток строки после ввода
            if (choiceAddress != 0 && choiceAddress <= addresses.size()) {
              Locality editableAddress = addresses.get(choiceAddress - 1);
              do {
                System.out.println("Адрес " + choiceAddress + ": "
                    + addresses.get(choiceAddress - 1));
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
                      addresses.get(i));
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
              System.out.println("Адрес " + i + ": " + addresses.get(i));
            }
          }
          break;
        case 6:
          advancedMenu();
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

  public static void close() {
    input.close();
    System.exit(0);
  }

  private static void advancedMenu() {
    System.out.print("""
        1 - Создать поток и вывести на экран
        2 - Фильтрация объектов
        3 - Удаление дубликатов
        4 - Сумма номеров домов
        5 - Использование Optional
        6 - Группировка по городу
        7 - SummaryStatistics для номеров домов
        8 - Сохранить данные в файл
        9 - Загрузить данные из файла
        0 - Назад
        -->\s""");

    try {
      choice = input.nextInt();
    } catch (Exception e) {
      throw new RuntimeException("Ошибка ввода пункта меню: " + e);
    }
    input.nextLine(); // Считываем остаток строки

    switch (choice) {
      case 1:
        createStreamAndDisplay(addresses);
        break;
      case 2:
        filterAddresses(addresses);
        break;
      case 3:
        removeDuplicates(addresses);
        break;
      case 4:
        sumHouseNumbers(addresses);
        break;
      case 5:
        demonstrateOptional(addresses);
        break;
      case 6:
        groupByCity(addresses);
        break;
      case 7:
        summaryStatistics(addresses);
        break;
      case 8:
        saveToFile(addresses);
        break;
      case 9:
        loadFromFile(addresses);
        break;
      case 0:
        break;
    }
  }

  private static void createStreamAndDisplay(ArrayList<Locality> addresses) {
    System.out.println("Все адреса:");
    addresses.stream().forEach(System.out::println);
  }

  private static void filterAddresses(ArrayList<Locality> addresses) {
    System.out.print("Введите минимальный номер дома для фильтрации: ");
    int minHouseNumber = input.nextInt();
    input.nextLine();

    System.out.println("Отфильтрованные адреса:");
    addresses.stream()
        .filter(address -> address.gethouseNumber() > minHouseNumber)
        .forEach(System.out::println);
  }

  private static void removeDuplicates(ArrayList<Locality> addresses) {
    List<Locality> uniqueAddresses = addresses.stream()
        .distinct()
        .toList();
    addresses.clear();
    addresses.addAll(uniqueAddresses);
    System.out.println("Дубликаты удалены.");
  }

  private static void sumHouseNumbers(ArrayList<Locality> addresses) {
    int sum = addresses.stream()
        .mapToInt(Locality::gethouseNumber)
        .sum();
    System.out.println("Сумма номеров домов: " + sum);
  }

  private static void demonstrateOptional(ArrayList<Locality> addresses) {
    Optional<Locality> optionalLocality = addresses.stream().findFirst();
    optionalLocality.ifPresentOrElse(
        address -> System.out.println("Первый адрес: " + address),
        () -> System.out.println("Список адресов пуст.")
    );
  }

  private static void groupByCity(ArrayList<Locality> addresses) {
    Map<String, Long> cityGroups = addresses.stream()
        .collect(Collectors.groupingBy(Locality::getCity, Collectors.counting()));

    cityGroups.forEach(
        (city, count) -> System.out.println("Город: " + city + ", Количество: " + count));
  }

  private static void summaryStatistics(ArrayList<Locality> addresses) {
    IntSummaryStatistics stats = addresses.stream()
        .mapToInt(Locality::gethouseNumber)
        .summaryStatistics();

    System.out.println("Статистика:");
    System.out.println("Min: " + stats.getMin());
    System.out.println("Max: " + stats.getMax());
    System.out.println("Sum: " + stats.getSum());
    System.out.println("Average: " + stats.getAverage());
  }

  private static void saveToFile(ArrayList<Locality> addresses) {
    if (!addresses.isEmpty()) { // Проверка, что список не пуст
      Path path = Paths.get("addresses.txt");
      try (BufferedWriter writer = Files.newBufferedWriter(path)) {
        addresses.stream()
            .map(
                p ->
                    p.getCountry()
                        + ", "
                        + p.getCity()
                        + ", "
                        + p.getpostIndex()
                        + ", "
                        + p.getStreet()
                        + ", "
                        + p.gethouseNumber()
                        + ", "
                        + p.getkvNum())
            .forEach(
                line -> {
                  try {
                    writer.write(line);
                    writer.newLine();
                  } catch (IOException e) {
                    throw new UncheckedIOException(e);
                  }
                });
        System.out.println("Данные сохранены в файл.");
      } catch (IOException e) {
        System.err.println("Ошибка при сохранении данных: " + e.getMessage());
      }
    } else {
      System.err.println("Ошибка: Список адресов пуст");
    }
  }

  private static void loadFromFile(ArrayList<Locality> addresses) {
    Path path = Path.of("addresses.txt");
    if (Files.exists(path)) {
      try {
        // Проверка, что файл не пуст
        if (Files.size(path) > 0) {
          try (Stream<String> lines = Files.lines(path)) {
            lines
                .map(line -> line.split(", "))
                .filter(data -> data.length == 6)
                .map(
                    data ->
                        new Locality(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            Integer.parseInt(data[4]),
                            Integer.parseInt(data[5])))
                .forEach(addresses::add);
            System.out.println("Данные загружены из файла.");
          } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
          } catch (NumberFormatException e) {
            System.out.println("Ошибка преобразования числа: " + e.getMessage());
          }
        } else {
          System.out.println("Файл пуст.");
        }
      } catch (IOException e) {
        System.out.println("Ошибка при проверке размера файла: " + e.getMessage());
      }
    } else {
      System.out.println("Файл не существует.");
    }
  }
}
