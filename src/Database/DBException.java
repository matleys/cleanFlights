package Database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kiren
 */
public class DBException extends Exception
{
    public DBException(String msg)
    {
        super(msg);
    }
    public DBException(Exception ex)
    {
        super(ex);
    }
}

