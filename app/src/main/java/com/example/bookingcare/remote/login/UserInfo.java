package com.example.bookingcare.remote.login; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    private static UserInfo _instance = new UserInfo();

    public static void init(UserInfo result){
        _instance = result;
    }

    public static UserInfo getInstance(){
       return _instance;
    };

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fistName")
    @Expose
    private String fistName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("addressDetail")
    @Expose
    private String addressDetail;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserInfo withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo withId(String id) {
        this.id = id;
        return this;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public UserInfo withFistName(String fistName) {
        this.fistName = fistName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserInfo withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName(){
        return this.fistName + " " + this.lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserInfo withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public UserInfo withAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInfo withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserInfo withSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public UserInfo withCreateAt(String createAt) {
        this.createAt = createAt;
        return this;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public UserInfo withUpdateAt(String updateAt) {
        this.updateAt = updateAt;
        return this;
    }

}

