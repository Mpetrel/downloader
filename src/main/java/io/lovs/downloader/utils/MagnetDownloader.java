package io.lovs.downloader.utils;

import bt.Bt;
import bt.BtException;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;
import com.google.inject.Module;
import io.lovs.downloader.entity.downloader.DownloaderEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:56
 **/
@Slf4j
public class MagnetDownloader {
    private static Config config;
    private static Module dhtModule;
    private static final Map<String, DownloaderEntity> downloadInfo = new HashMap<>();
    private static final Pattern magnetPattern = Pattern.compile("xt=urn:btih:([A-Za-z0-9]){40}");

    static {
        config = new Config() {
            @Override
            public int getNumOfHashingThreads() {
                return Runtime.getRuntime().availableProcessors() * 2;
            }
        };
        // 设置网卡 默认选择可连接互联网的网卡
        config.setAcceptorAddress(getInetAddressFromNetworkInterfaces());
        dhtModule = new DHTModule(new DHTConfig() {
            @Override
            public boolean shouldUseRouterBootstrap() {
                return true;
            }
        });
    }

    public static void download(String url, String path) {
        Path targetDirectory = new File(path).toPath();
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
        CompletableFuture<?> future =  client.startAsync(state -> {
            DownloaderEntity downloaderEntity = downloadInfo.get(magnetHash(url));
            if (downloaderEntity == null) {
                downloaderEntity = new DownloaderEntity();
                downloadInfo.put(magnetHash(url), downloaderEntity);
            }
            downloaderEntity.setDownloaded(state.getDownloaded());
            downloaderEntity.setPiecesComplete(state.getPiecesComplete());
            downloaderEntity.setPiecesInComplete(state.getPiecesIncomplete());
            downloaderEntity.setPiecesNotSkipped(state.getPiecesNotSkipped());
            downloaderEntity.setPiecesRemaining(state.getPiecesRemaining());
            downloaderEntity.setPiecesSkipped(state.getPiecesSkipped());
            downloaderEntity.setPiecesTotal(state.getPiecesTotal());
            downloaderEntity.setUploaded(state.getUploaded());

            if (state.getPiecesRemaining() == 0) {
                client.stop();
            }
        }, 1000);
        future.join();
    }

    /**
     * 返回下载信息
     * @param key
     * @return
     */
    public static DownloaderEntity getDownloadInfo(String key) {
        return downloadInfo.get(key);
    }


    public static void main(String[] args) {
        download("magnet:?xt=urn:btih:54dca0477d74d88ed051a9cd62fe5359151e7823&dn=elementaryos-0.4.1-stable.20180214.iso&tr=https%3A%2F%2Fashrise.com%3A443%2Fphoenix%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.ccc.de%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80%2Fannounce&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80%2Fannounce&ws=http://sgp1.dl.elementary.io/download/MTUzMTM2MDI5OA==/elementaryos-0.4.1-stable.20180214.iso", "G:\\tunder_download");
        //getInetAddressFromNetworkInterfaces();
        //System.out.println("54dca0477d74d88ed051a9cd62fe5359151e7823".length());
    }


    /**
     * 解析磁力地址中的hash
     * @return
     */
    private static String magnetHash(String magnet) {
        Matcher matcher = magnetPattern.matcher(magnet);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }




    /**
     * 测试选中ip是否能够连接网络
     * @return
     */
    private static InetAddress getInetAddressFromNetworkInterfaces() {
        InetAddress selectedAddress = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            outer:
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isMulticastAddress() && !inetAddress.isLoopbackAddress()
                            && inetAddress.getAddress().length == 4 && isNetWork(inetAddress)) {
                        selectedAddress = inetAddress;
                        break outer;
                    }
                }
            }

        } catch (SocketException e) {
            throw new BtException("Failed to retrieve network address", e);
        }
        // explicitly returning a loopback address here instead of null;
        // otherwise we'll depend on how JDK classes handle this,
        // e.g. java/net/Socket.java:635
        InetAddress a = (selectedAddress == null) ? InetAddress.getLoopbackAddress() : selectedAddress;
        return a;
    }

    /**
     * 测试网卡是否连接互联网
     *
     * @return
     */
    private static boolean isNetWork(InetAddress address) {
        SocketAddress from = new InetSocketAddress(address.getHostAddress(), 0);
        SocketAddress to = new InetSocketAddress("www.baidu.com", 443);
        Socket s = new Socket();
        try {
            s.bind(from);
            s.connect(to);
            return s.isConnected();
        } catch (IOException e) {
            log.debug("connect fail : {}" , address.getHostAddress());
        }
        return false;
    }


}
