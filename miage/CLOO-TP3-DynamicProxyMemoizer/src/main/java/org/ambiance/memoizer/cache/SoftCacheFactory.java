package org.ambiance.memoizer.cache;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tiling.memo.CacheFactory;

public class SoftCacheFactory implements CacheFactory {

	public Map<List<Object>, Object> createCache() {

		Map<List<Object>, Object> cache = new HashMap<List<Object>, Object>() {

			private static final long serialVersionUID = 4205152158883034653L;

			public Object get(Object key) {
				if (null != super.get(key))
					return ((SoftReference) super.get(key)).get();
				else
					return null;
			}

			public Object put(List<Object> key, Object value) {
				SoftReference<Object> sr = new SoftReference<Object>(value);

				super.put(key, sr);

				return sr.get();
			}

		};

		return Collections.synchronizedMap(cache);
	}

}
