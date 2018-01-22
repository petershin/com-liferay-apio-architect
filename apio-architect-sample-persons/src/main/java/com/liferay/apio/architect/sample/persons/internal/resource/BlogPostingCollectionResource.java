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

import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.CollectionResource;
import com.liferay.apio.architect.routes.CollectionRoutes;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.sample.persons.internal.resource.news.Employee;
import com.liferay.apio.architect.sample.persons.resource.ClientId;
import com.liferay.apio.architect.sample.persons.resource.UserService;
import com.liferay.apio.architect.sample.persons.resource.UserResource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class BlogPostingCollectionResource
	implements CollectionResource<BlogPostingCollectionResource.BlogPosting, Long> {

	@Reference
	private UserService _userService;

	@Override
	public CollectionRoutes<BlogPosting> collectionRoutes(
		CollectionRoutes.Builder<BlogPosting> builder) {

		return null;
	}

	@Override
	public String getName() {
		return "persons";
	}

	@Override
	public ItemRoutes<BlogPosting> itemRoutes(
		ItemRoutes.Builder<BlogPosting, Long> builder) {

		return null;
	}

	@Override
	public Representor<BlogPosting, Long> representor(
		Representor.Builder<BlogPosting, Long> builder) {

		return builder.types(
			"BlogPosting"
		).identifier(
			BlogPosting::getId
		).addLinkedModel(
			"person", Employee.class,
		).build();
	}

	public static class BlogPosting {

		public Long getId() {
			return 42L;
		}

		public long getPersonId() {
			return 22L;
		}
	}
}