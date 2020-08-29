package org.uce.fimeped.web.rest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class SortUtil {

	public static Sort generateSortRequest(String cadena) {
		List<Order> orders;	
		Order order;
		StringTokenizer tokens ;

		if (cadena != null && !cadena.isEmpty()) {
			tokens = new StringTokenizer(cadena);
			orders = new ArrayList<>();
			while (tokens.hasMoreElements()) {
				String p = (String) tokens.nextElement();

				if (p.contains("-")) {
					order = new Order(Sort.Direction.DESC, p.replace("-", ""));
				} else {
					order = new Order(Sort.Direction.ASC, p);
				}
				orders.add(order);

			}
			return new Sort(orders);
		}
		return null;

	}

}
