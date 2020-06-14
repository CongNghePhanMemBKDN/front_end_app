package com.example.bookingcare.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorBody {

        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("error")
        @Expose
        private String error;

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public ErrorBody withStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ErrorBody withMessage(String message) {
            this.message = message;
            return this;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public ErrorBody withError(String error) {
            this.error = error;
            return this;
        }
}
