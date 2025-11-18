import android.os.Parcel;
import android.os.Parcelable;

public class Watches {
    private String price;
    private String size;
    private  String color;
    private  String gender;
    private String brand;


    public Watches() {
    }

    public Watches(String price, String size, String color, String gender, String brand
                   ) {
        this.price = price;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.brand = brand;

    }

    protected Watches(Parcel in) {
        this.price = in.readString();
        this.size = in.readString();
        this.color = in.readString();
        this.gender = in.readString();
        this.brand = in.readString();

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.price);
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeString(this.gender);
        dest.writeString(this.brand);

    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price= price;
    }

    public String getsize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getcolor() {
        return color;
    }

    public void setcolor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender= gender;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String phone) {
        this.phone = phone;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCar_model() {
        return Car_model;
    }

    public void setCar_model(String car_model) {
        Car_model = car_model;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getKilometre() {
        return kilometre;
    }

    public void setKilometre(String kilometre) {
        this.kilometre = kilometre;
    }

    public String getEngine_capacity() {
        return Engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        Engine_capacity = engine_capacity;
    }

    public String getGear_shifting_model() {
        return Gear_shifting_model;
    }

    public void setGear_shifting_model(String gear_shifting_model) {
        Gear_shifting_model = gear_shifting_model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                "nameCar='" + nameCar + '\'' +
                ", horse_power='" + horse_power + '\'' +
                ", owners='" + owners + '\'' +
                ", phone='" + phone + '\'' +
                ", color='" + color + '\'' +
                ", car_num='" + car_num + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", year='" + year + '\'' +
                ", Car_model='" + Car_model + '\'' +
                ", test='" + test + '\'' +
                ", kilometre='" + kilometre + '\'' +
                ", Engine_capacity='" + Engine_capacity + '\'' +
                ", Gear_shifting_model='" + Gear_shifting_model + '\'' +
                ", price='" + price + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
