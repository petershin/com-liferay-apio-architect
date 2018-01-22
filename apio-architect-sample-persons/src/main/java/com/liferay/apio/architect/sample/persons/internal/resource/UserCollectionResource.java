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

package com.liferay.apio.architect.sample.persons.internal.resource;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.persons.resource.UserService;
import com.liferay.apio.architect.sample.persons.resource.User;
import com.liferay.apio.architect.sample.persons.resource.UserResource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class UserCollectionResource implements UserResource {

	@Override
	public CollectionRoutes<User> collectionRoutes(
		CollectionRoutes.Builder<User> builder) {

		return builder.addGetter(
			pagination -> {
				List<User> users = _userService.getPersons(pagination);

				return new PageItems<>(
					users, _userService.getTotalPersons(true));
			}
		).build();
	}

	@Override
	public String getName() {
		return "persons";
	}

	@Override
	public ItemRoutes<User> itemRoutes(
		ItemRoutes.Builder<User, Long> builder) {

		return builder.addGetter(
			_userService::getPerson
		).build();
	}

	@Override
	public Representor<User, Long> representor(
		Representor.Builder<User, Long> builder) {

		return builder.types(
			"User"
		).identifier(
			User::getId
		).addString(
			"name", User::getName
		).addBoolean(
			"isEmployee", User::isEmployee
		).build();
	}

	@Reference
	private UserService _userService;

}