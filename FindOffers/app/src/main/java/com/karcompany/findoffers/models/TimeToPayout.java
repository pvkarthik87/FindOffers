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

public class TimeToPayout implements Parcelable
{

    @SerializedName("amount")
    @Expose
    private long amount;
    @SerializedName("readable")
    @Expose
    private String readable;
    public final static Parcelable.Creator<TimeToPayout> CREATOR = new Creator<TimeToPayout>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TimeToPayout createFromParcel(Parcel in) {
            TimeToPayout instance = new TimeToPayout();
            instance.amount = ((long) in.readValue((long.class.getClassLoader())));
            instance.readable = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public TimeToPayout[] newArray(int size) {
            return (new TimeToPayout[size]);
        }

    }
    ;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(amount).append(readable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TimeToPayout) == false) {
            return false;
        }
        TimeToPayout rhs = ((TimeToPayout) other);
        return new EqualsBuilder().append(amount, rhs.amount).append(readable, rhs.readable).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(amount);
        dest.writeValue(readable);
    }

    public int describeContents() {
        return  0;
    }

}
