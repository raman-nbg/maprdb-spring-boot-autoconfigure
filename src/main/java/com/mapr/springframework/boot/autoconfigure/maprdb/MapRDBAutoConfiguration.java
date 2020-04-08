package com.mapr.springframework.boot.autoconfigure.maprdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapr.springframework.data.maprdb.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnClass({MapRTemplate.class })
@EnableConfigurationProperties(MapRDBProperties.class)
public class MapRDBAutoConfiguration {

    private MapRDBProperties properties;

    MapRDBAutoConfiguration(MapRDBProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(MapROperations.class)
    MapROperations maprOperations(MapROptions maprOptions) {

        return new MapRTemplate(maprOptions);
    }

    @Bean
    @ConditionalOnMissingBean(MapROptions.class)
    MapROptions maprOptions(
            @Autowired(required = false) ObjectMapper objectMapper,
            @Autowired(required = false) DrillConnectionFactory drillConnectionFactory,
            @Autowired(required = false) OjaiConnectionFactory ojaiConnectionFactory) {

        MapROptions options = new MapROptions();
        options.setAutoCreateTables(properties.isAutoCreateTables());
        options.setTableRootPath(properties.getTableRootPath());

        if (objectMapper != null) {
            options.setObjectMapper(objectMapper);
        }

        if (drillConnectionFactory != null) {
            options.setDrillConnectionFactory(drillConnectionFactory);
        }

        if (ojaiConnectionFactory != null) {
            options.setOjaiConnectionFactory(ojaiConnectionFactory);
        }

        return options;
    }

    @Bean
    @ConditionalOnMissingBean(DrillConnectionFactory.class)
    DrillConnectionFactory drillConnectionFactory() {

        MapRDBProperties.DrillProperties drillProperties = properties.getDrill();

        if (!drillProperties.isEnabled()) {
            return new NoDrillConnectionFactory();
        }

        if (drillProperties.getConnectionMode() == MapRDBProperties.DrillProperties.ConnectionMode.Zookeeper) {
            List<String> servers = drillProperties.getZookeeperServers();
            String clusterName = drillProperties.getClusterName();
            return new DrillZookeeperConnectionFactory(servers, clusterName);
        } else {
            throw new IllegalArgumentException("Only connection mode 'Zookeeper' is supported");
        }
    }

    @Bean
    @ConditionalOnMissingBean(OjaiConnectionFactory.class)
    OjaiConnectionFactory ojaiConnectionFactory() {
        return new DefaultOjaiConnectionFactory();
    }
}