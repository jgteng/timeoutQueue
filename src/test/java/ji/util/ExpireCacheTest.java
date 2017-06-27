package ji.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by ji on 17-6-22.
 */
public class ExpireCacheTest {

    public static void main(String[] args) throws InterruptedException {

        //创建一个ExpireCache,
        // 构造函数setExpireTime(long firstExpireTime, long subsequentExpireTime, TimeUnit unit, boolean continueCheckAfterExpire)
        ExpireCache<String, String> expireCache = ExpireCache
                // 四个参数分别是：首次超时时间，超时继续检查时间间隔，时间单位，超时后是否连续检查
                .setExpireTime(10, 2, TimeUnit.SECONDS, true)
                //超时多少次从队列中移除，第四个参数为true才有效，为false时第一次超时就会被删掉
                .setCheckTimes(5)
                //注册一个超时回调，元素超时后会触发handler方法
                .build(new ExpireCallBack() {
                    @Override
                    public void handler(Object key, boolean isEnd) throws Exception {
                        System.out.println("元素:[" + key + "]超时,本次超时后是否删除：" + isEnd);
                    }
                });

        expireCache.put("key_a", "v_a");
        expireCache.put("key_b", "v_b");

        TimeUnit.SECONDS.sleep(2);

        //expire会重新计算超时时间
        expireCache.expire("key_a");

        TimeUnit.SECONDS.sleep(2);

        System.out.println(expireCache.get("key_a"));

        //put可以更新已存在的值，并重新计算超时时间
        expireCache.put("key_a", "v_a_new");

        System.out.println(expireCache.get("key_a"));

        Thread.sleep(100000000);
    }
}
