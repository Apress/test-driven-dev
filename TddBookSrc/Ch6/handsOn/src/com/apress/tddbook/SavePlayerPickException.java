package com.apress.tddbook;

public class SavePlayerPickException extends Exception
{
    public SavePlayerPickException(String errorMsg)
    {
        super(errorMsg);
    }
}
