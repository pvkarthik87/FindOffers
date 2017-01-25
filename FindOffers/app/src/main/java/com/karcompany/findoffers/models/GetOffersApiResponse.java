/**
 * Created by pvkarthik on 2017-01-25.
 *
 * This is POJO class corresponding to server response JSON.
 */
package com.karcompany.findoffers.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GetOffersApiResponse implements Parcelable
{

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("count")
    @Expose
    private long count;
    @SerializedName("pages")
    @Expose
    private long pages;
    @SerializedName("information")
    @Expose
    private Information information;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    public final static Parcelable.Creator<GetOffersApiResponse> CREATOR = new Creator<GetOffersApiResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetOffersApiResponse createFromParcel(Parcel in) {
            GetOffersApiResponse instance = new GetOffersApiResponse();
            instance.code = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            instance.count = ((long) in.readValue((long.class.getClassLoader())));
            instance.pages = ((long) in.readValue((long.class.getClassLoader())));
            instance.information = ((Information) in.readValue((Information.class.getClassLoader())));
            in.readList(instance.offers, (com.karcompany.findoffers.models.Offer.class.getClassLoader()));
            return instance;
        }

        public GetOffersApiResponse[] newArray(int size) {
            return (new GetOffersApiResponse[size]);
        }

    }
    ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(message).append(count).append(pages).append(information).append(offers).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetOffersApiResponse) == false) {
            return false;
        }
        GetOffersApiResponse rhs = ((GetOffersApiResponse) other);
        return new EqualsBuilder().append(code, rhs.code).append(message, rhs.message).append(count, rhs.count).append(pages, rhs.pages).append(information, rhs.information).append(offers, rhs.offers).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(message);
        dest.writeValue(count);
        dest.writeValue(pages);
        dest.writeValue(information);
        dest.writeList(offers);
    }

    public int describeContents() {
        return  0;
    }

}
