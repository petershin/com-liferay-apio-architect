/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.sample.persons.resource;

import com.liferay.apio.architect.pagination.Pagination;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public interface UserService {
	public default List<User> getPersons(Pagination pagination) {
		return Arrays.asList(() -> "Alex", () -> "Carlos");
	}

	public default User getPerson(Long id) {
		return () -> "Jose";
	}

	public default int getTotalPersons(boolean b) {
		return 10;
	}
}