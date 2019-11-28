package com.example.omdbapi.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.omdbapi.db.DBHelper.COLUMN_FOREIGN_ID;
import static com.example.omdbapi.db.DBHelper.COLUMN_PRIMARY_ID;

@DatabaseTable
public class Rating implements Parcelable {

    @DatabaseField(generatedId = true, columnName = COLUMN_PRIMARY_ID)
    private int id;

    @DatabaseField
    @SerializedName("Source")
    @Expose
    private String source;
    @DatabaseField
    @SerializedName("Value")
    @Expose
    private String value;

    @DatabaseField(foreign = true, columnName = COLUMN_FOREIGN_ID)
    private MovieDetails movieDetails;


    public final static Parcelable.Creator<Rating> CREATOR = new Creator<Rating>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        public Rating[] newArray(int size) {
            return (new Rating[size]);
        }

    }
            ;

    protected Rating(Parcel in) {
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Rating() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(source);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }
}
