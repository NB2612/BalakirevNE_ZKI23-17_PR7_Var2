import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Locality {

    private String country; // Название страны
    private String city; // Название города
    private String street; // Название улицы
    private String postIndex; // Почтовый индекс
    private int houseNumber; // Номер дома
    private int kvNum; // Номер квартиры

    private static final Logger LOGGER = Logger.getLogger(Locality.class.getName());

    Locality() {
        this.country = "null";
        this.city = "null";
        this.street = "null";
        this.postIndex = "000000";
        this.houseNumber = 0;
        this.kvNum = 0;
    }

    Locality(String country, String city, String postIndex,
             String street, int houseNumber, int kvNum) {
        try {
            setCountry(country);
            setCity(city);
            setpostIndex(postIndex);
            setStreet(street);
            sethouseNumber(houseNumber);
            setkvNum(kvNum);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Данные введены некоректно", e);
        }
    }

    public String formatAddress() { // Формат вывода данных
        return postIndex + ", " + country + ", " + city + ", " + street + ", д. " + houseNumber +
            ", кв. " + kvNum;
    }

    public int getkvNum() {
        return kvNum;
    }

    public void setkvNum(int kvNum) {
        if (kvNum > 0) this.kvNum = kvNum;
        else throw new IllegalArgumentException("Номер квартиры должен быть положительным");
    }

    public int gethouseNumber() {
        return houseNumber;
    }

    public void sethouseNumber(int houseNumber) {
        if (houseNumber > 0) this.houseNumber = houseNumber;
        else throw new IllegalArgumentException("Номер дома должен быть положительным");
    }

    public String getpostIndex() {
        return postIndex;
    }

    public void setpostIndex(String postIndex) {
        if (postIndex.matches("\\d{6}")) this.postIndex = postIndex;
        else throw new IllegalArgumentException("Не действительный почтовый индекс");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street.matches("^\\D[а-яА-Я].+")) this.street = street;
        else throw new IllegalArgumentException("Название улицы введено не корректно");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city.matches("^\\D[а-яА-Я]{2,}")) this.city = city;
        else throw new IllegalArgumentException("Название города введено не корректно");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country.matches("^\\D[а-яА-Я]{3,}")) this.country = country;
        else throw new IllegalArgumentException("Название страны введено не корректно");
    }

    // Компаратор для сортировки
    public static Comparator<Locality> sortBy(String field, boolean upOrDown) {
        Comparator<Locality> comp = switch (field) {
            case "index" -> Comparator.comparing(Locality::getpostIndex);
            case "country" -> Comparator.comparing(Locality::getCountry);
            case "city" -> Comparator.comparing(Locality::getCity);
            case "street" -> Comparator.comparing(Locality::getStreet);
            case "houseNumber" -> Comparator.comparing(Locality::gethouseNumber);
            case "kvNum" -> Comparator.comparing(Locality::getkvNum);
            default -> null;
        };

        assert !upOrDown && comp != null;

        comp = comp.reversed();

        return comp;
    }

}



