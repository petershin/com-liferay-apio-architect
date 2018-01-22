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
import com.liferay.apio.architect.sample.persons.resource.ClientId;
import com.liferay.apio.architect.sample.persons.resource.User;
import com.liferay.apio.architect.sample.persons.resource.UserService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class ClientCollectionResource
	implements CollectionResource<User, ClientId> {

	@Reference
	private UserService _userService;

	@Override
	public CollectionRoutes<User> collectionRoutes(
		CollectionRoutes.Builder<User> builder) {

		return builder.addGetter(
			pagination -> {
				List<User> collect = _userService.getPersons(pagination);

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
	public ItemRoutes<User> itemRoutes(
		ItemRoutes.Builder<User, ClientId> builder) {

		return builder.addGetter(
			id -> {
				User user = _userService.getPerson(id.get());

				if (user.isEmployee()) {
					throw new NotFoundException();
				}

				return user;
			}
		).build();
	}

	@Override
	public Representor<User, ClientId> representor(
		Representor.Builder<User, ClientId> builder) {

		return builder.types(
			"ClientId"
		).identifier(
			user -> user::getId
		).addString(
			"name", User::getName
		).build();
	}

}