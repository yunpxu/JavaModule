import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ResolvedModule;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppTest {
    public static void main(String[] args) {
        //boot layer
        ModuleLayer bootLayer = ModuleLayer.boot();
        //boot configuration
        Configuration bootCfg = bootLayer.configuration();

        //path of the module
        ModuleFinder finder = ModuleFinder.of(Paths.get("/Users/yunpxu/IdeaProjects/JavaModule/out/production"));
        //configuration for module com.foo.app
        Configuration appCfg = bootCfg.resolve(finder, ModuleFinder.of(), Set.of("com.foo.app"));
        //print dependence for module com.foo.app
        appCfg.modules().stream().forEach(module1 -> {
            System.out.format("%s -> %s%n", module1.name(), module1.reads().stream().map(ResolvedModule::name).collect(Collectors.joining(", ")));
        });

        //AppClassLoader->PlatformClassLoader->null
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        //create a new layer for module com.foo.app
        ModuleLayer appLayer = bootLayer.defineModulesWithOneLoader(appCfg, systemClassLoader);
        ClassLoader appClassLoader = appLayer.findLoader("com.foo.app");
        ClassLoader barClassLoader = appLayer.findLoader("com.foo.bar");
        ClassLoader quxClassLoader = appLayer.findLoader("org.baz.qux");
        System.out.println("defineModulesWithOneLoader");
        System.out.format("com.foo.app %s %s%n", appClassLoader, appClassLoader.getParent());
        System.out.format("com.foo.bar %s %s%n", barClassLoader, barClassLoader.getParent());
        System.out.format("org.baz.qux %s %s%n", quxClassLoader, quxClassLoader.getParent());

        appLayer = bootLayer.defineModulesWithManyLoaders(appCfg, systemClassLoader);
        appClassLoader = appLayer.findLoader("com.foo.app");
        barClassLoader = appLayer.findLoader("com.foo.bar");
        quxClassLoader = appLayer.findLoader("org.baz.qux");
        System.out.println("defineModulesWithManyLoaders");
        System.out.format("com.foo.app %s %s%n", appClassLoader, appClassLoader.getParent());
        System.out.format("com.foo.bar %s %s%n", barClassLoader, barClassLoader.getParent());
        System.out.format("org.baz.qux %s %s%n", quxClassLoader, quxClassLoader.getParent());

        Function<String, ClassLoader> classLoaderFunc = (m) -> {
            if (m.contains("foo")) {
                return systemClassLoader;
            } else {
                return new ClassLoader() {
                };//dummy class loader
            }
        };
        appLayer = bootLayer.defineModules(appCfg, classLoaderFunc);
        appClassLoader = appLayer.findLoader("com.foo.app");
        barClassLoader = appLayer.findLoader("com.foo.bar");
        quxClassLoader = appLayer.findLoader("org.baz.qux");
        System.out.println("defineModulesWithCustomLoaders");
        System.out.format("com.foo.app %s %s%n", appClassLoader, appClassLoader.getParent());
        System.out.format("com.foo.bar %s %s%n", barClassLoader, barClassLoader.getParent());
        System.out.format("org.baz.qux %s %s%n", quxClassLoader, quxClassLoader.getParent());
    }
}
