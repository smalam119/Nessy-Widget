package com.thyme.smalam119.nessy.Model;

/**
 * Created by smalam119 on 1/11/18.
 */

public class QuotesResponse
{
    private Contents contents;
    private Success success;

    public Contents getContents ()
    {
        return contents;
    }

    public void setContents (Contents contents)
    {
        this.contents = contents;
    }

    public Success getSuccess ()
    {
        return success;
    }

    public void setSuccess (Success success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [contents = "+contents+", success = "+success+"]";
    }
}

