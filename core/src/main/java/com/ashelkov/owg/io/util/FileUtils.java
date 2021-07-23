package com.ashelkov.owg.io.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Date;
import java.util.Set;

import com.ashelkov.owg.wallet.Wallet;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private static final Set<PosixFilePermission> WALLET_DIR_PERMISSIONS =
            PosixFilePermissions.fromString("rwxr-x---");
    private static final Set<PosixFilePermission> WALLET_FILE_PERMISSIONS =
            PosixFilePermissions.fromString("rwx------");

    private static final String OSX_WALLET_DIR = String.format("Library%sWallets", File.separator);
    private static final String WINDOWS_WALLET_DIR = "Wallets";
    private static final String LINUX_WALLET_DIR = ".wallets";

    private static final String DEFAULT_FILE_EXT = "wal";
    private static final String DEFAULT_FILE_NAME = String.format(
            "%s.%s",
            new java.sql.Date(System.currentTimeMillis()),
            DEFAULT_FILE_EXT);

    private static void createDirectory(Path dir) {
        try {
            Files.createDirectory(dir, PosixFilePermissions.asFileAttribute(WALLET_DIR_PERMISSIONS));
        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }

    public static String getDefaultWalletDir() {

        String root;
        String dir;

        if (SystemUtils.IS_OS_MAC_OSX) {
            root = System.getProperty("user.home");
            dir = OSX_WALLET_DIR;
        } else if (SystemUtils.IS_OS_WINDOWS) {
            root = System.getenv("APPDATA");
            dir = WINDOWS_WALLET_DIR;
        } else {
            root = System.getProperty("user.home");
            dir = LINUX_WALLET_DIR;
        }

        return String.join(File.separator, root, dir, DEFAULT_FILE_NAME);
    }

    public static void saveWalletToFile(Path filePath, String mnemonic, Wallet wallet) {

        // Check if directory exists - if not, create it
        Path rootDir = filePath.getParent();
        if (!Files.exists(rootDir)) {
            createDirectory(rootDir);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, US_ASCII)) {

            // Output timestamp
            writer.write(new Date(System.currentTimeMillis()).toString());
            writer.write('\n');
            writer.write('\n');

            // Output mnemonic
            writer.write(mnemonic);
            writer.write('\n');
            writer.write('\n');

            // Output wallet
            writer.write(wallet.toString());
            writer.write('\n');

            Files.setPosixFilePermissions(filePath, WALLET_FILE_PERMISSIONS);

        } catch (IOException e) {
            logger.error(e.getMessage());
            System.exit(1);
        }
    }
}