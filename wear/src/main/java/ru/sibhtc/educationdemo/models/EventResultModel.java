package ru.sibhtc.educationdemo.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nikpodrivnik on 29/09/15.
 */
public class EventResultModel implements Serializable {
    private Date dateEvent;
    private Long timeResult;//время сдачи в секундах
    private Integer errorCount;
    private Integer answerCount;//количество верных ответов

    public EventResultModel(Date dateEvent, Long timeResult, Integer errorCount, Integer answerCount) {
        this.dateEvent = dateEvent;
        this.timeResult = timeResult;
        this.errorCount = errorCount;
        this.answerCount = answerCount;
    }

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

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public EventResultModel() {
    }
}
