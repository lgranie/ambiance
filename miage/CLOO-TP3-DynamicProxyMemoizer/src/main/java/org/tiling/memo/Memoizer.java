package org.tiling.memo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>Memoizer</code> provides static methods for creating memoized proxies
 * of classes.
 */
public class Memoizer implements CacheStatistics, InvocationHandler {

  private static final CacheFactory
    DEFAULT_CACHE_FACTORY = new DefaultCacheFactory();

  /**
   * Memoizes all the interfaces the specified object implements. The returned
   * memoized proxy acts in exactly the same way as the specified object,
   * except that returned values are cached. The cache used is one which caches
   * objects for the lifetime of the application.
   * @param object the object to be memoized
   * @return the memoized proxy class
   */
  public static Object memoize(Object object) {
    return memoize(object, DEFAULT_CACHE_FACTORY);
  }

  /**
   * Memoizes all the interfaces the specified object implements. The returned
   * memoized proxy acts in exactly the same way as the specified object,
   * except that returned values are cached. The caching strategy is determined
   * by the specified {@link CacheFactory}.
   * @param object the object to be memoized
   * @param cacheFactory the cache factory
   * @return the memoized proxy class
   */
  public static Object memoize(Object object,
      CacheFactory cacheFactory) {
	  
    return Proxy.newProxyInstance(
      object.getClass().getClassLoader(),
      object.getClass().getInterfaces(),
      new Memoizer(object, cacheFactory)
    );
  }

  /**
   * Returns the {@link CacheStatistics} for a memoized proxy class.
   * The instance of {@link CacheStatistics} that is returned is not a snapshot,
   * that is, it updates dynamically as methods on the memoized proxy class are
   * called.
   * @param memoizedClass the memoized proxy class, as returned by a
   * <code>memoize</code> method.
   * @return the cache statistics for the memoized proxy class
   */
  public static CacheStatistics getCacheStatistics(Object memoizedClass) {
    return (CacheStatistics) Proxy.getInvocationHandler(memoizedClass);
  }

  private Object object;
  private CacheFactory cacheFactory;
  private Map<Method, Map<List<Object>, Object>> caches = new HashMap<Method, Map<List<Object>, Object>>();

  private Memoizer(Object object,
      CacheFactory cacheFactory) {
    this.object = object;
    this.cacheFactory = cacheFactory;
  }

  public Object invoke(Object proxy, Method method,
      Object[] args) throws Throwable {

    if (method.getReturnType().equals(Void.TYPE)) {
    	
      // Don't cache void methods
      misses++;
      return invoke(method, args);
      
    } else {
    	
      Map<List<Object>, Object> cache = getCache(method);
      List<Object> key = Arrays.asList(args);
      Object value = cache.get(key);
      
      if (value == null && !cache.containsKey(key)) {
    	  
        misses++;
        value = invoke(method, args);
        cache.put(key, value);
        
      } else {
        hits++;
      }
      return value;
    }
  }

  private Object invoke(Method method, Object[] args)
      throws Throwable {
    try {
      return method.invoke(object, args);
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    }
  }

  private synchronized Map<List<Object>, Object> getCache(Method m) {
	Map<List<Object>, Object> cache = caches.get(m);
    if (cache == null) {
      cache = (Map<List<Object>, Object>) cacheFactory.createCache();
      caches.put(m, cache);
    }
    return cache;
  }

  private int hits, misses;

  public int getHits() {
    return hits;
  }

  public int getMisses() {
    return misses;
  }

}
