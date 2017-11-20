package com.cc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: NetUtils
 * Description: NetUtils
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2017/5/4
 *
 * @author <a href=mailto:zhouzhichao@cc.com>chaochao</a>
 */
public class NetUtils {

    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);
    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;
    public static final String ANYHOST = "0.0.0.0";
    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    public static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public NetUtils() {
    }

    public static boolean isInvalidPort(int port) {
        return port > '\uffff' || port < 0;
    }

    public static boolean isRandomPort(int port) {
        return port < 0;
    }

    public static int getAvailablePort(String host, int port) throws Exception {
        if(!isAnyHost(host) && !isLocalHost(host) && !isHostInNetworkCard(host)) {
            throw new Exception("The host " + host + " is not found in network cards, please check config");
        } else {
            if(port < 0) {
                port = 22000;
            }

            for(int i = port; i <= '\uffff'; ++i) {
                ServerSocket ss = null;

                try {
                    ss = new ServerSocket();
                    ss.bind(new InetSocketAddress(host, i));
                    logger.debug("ip:{} port:{} is available", host, Integer.valueOf(i));
                    int e = i;
                    return e;
                } catch (IOException var14) {
                    logger.warn("Can\'t bind to address [{}:{}], Maybe 1) The port has been bound. 2) The network card of this host is not exists or disable. 3) The host is wrong.", host, Integer.valueOf(i));
                    logger.info("Begin try next port(auto +1):{}", Integer.valueOf(i + 1));
                } finally {
                    if(ss != null) {
                        try {
                            ss.close();
                        } catch (IOException var13) {
                            ;
                        }
                    }

                }
            }

            throw new Exception("Can\'t bind to ANY port of " + host + ", please check config");
        }
    }

    public static boolean isLocalHost(String host) {
        return StringUtils.isNotBlank(host) && (LOCAL_IP_PATTERN.matcher(host).matches() || "localhost".equalsIgnoreCase(host));
    }

    public static boolean isAnyHost(String host) {
        return "0.0.0.0".equals(host);
    }

    public static boolean isIPv4Host(String host) {
        return StringUtils.isNotBlank(host) && IPV4_PATTERN.matcher(host).matches();
    }

    private static boolean isInvalidLocalHost(String host) {
        return StringUtils.isBlank(host) || isAnyHost(host) || isLocalHost(host);
    }

    private static boolean isValidAddress(InetAddress address) {
        if(address != null && !address.isLoopbackAddress()) {
            String name = address.getHostAddress();
            return name != null && !isAnyHost(name) && !isLocalHost(name) && isIPv4Host(name);
        } else {
            return false;
        }
    }

    public static boolean isHostInNetworkCard(String host) {
        try {
            InetAddress e = InetAddress.getByName(host);
            return NetworkInterface.getByInetAddress(e) != null;
        } catch (Exception var2) {
            return false;
        }
    }

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null?null:address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;

        try {
            localAddress = InetAddress.getLocalHost();
            if(isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            logger.warn("Error when retriving ip address: " + var6.getMessage(), var6);
        }

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            if(e != null) {
                while(e.hasMoreElements()) {
                    try {
                        NetworkInterface e1 = (NetworkInterface)e.nextElement();
                        Enumeration addresses = e1.getInetAddresses();
                        if(addresses != null) {
                            while(addresses.hasMoreElements()) {
                                try {
                                    InetAddress e2 = (InetAddress)addresses.nextElement();
                                    if(isValidAddress(e2)) {
                                        return e2;
                                    }
                                } catch (Throwable var5) {
                                    logger.warn("Error when retriving ip address: " + var5.getMessage(), var5);
                                }
                            }
                        }
                    } catch (Throwable var7) {
                        logger.warn("Error when retriving ip address: " + var7.getMessage(), var7);
                    }
                }
            }
        } catch (Throwable var8) {
            logger.warn("Error when retriving ip address: " + var8.getMessage(), var8);
        }

        logger.error("Can\'t get valid host, will use 127.0.0.1 instead.");
        return localAddress;
    }

    public static String toAddressString(InetSocketAddress address) {
        return address == null?"":toIpString(address) + ":" + address.getPort();
    }

    public static String toIpString(InetSocketAddress address) {
        if(address == null) {
            return null;
        } else {
            InetAddress inetAddress = address.getAddress();
            return inetAddress == null?address.getHostName():inetAddress.getHostAddress();
        }
    }

    public static String getLocalHostByRegistry(String registryIp) {
        String host = null;
        if(registryIp != null && registryIp.length() > 0) {
            List addrs = getIpListByRegistry(registryIp);

            for(int i = 0; i < addrs.size(); ++i) {
                InetAddress address = getLocalHostBySocket((InetSocketAddress)addrs.get(i));
                if(address != null) {
                    host = address.getHostAddress();
                    if(host != null && !isInvalidLocalHost(host)) {
                        return host;
                    }
                }
            }
        }

        if(isInvalidLocalHost(host)) {
            host = getLocalHost();
        }

        return host;
    }

    private static InetAddress getLocalHostBySocket(InetSocketAddress remoteAddress) {
        InetAddress host = null;

        try {
            Socket e = new Socket();

            try {
                e.connect(remoteAddress, 1000);
                host = e.getLocalAddress();
            } finally {
                try {
                    e.close();
                } catch (Throwable var10) {
                    ;
                }

            }
        } catch (Exception var12) {
            logger.warn("Can not connect to host {}, cause by :{}", remoteAddress.toString(), var12.getMessage());
        }

        return host;
    }

    public static List<InetSocketAddress> getIpListByRegistry(String registryIp) {
        ArrayList ips = new ArrayList();
        String defaultPort = null;
        String[] srcIps = registryIp.split(",");
        String[] ads = srcIps;
        int j = srcIps.length;

        for(int ip = 0; ip < j; ++ip) {
            String address = ads[ip];
            int a = address.indexOf("://");
            if(a > -1) {
                address = address.substring(a + 3);
            }

            String[] s1 = address.split(":");
            if(s1.length > 1) {
                if(defaultPort == null && s1[1] != null && s1[1].length() > 0) {
                    defaultPort = s1[1];
                }

                ips.add(new String[]{s1[0], s1[1]});
            } else {
                ips.add(new String[]{s1[0], defaultPort});
            }
        }

        ArrayList var11 = new ArrayList();

        for(j = 0; j < ips.size(); ++j) {
            String[] var12 = (String[])ips.get(j);

            try {
                InetSocketAddress var13 = new InetSocketAddress(var12[0], Integer.parseInt(var12[1] == null?defaultPort:var12[1]));
                var11.add(var13);
            } catch (Exception var10) {
                ;
            }
        }

        return var11;
    }

    public static boolean isMatchIPByPattern(String whitelist, String localIP) {
        if(StringUtils.isNotBlank(whitelist)) {
            if("*".equals(whitelist)) {
                return true;
            }

            String[] arr$ = whitelist.replace(',', ';').split(";", -1);
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String ips = arr$[i$];

                try {
                    String e;
                    Pattern pattern;
                    if(ips.contains("*")) {
                        e = ips.trim().replace(".", "\\.").replace("*", ".*");
                        pattern = Pattern.compile(e);
                        if(pattern.matcher(localIP).find()) {
                            return true;
                        }
                    } else if(!isIPv4Host(ips)) {
                        e = ips.trim().replace(".", "\\.");
                        pattern = Pattern.compile(e);
                        if(pattern.matcher(localIP).find()) {
                            return true;
                        }
                    } else if(ips.equals(localIP)) {
                        return true;
                    }
                } catch (Exception var8) {
                    logger.warn("syntax of pattern {} is invalid", ips);
                }
            }
        }

        return false;
    }

    public static String connectToString(InetSocketAddress local, InetSocketAddress remote) {
        return toAddressString(local) + " <-> " + toAddressString(remote);
    }

    public static String channelToString(SocketAddress local1, SocketAddress remote1) {
        try {
            InetSocketAddress e = (InetSocketAddress)local1;
            InetSocketAddress remote = (InetSocketAddress)remote1;
            return toAddressString(e) + " -> " + toAddressString(remote);
        } catch (Exception var4) {
            return local1 + "->" + remote1;
        }
    }

    public static boolean canTelnet(String ip, int port, int timeout) {
        Socket socket = null;

        boolean var5;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            boolean e = socket.isConnected();
            return e;
        } catch (Exception var15) {
            var5 = false;
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException var14) {
                    ;
                }
            }

        }

        return var5;
    }

    public static String getTransportKey(String ip, int port) {
        return ip + "::" + port;
    }

    public static String getClientTransportKey(String protocolName, String ip, int port) {
        return protocolName + "::" + ip + "::" + port;
    }

/*    public static String getTransportKey(Channel channel) {
        InetSocketAddress address = (InetSocketAddress)channel.remoteAddress();
        String remoteIp = toIpString(address);
        int port = address.getPort();
        return getTransportKey(remoteIp, port);
    }*/

}
