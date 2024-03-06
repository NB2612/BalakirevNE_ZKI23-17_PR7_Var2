public class Errors {
    private static final String Incorrect_action = "Некоректное действие",
        Incorrect_index = "Не действительный почтовый индекс",
        Incorrect_street = "Название улицы введено не корректно",
        Incorrect_city = "Название города введено не корректно",
        Incorrect_country = "Название страны введено не корректно",
        Incorrect_num_kv = "Номер квартиры введен не корректно",
        Incorrect_num_house = "Название страны введено не корректно",
        Address_list_empty = "Список аддресов пуст";

    public Errors(){}

    public static void printError(int err) {
        System.out.println(switch (err) {
            case 101 -> Incorrect_action;
            case 102 -> Incorrect_index;
            case 103 -> Incorrect_street;
            case 104 -> Incorrect_city;
            case 105 -> Incorrect_country;
            case 106 -> Incorrect_num_kv;
            case 107 -> Incorrect_num_house;
            case 108 -> Address_list_empty;
            default -> null;
        });
    }
}
