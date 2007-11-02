package org.tiling.memo;

import java.util.List;
import java.util.Map;

/**
 * Defines a factory for creating specialist caches.
 */
public interface CacheFactory {
  public Map<List<Object>, Object> createCache();
}
