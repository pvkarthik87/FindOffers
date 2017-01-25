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

public class Offer implements Parcelable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("offer_id")
    @Expose
    private long offerId;
    @SerializedName("teaser")
    @Expose
    private String teaser;
    @SerializedName("required_actions")
    @Expose
    private String requiredActions;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("offer_types")
    @Expose
    private List<OfferType> offerTypes = null;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("payout")
    @Expose
    private long payout;
    @SerializedName("time_to_payout")
    @Expose
    private TimeToPayout timeToPayout;
    public final static Parcelable.Creator<Offer> CREATOR = new Creator<Offer>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Offer createFromParcel(Parcel in) {
            Offer instance = new Offer();
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.offerId = ((long) in.readValue((long.class.getClassLoader())));
            instance.teaser = ((String) in.readValue((String.class.getClassLoader())));
            instance.requiredActions = ((String) in.readValue((String.class.getClassLoader())));
            instance.link = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.offerTypes, (com.karcompany.findoffers.models.OfferType.class.getClassLoader()));
            instance.thumbnail = ((Thumbnail) in.readValue((Thumbnail.class.getClassLoader())));
            instance.payout = ((long) in.readValue((long.class.getClassLoader())));
            instance.timeToPayout = ((TimeToPayout) in.readValue((TimeToPayout.class.getClassLoader())));
            return instance;
        }

        public Offer[] newArray(int size) {
            return (new Offer[size]);
        }

    }
    ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(String requiredActions) {
        this.requiredActions = requiredActions;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<OfferType> getOfferTypes() {
        return offerTypes;
    }

    public void setOfferTypes(List<OfferType> offerTypes) {
        this.offerTypes = offerTypes;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getPayout() {
        return payout;
    }

    public void setPayout(long payout) {
        this.payout = payout;
    }

    public TimeToPayout getTimeToPayout() {
        return timeToPayout;
    }

    public void setTimeToPayout(TimeToPayout timeToPayout) {
        this.timeToPayout = timeToPayout;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(title).append(offerId).append(teaser).append(requiredActions).append(link).append(offerTypes).append(thumbnail).append(payout).append(timeToPayout).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Offer) == false) {
            return false;
        }
        Offer rhs = ((Offer) other);
        return new EqualsBuilder().append(title, rhs.title).append(offerId, rhs.offerId).append(teaser, rhs.teaser).append(requiredActions, rhs.requiredActions).append(link, rhs.link).append(offerTypes, rhs.offerTypes).append(thumbnail, rhs.thumbnail).append(payout, rhs.payout).append(timeToPayout, rhs.timeToPayout).isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(offerId);
        dest.writeValue(teaser);
        dest.writeValue(requiredActions);
        dest.writeValue(link);
        dest.writeList(offerTypes);
        dest.writeValue(thumbnail);
        dest.writeValue(payout);
        dest.writeValue(timeToPayout);
    }

    public int describeContents() {
        return  0;
    }

}
