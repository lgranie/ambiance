package org.tiling.memo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link CacheFactory} that uses a <i>Least-Recently-Used</i> (LRU) cache
 * as the cache implementation. A LRU cache holds a bounded number of entries.
 * The cache ensures that is does not exceed its maximum size by discarding
 * the oldest entry, that is, the one that was least recently accessed.
 */
public class LruCacheFactory implements CacheFactory {

  private int maxEntries;

  public LruCacheFactory(final int maxEntries) {
    this.maxEntries = maxEntries;
  }

  public Map<List<Object>, Object> createCache() {
	  Map<List<Object>, Object> cache =
      new LinkedHashMap<List<Object>, Object>(maxEntries + 1, 0.75F, true) {
    
		private static final long serialVersionUID = 4081955899720371622L;

	// Remove the oldest entry if there are more
      // than maxEntries
      public boolean removeEldestEntry(Map.Entry e) {
        return size() > maxEntries;
      }
    };
    return Collections.synchronizedMap(cache);
  }

}
