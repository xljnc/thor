package com.wt.test.thor.test;

import lombok.experimental.UtilityClass;
import org.neo4j.dbms.api.DatabaseManagementService;

/**
 * @author qiyu
 * @date 2023/1/28 16:47
 */
@UtilityClass
public class Neo4jUtil {
    /**
     * Registers a shutdown hook for the Neo4j instance so that it
     * shuts down nicely when the VM exits (even if you "Ctrl-C" the
     * running application).
     **/
    public static void registerShutdownHook(final DatabaseManagementService managementService) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> managementService.shutdown()));
    }
}
