package com.wt.test.thor.test;

import org.hashids.Hashids;

import java.util.Arrays;

/**
 * @author qiyu
 * @since 2023/5/10
 */
public class HashIdsTest {
    public static void main(String[] args) {
        final String SALT = "this is my salt";
        final int MIN_HASH_LENGTH = 11;
        
        Hashids hashids = new Hashids(SALT, MIN_HASH_LENGTH);
        String encryptString = hashids.encode(347L);
        
        System.out.println(encryptString);
        long[] decrypedNumbers = hashids.decode(encryptString);
        Arrays.stream(decrypedNumbers).forEach(System.out::println); // 347
        
    }
}
