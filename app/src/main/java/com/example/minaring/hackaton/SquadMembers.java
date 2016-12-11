package com.example.minaring.hackaton;

/**
 * Created by Minaring on 12/11/2016.
 */

public class SquadMembers
{
    public String name, uid, encodeString;

    public SquadMembers(String name, String uid, String encodeString)
    {
        this.name = name;
        this.uid = uid;
        this.encodeString = encodeString;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getEncodeString()
    {
        return encodeString;
    }

    public void setEncodeString(String encodeString)
    {
        this.encodeString = encodeString;
    }
}
