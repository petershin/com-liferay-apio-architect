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

package com.liferay.apio.architect.sample.persons.internal.resource.news;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.persons.resource.User;
import com.liferay.apio.architect.sample.persons.resource.UserService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class EmployeeCollectionResource
	implements CollectionResource<Employee, Long> {

	@Reference
	private UserService _userService;

	@Override
	public CollectionRoutes<Employee> collectionRoutes(
		CollectionRoutes.Builder<Employee> builder) {

		return builder.addGetter(
			pagination -> {
				List<Employee> collect = _userService.getPersons(pagination).stream().map(
					Employee::new
				).collect(
					Collectors.toList()
				);

				return new PageItems<>(
					collect, _userService.getTotalPersons(true));
			}
		).build();
	}

	@Override
	public String getName() {
		return "clients";
	}

	@Override
	public ItemRoutes<Employee> itemRoutes(
		ItemRoutes.Builder<Employee, Long> builder) {

		return builder.addGetter(
			id -> {
				User user = _userService.getPerson(id);

				if (user.isEmployee()) {
					throw new NotFoundException();
				}

				return new Employee(user);
			}
		).build();
	}

	@Override
	public Representor<Employee, Long> representor(
		Representor.Builder<Employee, Long> builder) {

		return builder.types(
			"Employee"
		).identifier(
			Employee::getId
		).addString(
			"name", Employee::getName
		).build();
	}

}