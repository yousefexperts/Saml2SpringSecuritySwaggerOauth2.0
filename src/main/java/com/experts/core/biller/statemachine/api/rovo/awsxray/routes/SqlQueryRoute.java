package com.experts.core.biller.statemachine.api.rovo.awsxray.routes;

import org.apache.camel.builder.RouteBuilder;

import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.beans.AuditMapper;

public class SqlQueryRoute extends RouteBuilder {

    public static final String SQL_QUERY= "seda:sql-query";

    @Override
    public void configure() throws Exception {
        from(SQL_QUERY).routeId("sql-query")
                .log("Performing sample Camel-based SQL query")
                .to("sql:SELECT *  FROM audit")
                .bean(AuditMapper.class)
                .log("${body}");
    }
}
