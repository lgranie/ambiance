package org.tiling.memo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link CacheFactory} that uses <code>java.util.HashMap</code>
 * as the cache implementation.
 */
public class DefaultCacheFactory implements CacheFactory {

  public Map<List<Object>, Object> createCache() {
    return Collections.synchronizedMap(new HashMap<List<Object>, Object>());
  }

}
