package org.tiling.memo;

/**
 * Encapsulates the hit and miss statistics for a cache.
 */
public interface CacheStatistics {
  /**
   * Returns the number of cache hits (method calls whose
   * return values were in the cache).
   * @return the number of cache hits
   */
  public int getHits();
  /**
   * Returns the number of cache misses (method calls whose
   * return values were not in the cache, so needed to
   * invoke the underlying method).
   * @return the number of cache misses
   */
  public int getMisses();
}
