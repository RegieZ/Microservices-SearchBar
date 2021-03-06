package com.regino.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class IDWorker {
    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    private final static long init = 1288834974657L;
    // 机器标识位数
    private final static long workerIDBits = 5L;
    // 数据中心标识位数
    private final static long dataCenterIDBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIDBits);
    // 数据中心ID最大值
    private final static long maxDataCenterID = -1L ^ (-1L << dataCenterIDBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIDShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long dataCenterIDShift = sequenceBits + workerIDBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIDBits + dataCenterIDBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    // 上次生产id时间戳
    private static long lastTimestamp = -1L;
    private final long workerId;
    // 数据标识ID部分
    private final long dataCenterID;
    // 并发控制
    private long sequence = 0L;

    public IDWorker() {
        this.dataCenterID = getDatacenterId(maxDataCenterID);
        this.workerId = getMaxWorkerId(dataCenterID, maxWorkerId);
    }

    /**
     * @param workerID：工作机器ID
     * @param dataCenterID：序列号
     */
    public IDWorker(long workerID, long dataCenterID) {
        if (workerID > maxWorkerId || workerID < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterID > maxDataCenterID || dataCenterID < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterID));
        }
        this.workerId = workerID;
        this.dataCenterID = dataCenterID;
    }

    /**
     * 获取 maxWorkerID
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识ID部分
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDataCenterID: " + e.getMessage());
        }
        return id;
    }

    public static void main(String[] args) {

        IDWorker idWorker = new IDWorker(0, 0);

        for (int i = 0; i < 10000; i++) {
            long nextId = idWorker.nextId();
            System.out.println(nextId);
        }
    }

    /**
     * 获取下一个ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //4096000 ---> 4096000
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - init) << timestampLeftShift)
                | (dataCenterID << dataCenterIDShift)
                | (workerId << workerIDShift) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
