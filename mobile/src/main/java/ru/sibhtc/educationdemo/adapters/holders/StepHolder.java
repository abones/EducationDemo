package ru.sibhtc.educationdemo.adapters.holders;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class StepHolder {
    private TextView txtTitle;
    private TextView stepTime;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView stepValue;

    public TextView getTxtTitle() {
        return txtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        this.txtTitle = txtTitle;
    }

    public TextView getStepTime() {
        return stepTime;
    }

    public void setStepTime(TextView stepTime) {
        this.stepTime = stepTime;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TextView getStepValue() {
        return stepValue;
    }

    public void setStepValue(TextView stepValue) {
        this.stepValue = stepValue;
    }


}
