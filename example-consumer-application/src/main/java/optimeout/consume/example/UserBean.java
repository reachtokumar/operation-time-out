package optimeout.consume.example;

public class UserBean {
    private Integer id;
    private String name;
    private String country;
    private String state;
    private String localAddress;
    private String city;

    public UserBean(Integer id, String name, String country, String state, String localAddress, String city) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.state = state;
        this.localAddress = localAddress;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
