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

public class Thumbnail implements Parcelable
{

    @SerializedName("lowres")
    @Expose
    private String lowres;
    @SerializedName("hires")
    @Expose
    private String hires;
    public final static Parcelable.Creator<Thumbnail> CREATOR = new Creator<Thumbnail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Thumbnail createFromParcel(Parcel in) {
            Thumbnail instance = new Thumbnail();
            instance.lowres = ((String) in.readValue((String.class.getClassLoader())));
            instance.hires = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Thumbnail[] newArray(int size) {
            return (new Thumbnail[size]);
        }

    }
    ;

    public String getLowres() {
        return lowres;
    }

    public void setLowres(String lowres) {
        this.lowres = lowres;
    }

    public String getHires() {
        return hires;
    }

    public void setHires(String hires) {
        this.hires = hires;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lowres).append(hires).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Thumbnail) == false) {
            return false;
        }
        Thumbnail rhs = ((Thumbnail) other);
        return new EqualsBuilder().append(lowres, rhs.lowres).append(hires, rhs.hires).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lowres);
        dest.writeValue(hires);
    }

    public int describeContents() {
        return  0;
    }

}
