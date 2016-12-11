package com.example.minaring.hackaton;

/**
 * Created by Minaring on 12/11/2016.
 */

public class Goals
{
    public String title, body, squadID, goalID, targetDate;
    public double currBalance, budget;

    public Goals(String title, String body, String squadID, String goalID, String targetDate, double currBalance, double budget)
    {
        this.title = title;
        this.body = body;
        this.squadID = squadID;
        this.goalID = goalID;
        this.targetDate = targetDate;
        this.currBalance = currBalance;
        this.budget = budget;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getSquadID()
    {
        return squadID;
    }

    public void setSquadID(String squadID)
    {
        this.squadID = squadID;
    }

    public String getGoalID()
    {
        return goalID;
    }

    public void setGoalID(String goalID)
    {
        this.goalID = goalID;
    }

    public String getTargetDate()
    {
        return targetDate;
    }

    public void setTargetDate(String targetDate)
    {
        this.targetDate = targetDate;
    }

    public double getCurrBalance()
    {
        return currBalance;
    }

    public void setCurrBalance(double currBalance)
    {
        this.currBalance = currBalance;
    }

    public double getBudget()
    {
        return budget;
    }

    public void setBudget(double budget)
    {
        this.budget = budget;
    }

    public double getPercentage()
    {
        double value = 0.0;

        value = currBalance / budget;

        return value * 100;
    }
}
