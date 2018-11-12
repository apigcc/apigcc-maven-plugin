package com.apigcc.maven;

import com.apigcc.Apigcc;
import com.apigcc.Context;
import com.apigcc.Options;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * generate rest doc with apiggs
 */
@Mojo(name = Context.NAME)
public class ApigccMojo extends AbstractMojo {

    MavenProject project;

    @Parameter
    String id;
    @Parameter
    String title;
    @Parameter
    String description;
    @Parameter
    String out;
    @Parameter
    String production;
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
    @Parameter
    String css;

    public void execute() {
        if(getPluginContext().containsKey("project") && getPluginContext().get("project") instanceof MavenProject){
            project = (MavenProject) getPluginContext().get("project");
            build();
        }
    }

    private void build(){
        Options options = new Options();
        if (source != null) {
            for (String dir : source.split(",")) {
                Path path = resolve(dir);
                options.source(path);
            }
        } else {
            options.source(Paths.get(project.getBuild().getSourceDirectory()));
            if(project.getCollectedProjects()!=null){
                for (MavenProject sub : project.getCollectedProjects()) {
                    options.source(Paths.get(sub.getBuild().getSourceDirectory()));
                }
            }
        }
        if (dependency != null) {
            String[] dirs = dependency.split(",");
            for (String dir : dirs) {
                Path path = resolve(dir);
                options.dependency(path);
            }
        }else{
            if(project.getParent()!=null && project.getParent().getCollectedProjects()!=null){
                for (MavenProject p : project.getParent().getCollectedProjects()) {
                    String path = p.getBuild().getSourceDirectory();
                    options.dependency(Paths.get(path));
                }
            }
        }
        if (jar != null) {
            for (String dir : jar.split(",")) {
                Path path = resolve(dir);
                options.jar(path);
            }
        }
        if (id != null) {
            options.id(id);
        } else {
            options.id(project.getName());
        }
        if (production != null){
            options.production(Paths.get(production));
        }
        if (out != null) {
            Path path = resolve(out);
            options.out(path);
        } else {
            options.out(Paths.get(project.getBuild().getDirectory()));
        }
        if (title != null) {
            options.title(title);
        } else {
            options.title(project.getName());
        }
        if (description != null) {
            options.description(description);
        } else if (project.getDescription()!=null) {
            options.description(project.getDescription());
        }
        if (version != null){
            options.version(version);
        } else if (project.getVersion()!=null){
            options.version(project.getVersion());
        }
        if (ignore != null) {
            options.ignore(ignore.split(","));
        }
        if (css != null) {
            options.css(css);
        }

        new Apigcc(options).lookup().build();

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

    public void setProduction(String production) {
        this.production = production;
    }

    public String getProduction() {
        return production;
    }
}
