package org.ambiance.memoizer.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.tiling.memo.CacheFactory;

public class WeakCacheFactory implements CacheFactory {
	
	public Map<List<Object>, Object> createCache() {
	    return Collections.synchronizedMap(new WeakHashMap<List<Object>, Object>());
	}

}
