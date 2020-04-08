package com.mapr.springframework.boot.autoconfigure.maprdb;

import com.mapr.springframework.data.maprdb.core.DrillConnectionFactory;

import java.sql.Connection;

public class NoDrillConnectionFactory implements DrillConnectionFactory {
    @Override
    public Connection createConnection() {
        return null;
    }
}
