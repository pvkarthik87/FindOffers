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

public class OfferType implements Parcelable
{

    @SerializedName("offer_type_id")
    @Expose
    private long offerTypeId;
    @SerializedName("readable")
    @Expose
    private String readable;
    public final static Parcelable.Creator<OfferType> CREATOR = new Creator<OfferType>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OfferType createFromParcel(Parcel in) {
            OfferType instance = new OfferType();
            instance.offerTypeId = ((long) in.readValue((long.class.getClassLoader())));
            instance.readable = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public OfferType[] newArray(int size) {
            return (new OfferType[size]);
        }

    }
    ;

    public long getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(long offerTypeId) {
        this.offerTypeId = offerTypeId;
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
        return new HashCodeBuilder().append(offerTypeId).append(readable).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OfferType) == false) {
            return false;
        }
        OfferType rhs = ((OfferType) other);
        return new EqualsBuilder().append(offerTypeId, rhs.offerTypeId).append(readable, rhs.readable).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(offerTypeId);
        dest.writeValue(readable);
    }

    public int describeContents() {
        return  0;
    }

}
