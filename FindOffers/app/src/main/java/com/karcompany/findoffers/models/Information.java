/**
 * Created by pvkarthik on 2017-01-25.
 *
 * This is POJO class corresponding to server response JSON.
 */
package com.karcompany.findoffers.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Information implements Parcelable
{

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("appid")
    @Expose
    private long appid;
    @SerializedName("virtual_currency")
    @Expose
    private String virtualCurrency;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("support_url")
    @Expose
    private String supportUrl;
    public final static Parcelable.Creator<Information> CREATOR = new Creator<Information>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Information createFromParcel(Parcel in) {
            Information instance = new Information();
            instance.appName = ((String) in.readValue((String.class.getClassLoader())));
            instance.appid = ((long) in.readValue((long.class.getClassLoader())));
            instance.virtualCurrency = ((String) in.readValue((String.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            instance.supportUrl = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Information[] newArray(int size) {
            return (new Information[size]);
        }

    }
    ;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppid() {
        return appid;
    }

    public void setAppid(long appid) {
        this.appid = appid;
    }

    public String getVirtualCurrency() {
        return virtualCurrency;
    }

    public void setVirtualCurrency(String virtualCurrency) {
        this.virtualCurrency = virtualCurrency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSupportUrl() {
        return supportUrl;
    }

    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(appName).append(appid).append(virtualCurrency).append(country).append(language).append(supportUrl).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Information) == false) {
            return false;
        }
        Information rhs = ((Information) other);
        return new EqualsBuilder().append(appName, rhs.appName).append(appid, rhs.appid).append(virtualCurrency, rhs.virtualCurrency).append(country, rhs.country).append(language, rhs.language).append(supportUrl, rhs.supportUrl).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(appName);
        dest.writeValue(appid);
        dest.writeValue(virtualCurrency);
        dest.writeValue(country);
        dest.writeValue(language);
        dest.writeValue(supportUrl);
    }

    public int describeContents() {
        return  0;
    }

}
