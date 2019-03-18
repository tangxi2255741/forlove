package com.txr.forlove.common.advice.log;

import com.txr.forlove.common.advice.AdviceSupport;
import com.txr.forlove.common.utils.JsonLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.PrioritizedParameterNameDiscoverer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月1日
 * @since 2018年8月1日
 * 
 */
public class LogRecordAdvice extends AdviceSupport {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected final PrioritizedParameterNameDiscoverer nameDiscoverer = new PrioritizedParameterNameDiscoverer();

	public LogRecordAdvice() {
		nameDiscoverer.addDiscoverer(new LocalVariableTableParameterNameDiscoverer());
		nameDiscoverer.addDiscoverer(new DefaultParameterNameDiscoverer());
	}

	protected JsonLog toJson(Object src) {
		return JsonLog.create(src);
	}
	
	
	  
	static class DefaultParameterNameDiscoverer implements ParameterNameDiscoverer {
		private final Map<Class<?>, Map<Method, String[]>> parameterNamesCache = new ConcurrentHashMap<Class<?>, Map<Method, String[]>>(32);
		private final Map<Class<?>, Map<Constructor<?>, String[]>> ctorNamesCache = new ConcurrentHashMap<Class<?>, Map<Constructor<?>, String[]>>(32);

		@Override
		public String[] getParameterNames(Method method) {
			Class<?> clazz = method.getDeclaringClass();
			Map<Method, String[]> clazzMap = parameterNamesCache.get(clazz);

			if (clazzMap != null) {
				String[] names = clazzMap.get(method);
				if (names != null) {
					return names;
				}
			}

			Class<?>[] types = method.getParameterTypes();
			String[] names = nameDiscover(types);
			if (clazzMap == null) {
				clazzMap = new HashMap<Method, String[]>();
				parameterNamesCache.put(clazz, clazzMap);
			}
			clazzMap.put(method, names);

			return names;
		}

		protected String[] nameDiscover(Class<?>[] types) {
			if (types == null || types.length == 0) {
				return new String[0];
			}

			ArrayList<String> nameList = new ArrayList<String>(types.length);
			int i = 0;
			for (Class<?> t : types) {
				if(t.isPrimitive() || !t.getName().startsWith("com.jd")){
					nameList.add("arg" + (i++));
				} else {
					String simpleName = t.getSimpleName();
					String newName = simpleName.substring(0, 1).toLowerCase() + (simpleName.length() > 1 ? simpleName.substring(1) : "");
					if(nameList.contains(newName)){
						newName = newName + i++;
					}
					nameList.add(newName);
				}
			}

			
			String[] names = nameList.toArray(new String[types.length]);
			return names;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public String[] getParameterNames(Constructor ctor) {
			Class<?> clazz = ctor.getDeclaringClass();
			Map<Constructor<?>, String[]> clazzMap = ctorNamesCache.get(clazz);

			if (clazzMap != null) {
				String[] names = clazzMap.get(ctor);
				if (names != null) {
					return names;
				}
			}

			Class<?>[] types = ctor.getParameterTypes();
			String[] names = nameDiscover(types);
			if (clazzMap == null) {
				clazzMap = new HashMap<Constructor<?>, String[]>();
				ctorNamesCache.put(clazz, clazzMap);
			}
			clazzMap.put(ctor, names);

			return names;
		}

	}
}
