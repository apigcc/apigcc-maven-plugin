# apiggs-maven-plugin
easy use apiggs with maven

### install
```xml
<plugin>
    <groupId>com.github.apiggs</groupId>
    <artifactId>apiggs-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>apiggs</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- options in there -->
    </configuration>
</plugin>
```
### options



1. id 项目id，生成id.html文件
1. title 文档标题
1. description 文档描述
1. out 输出目录，默认为 target/apiggs
1. source 源码目录
1. dependency 源码依赖的代码目录
1. jar 源码依赖的jar包目录
1. ignore 忽略某些类型
1. version 文档版本号