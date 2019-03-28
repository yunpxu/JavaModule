package com.foo.bar;

import org.baz.qux.Qux;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bar {
    static {
        try {
            DriverManager driverManager = (DriverManager) DriverManager.getDriver("");
            DocumentBuilderFactory doc = DocumentBuilderFactory.newInstance();
        } catch (SQLException e) {

        }
    }

    public static void print() {
        System.out.println("Bar");
        Qux.print();
    }
}
