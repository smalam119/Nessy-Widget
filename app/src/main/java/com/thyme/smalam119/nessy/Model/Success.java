package com.thyme.smalam119.nessy.Model;

/**
 * Created by smalam119 on 1/11/18.
 */

public class Success
{
    private String total;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+"]";
    }
}
