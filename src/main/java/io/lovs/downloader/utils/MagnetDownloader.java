package io.lovs.downloader.utils;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.net.InetPeerAddress;
import bt.runtime.BtClient;
import bt.runtime.Config;
import com.google.inject.Module;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:56
 **/
public class MagnetDownloader {
    private static Config config;
    private static Module dhtModule;
    static {
        config = new Config() {
            @Override
            public int getNumOfHashingThreads() {
                return Runtime.getRuntime().availableProcessors() * 2;
            }
        };

        DHTConfig dhtConfig = new DHTConfig();
        dhtConfig.setBootstrapNodes(Arrays.asList(
                new InetPeerAddress("127.0.0.1", 6881)));
        dhtModule = new DHTModule(dhtConfig);

    }

    public static void download(String url, String path) {
        Path targetDirectory = new File(path).toPath();

// create file system based backend for torrent data
        Storage storage = new FileSystemStorage(targetDirectory);

// create client with a private runtime
        BtClient client = Bt.client()
                .config(config)
                .storage(storage)
                .magnet(url)
                .autoLoadModules()
                .module(dhtModule)
                .stopWhenDownloaded()
                .build();
        // launch
        client.startAsync(state -> {
            if (state.getPiecesRemaining() == 0) {
                client.stop();
            }
        }, 1000).join();
    }


    public static void main(String[] args) {
        download("magnet:?xt=urn:btih:af0d9aa01a9ae123a73802cfa58ccaf355eb19f1", "G:\\tunder_download");
    }

}
