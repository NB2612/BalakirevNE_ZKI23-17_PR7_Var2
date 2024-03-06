import java.util.Comparator;

public class Locality {

    private String country; // Название страны
    private String city; // Название города
    private String street; // Название улицы
    private String post_index; // Почтовый индекс
    private int num_house; // Номер дома
    private int num_kv; // Номер квартиры

    Locality() {
        this.country = "null";
        this.city = "null";
        this.street = "null";
        this.post_index = "000000";
        this.num_house = 0;
        this.num_kv = 0;
    }

    Locality(String country, String city, String post_index,
             String street, int num_house, int num_kv) {
        setCountry(country);
        setCity(city);
        setPost_index(post_index);
        setStreet(street);
        setNum_house(num_house);
        setNum_kv(num_kv);
    }

    public String formatAddress() { // Формат вывода данных
        return post_index + ", " + country + ", " + city + ", " + street + ", д. " + num_house + ", кв. " + num_kv;
    }

    public int getNum_kv() {
        return num_kv;
    }

    public void setNum_kv(int num_kv) {
        if(num_kv>0) this.num_kv = num_kv;
        else Errors.printError(106);
    }

    public int getNum_house() {
        return num_house;
    }

    public void setNum_house(int num_house) {
        if(num_house>0) this.num_house = num_house;
        else Errors.printError(107);
    }

    public String getPost_index() {
        return post_index;
    }

    public void setPost_index(String post_index) {
        if(post_index.matches("\\d{6}")) this.post_index = post_index;
        else Errors.printError(102);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if(street.matches("^\\D[а-яА-Я].+")) this.street = street;
        else Errors.printError(103);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(city.matches("^\\D[а-яА-Я]{2,}")) this.city = city;
        else Errors.printError(104);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if(country.matches("^\\D[а-яА-Я]{3,}")) this.country = country;
        else Errors.printError(105);
    }

    // Компаратор для сортировки
    public static Comparator<Locality> sortBy(String field, boolean up_or_down){
        Comparator<Locality> comp = switch (field) {
            case "index" -> Comparator.comparing(Locality::getPost_index);
            case "country" -> Comparator.comparing(Locality::getCountry);
            case "city" -> Comparator.comparing(Locality::getCity);
            case "street" -> Comparator.comparing(Locality::getStreet);
            case "num_house" -> Comparator.comparing(Locality::getNum_house);
            case "num_kv" -> Comparator.comparing(Locality::getNum_kv);
            default -> null;
        };

        if (!up_or_down && comp != null) comp = comp.reversed();

        return comp;
    }

}



