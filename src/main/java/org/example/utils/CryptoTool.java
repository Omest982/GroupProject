package org.example.utils;

import org.hashids.Hashids;

public class CryptoTool {
    private final Hashids hashids;

    public CryptoTool(String salt){
        int minHashLength = 10;
        this.hashids = new Hashids(salt, minHashLength);
    }

    public String hashOf(Long val){
        return hashids.encode(val);
    }

    public Long idOf(String hash){
        long[] res = hashids.decode(hash);
        if(res != null && res.length > 0){
            return res[0];
        }
        return null;
    }
}
