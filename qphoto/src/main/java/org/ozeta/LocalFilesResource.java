package org.ozeta;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/files")
public class LocalFilesResource {

    @ConfigProperty(name = "resource.path") 
    String dir;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listFiles() {
        return Stream.of(new File(dir).listFiles())
        .map(File::getName)
        .collect(Collectors.toList());
    }

    @GET
    @Path("/hello")
    public String hello() {
        return "hi";
    }

    @GET
    @Path("/{name}")
    @Produces({"image/jpeg,image/png"})
    public File getImage(String name) {
        return Stream.of(new File(dir).listFiles())
        .filter(f -> f.getName().equals(name))
        .reduce((a, b) -> {
            throw new IllegalStateException("Multiple elements");
        }).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}