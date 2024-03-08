public class Errors {
    private static final String IncorrectAction = "Некоректное действие",
            IncorrectIndex = "Не действительный почтовый индекс",
            IncorrectStreet = "Название улицы введено не корректно",
            IncorrectCity = "Название города введено не корректно",
            IncorrectCountry = "Название страны введено не корректно",
            IncorrectNumKV = "Номер квартиры введен не корректно",
            IncorrectNumHouse = "Название страны введено не корректно",
            AddressListEmpty = "Список аддресов пуст";

    public Errors() {
    }

    public static void printError(int err) {
        System.out.println(switch (err) {
            case 101 -> IncorrectAction;
            case 102 -> IncorrectIndex;
            case 103 -> IncorrectStreet;
            case 104 -> IncorrectCity;
            case 105 -> IncorrectCountry;
            case 106 -> IncorrectNumKV;
            case 107 -> IncorrectNumHouse;
            case 108 -> AddressListEmpty;
            default -> null;
        });
    }
}
