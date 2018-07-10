package com.giladsagi.privateteacher;

/**
 * Created by GILADSAG on 7/25/2017.
 */

public class ListItem {
    private String item_id;
    private String city;
    private String subject;
    private String telephone;
    private String info;
    private String email;
    private String name;
    private String age;
    private String gender;
    private String price;
    private String rating;
    private String image;

    public ListItem(String item_id, String city, String subject, String telephone, String info, String email, String name, String age, String gender, String price, String rating, String image) {
        this.item_id = item_id;
        this.city = city;
        this.subject = subject;
        this.telephone = telephone;
        this.info = info;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }




    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getCity() {
        return city;
    }

    public String getSubject() {
        return subject;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getInfo() {
        return info;
    }

    public String getEmail() {
        return email;
    }
}

