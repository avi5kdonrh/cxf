/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.cxf.jaxrs.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/bookstore/")
public class SuperBookStore extends BookSuperClass implements BookInterface {
    public SuperBookStore() {
    }

    public SuperBook getBook(String id) {
        return null;
    }

    @Override
    public SuperBook getNewBook(String id, Boolean isNew) {
        return null;
    }

    @POST
    @Path("/books")
    @Consumes(MediaType.APPLICATION_XML)
    public void addBook(Book book) {
    }

    @PUT
    @Path("/books/")
    public Response updateBook(SuperBook book) {
        return null;
    }

    @DELETE
    @Path("/books/{bookId}/")
    public Response deleteBook(@PathParam("bookId") String id) {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public String getAuthor() {
        return null;
    }
}


