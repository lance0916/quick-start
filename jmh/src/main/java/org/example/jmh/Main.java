package org.example.jmh;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author WuQinglong
 * @date 2024/12/5 11:38
 */
public class Main {

    private static final ObjectMapper objectMapper = new ObjectMapper()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
            // 指定要运行的基准测试类
            .include(Main.class.getSimpleName())
            // 测试平均耗时
            .mode(Mode.AverageTime)
            // 预热迭代次数
            .warmupIterations(5)
            // 实际测量执行次数
            .measurementIterations(5)
            // 指定fork出多少个子进程来执行同一基准测试方法
            .forks(1)
            // 指定使用多少个线程来执行基准测试方法
            .threads(3)
            // 输出结果的时间单位
            .timeUnit(TimeUnit.MICROSECONDS)
            // 结果处理器
//            .output(FileUtil.getUserHomePath() + "/jmh.log")
//            .resultFormat(ResultFormatType.JSON)
            .build();
        new Runner(opt).run();
    }

    @Benchmark
    public Object hutool() {
        User User = create();
        String s = JSONUtil.toJsonStr(User);
        return JSONUtil.toBean(s, User.class);
    }

    @Benchmark
    public Object beanUtil() throws Exception {
        User User = create();
        User ret = new User();
        BeanUtils.copyProperties(User, ret);
        return ret;
    }

    @Benchmark
    public Object jackson() throws Exception {
        User User = create();

        String s = objectMapper.writeValueAsString(User);
        return objectMapper.readValue(s, User.class);
    }

    @Benchmark
    public Object gson() throws Exception {
        User User = create();
        String s = gson.toJson(User);
        return gson.fromJson(s, User.class);
    }

    @Benchmark
    public Object cloneFun() throws Exception {
        User User = create();
        return User.clone();
    }

    @Benchmark
    public Object mapstruct() throws Exception {
        User User = create();
        return UserMapper.INSTANCE.copy(User);
    }

    private User create() {
        User user = new User();
        user.setName1("name1");
        user.setName2("name2");
        user.setName3("name3");
        user.setName4("name4");
        user.setName5("name5");
        user.setName6("name6");
        user.setName7("name7");
        user.setName8("name8");
        user.setName9("name9");
        user.setName11("name11");
        user.setName12("name12");
        user.setName13("name13");
        user.setName14("name14");
        user.setName15("name15");
        user.setName16("name16");
        user.setName17("name17");
        user.setName18("name18");
        user.setName19("name19");
        user.setName20("name20");
        return user;
    }

}