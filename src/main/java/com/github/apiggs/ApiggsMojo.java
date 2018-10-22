package com.github.apiggs;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * generate rest doc with apiggs
 */
@Mojo(name = "apiggs")
public class ApiggsMojo extends AbstractMojo {

    MavenProject project;

    @Parameter
    String id;
    @Parameter
    String title;
    @Parameter
    String description;
    @Parameter
    String out;
    //传字符串，使用逗号分隔
    String source;
    @Parameter
    String dependency;
    @Parameter
    String jar;
    @Parameter
    String ignore;
    @Parameter
    String version;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if(getPluginContext().containsKey("project") && getPluginContext().get("project") instanceof MavenProject){
            project = (MavenProject) getPluginContext().get("project");
            build();
        }
    }

    private void build(){
        Environment env = new Environment();
        if (source != null) {
            for (String dir : source.split(",")) {
                Path path = resolve(dir);
                env.source(path);
                System.out.println("source "+path);
            }
        } else {
            Path source = Paths.get(project.getBuild().getSourceDirectory());
            System.out.println("source " + source);
            env.source(source);
        }
        if (dependency != null) {
            String[] dirs = dependency.split(",");
            for (String dir : dirs) {
                Path path = resolve(dir);
                env.dependency(path);
                System.out.println("dependency "+path);
            }
        }
        if (jar != null) {
            for (String dir : jar.split(",")) {
                Path path = resolve(dir);
                env.jar(path);
                System.out.println("jar "+path);
            }
        }
        if (id != null) {
            env.id(id);
        } else {
            env.id(project.getName());
        }
        if (out != null) {
            Path path = resolve(out);
            env.out(path);
        } else {
            env.out(Paths.get(project.getBuild().getDirectory()));
        }
        if (title != null) {
            env.title(title);
        } else {
            env.title(project.getName());
        }
        if (description != null) {
            env.description(description);
        } else if (project.getDescription()!=null) {
            env.description(project.getDescription());
        }
        if (version != null){
            env.version(version);
        } else if (project.getVersion()!=null){
            env.version(project.getVersion());
        }
        if (ignore != null) {
            env.ignore(ignore.split(","));
        }

        new Apiggs(env).lookup().build();

        System.out.println("\r\n\napiggs build on "+env.getOut());
    }


    private Path resolve(String dir){
        Path path = Paths.get(dir);
        if(path.isAbsolute()){
            return path;
        }else{
            return project.getBasedir().toPath().resolve(path);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public String getIgnore() {
        return ignore;
    }

    public void setIgnore(String ignore) {
        this.ignore = ignore;
    }
}
