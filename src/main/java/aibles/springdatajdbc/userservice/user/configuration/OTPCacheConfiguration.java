package aibles.springdatajdbc.userservice.user.configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OTPCacheConfiguration {

    private static final int OTP_EXPIRE_MINUTES = 3;

    @Bean
    public LoadingCache<String, String> createOtpCacheBean() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(OTP_EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key.toUpperCase();
                    }
                });
    }
}
