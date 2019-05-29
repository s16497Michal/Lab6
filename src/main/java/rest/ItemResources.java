package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Item;

@Path("/items")
@Stateless
public class ItemResources {

    @PersistenceContext
    EntityManager em;

    @POST
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(@PathParam("id") int id, Comment comment) {
        TypedQuery<Item> result = em.createNamedQuery("item.id", Item.class).setParameter("itemId", id);
        Item item;
        try {
            item = result.getSingleResult();
        } catch (NoResultException e) {
            return Response.status(404).build();
        }

        if (item.getComments() == null)
        	item.setComments(new ArrayList<Comment>());
        if (comment.getContent() == null)
            return Response.status(400).entity("no content in comment").build();

        comment.setId(item.getComments().size() + 1);
        item.getComments().add(comment);
        comment.setItem(item);
        em.persist(comment);
        return Response.status(201).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return em.createNamedQuery("item.all", Item.class).getResultList();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) {
        TypedQuery<Item> result = em.createNamedQuery("item.id", Item.class).setParameter("itemId", id);
        Item item;
        try {
        	item = result.getSingleResult();
        } catch (NoResultException e) {
            return Response.status(404).build();
        }
        return Response.ok(item).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Item p) {
        TypedQuery<Item> result = em.createNamedQuery("item.id", Item.class).setParameter("itemId", id);
        Item item;
        try {
        	item = result.getSingleResult();
        } catch (NoResultException e) {
            return Response.status(404).build();
        }

        if (p.getCategory() != null)
        	item.setCategory(p.getCategory());
        if (p.getName() != null)
        	item.setName(p.getName());
        if (p.getPrice() != null && p.getPrice() >= 10)
        	item.setPrice(p.getPrice());
        item.setName(item.getName().toUpperCase());
        em.persist(item);
        return Response.ok().build();
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Add(Item item) {
        if (item.getCategory() == null)
            return Response.status(400).entity("bad category").build();
        if (item.getName() == null)
            return Response.status(400).entity("bad name").build();
        if (item.getPrice() < 10)
            return Response.status(400).entity("bad price").build();
        item.setName(item.getName().toUpperCase());
        em.persist(item);
        return Response.status(201).build();
    }

    @GET
    @Path("/price")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItemsByPriceRange(@QueryParam("min") double min, @QueryParam("max") double max) {
        return em.createNamedQuery("item.id.byPrice", Item.class).setParameter("min", min).setParameter("max", max).getResultList();
    }

    @GET
    @Path("/category/{cat}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getProductsByPriceRange(@PathParam("cat") Item.Category cat) {
        return em.createNamedQuery("item.id.byPrice.id.byCategory", Item.class).setParameter("cat", cat).getResultList();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getProductsByPriceRange(@PathParam("name") String name) {
        name = "%" + name.toUpperCase() + "%";
        return em.createNamedQuery("item.id.byPrice.id.byName", Item.class).setParameter("name", name).getResultList();
    }

    @GET
    @Path("/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getComments(@PathParam("id") int id) {
        return em.createNamedQuery("item.id.byPrice.id.comments", Comment.class).setParameter("itemId", id).getResultList();
    }

    @DELETE
    @Path("/{id}/comments/{cid}")
    public Response deleteComment(@PathParam("id") int id, @PathParam("cid") int cid) {
        TypedQuery<Comment> result = em.createNamedQuery("item.id.comment.cid", Comment.class)
            .setParameter("itemId", id)
            .setParameter("commentId", cid);
        Comment comment;

        try {
            comment = result.getSingleResult();
            em.remove(comment);
        } catch (NoResultException e) {
            return Response.status(404).build();
        }
        return Response.ok(comment).build();
    }

}
