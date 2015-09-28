package ru.sibhtc.educationdemo.adapters.holders;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class DetailResultItemHolder {
    private TextView dateEvent;
    private TextView timeResult;//время сдачи в минутах
    private TextView errorCount;
    private TextView resultRating;
    private ImageView resultRatingImage;

    public TextView getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(TextView dateEvent) {
        this.dateEvent = dateEvent;
    }

    public TextView getTimeResult() {
        return timeResult;
    }

    public void setTimeResult(TextView timeResult) {
        this.timeResult = timeResult;
    }

    public TextView getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(TextView errorCount) {
        this.errorCount = errorCount;
    }

    public TextView getResultRating() {
        return resultRating;
    }

    public void setResultRating(TextView resultRating) {
        this.resultRating = resultRating;
    }

    public ImageView getResultRatingImage() {
        return resultRatingImage;
    }

    public void setResultRatingImage(ImageView resultRatingImage) {
        this.resultRatingImage = resultRatingImage;
    }
}
