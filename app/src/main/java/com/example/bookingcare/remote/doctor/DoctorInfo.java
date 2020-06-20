package com.example.bookingcare.remote.doctor; ;

import com.example.bookingcare.remote.schedules.TimeSlot;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class DoctorInfo<Líst> {
    private static DoctorInfo _instance = new DoctorInfo();

    public DoctorInfo() {
    }

    public static void init(DoctorInfo result){
        _instance = result;
    }

    public static DoctorInfo getInstance(){
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

    private Map<String, List<TimeSlot>> schedule;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public DoctorInfo withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DoctorInfo withId(String id) {
        this.id = id;
        return this;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public DoctorInfo withFistName(String fistName) {
        this.fistName = fistName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DoctorInfo withLastName(String lastName) {
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

    public DoctorInfo withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public DoctorInfo withAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DoctorInfo withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DoctorInfo withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public DoctorInfo withSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public DoctorInfo withCreateAt(String createAt) {
        this.createAt = createAt;
        return this;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public DoctorInfo withUpdateAt(String updateAt) {
        this.updateAt = updateAt;
        return this;
    }

}

