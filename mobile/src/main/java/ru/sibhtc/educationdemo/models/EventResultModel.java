package ru.sibhtc.educationdemo.models;

import java.util.Date;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class EventResultModel {
    private Date dateEvent;
    private Long timeResult;//время сдачи в минутах
    private Integer errorCount;
    private Integer correctAnswerCount;//количество верных ответов

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public Long getTimeResult() {
        return timeResult;
    }

    public void setTimeResult(Long timeResult) {
        this.timeResult = timeResult;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(Integer correctAnswerCount) {
        this.correctAnswerCount = correctAnswerCount;
    }
}
