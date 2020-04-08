package com.mapr.springframework.boot.autoconfigure.maprdb;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "spring.data.maprdb")
public class MapRDBProperties {
    private boolean autoCreateTables = false;
    private String tableRootPath = "";
    private DrillProperties drill = new DrillProperties();

    public boolean isAutoCreateTables() {
        return autoCreateTables;
    }

    public void setAutoCreateTables(boolean autoCreateTables) {
        this.autoCreateTables = autoCreateTables;
    }

    public String getTableRootPath() {
        return tableRootPath;
    }

    public void setTableRootPath(String tableRootPath) {
        this.tableRootPath = tableRootPath;
    }

    public DrillProperties getDrill() {
        return drill;
    }

    public void setDrill(DrillProperties drill) {
        this.drill = drill;
    }

    public static class DrillProperties {
        private boolean enabled = false;
        private ConnectionMode connectionMode;
        private List<String> zookeeperServers;
        private String clusterName;

        public List<String> getZookeeperServers() {
            return zookeeperServers;
        }

        public void setZookeeperServers(List<String> zookeeperServers) {
            this.zookeeperServers = zookeeperServers;
        }

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public ConnectionMode getConnectionMode() {
            return connectionMode;
        }

        public void setConnectionMode(ConnectionMode connectionMode) {
            this.connectionMode = connectionMode;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public static enum ConnectionMode {
            Zookeeper,
            Drillbit
        }
    }
}
