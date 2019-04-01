import redis.clients.jedis.Jedis;

public class RedisTest {
    public static void main(String[] args) {
    Jedis redis = new Jedis("127.0.0.1",6379);
    redis.auth("123456");
    String result = redis.ping();
    System.out.println(result);
    redis.select(0);
    String name  = redis.get("one");
    String  lll= redis.set("value","new-value");
    if (lll.equalsIgnoreCase("ok"))
    {
        System.out.println("Add to db success");
    }
    System.out.println(name);

    }

}
