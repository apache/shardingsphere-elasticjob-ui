package org.apache.shardingsphere.elasticjob.lite.ui.exception;

/**
 * Driver class not in whitelist exception.
 */
public final class DriverClassNotInWhitelistException extends RuntimeException {
    
    public DriverClassNotInWhitelistException(final String driverClass) {
        super("Please add '" + driverClass + "' to [dynamic.datasource.allowed-driver-classes] in application.properties");
    }
}
