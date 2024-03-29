package com.ginkgocap.ywxt.video.service;

import java.util.List;

/**
 * Created by gintong on 2017/7/12.
 */
public interface IRedisService {

    public boolean set(String key, String value);

    public boolean set(String key, String value, long expire);

    public String get(String key);

    public abstract long del(String... keys);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);
}
