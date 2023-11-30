# Bring Core

## General DI flow diagram

![Bring DI diagram](https://github.com/YevgenDemoTestOrganization/bring/assets/73576438/0e8d074a-3d49-4099-bf8e-68b029056cce)

1. The BringApplication provides a simple entry point to initialize and run a BringApplicationContext.
   It allows the user to create and configure a BringApplicationContext either by specifying a base package for component scanning or by providing a class that contains configuration information.
2. The ClassPathScannerFactory is responsible for creating a set of class path scanners, each designed to scan the classpath for specific types of annotated classes relevant to the Bring application. It initializes scanners for components, services, and configuration classes etc.
3. The BeanPostProcessorDefinitionFactory is responsible for creating and managing a list of BeanFactoryPostProcessor instances. It initializes the list with default post-processors, such as the ConfigurationClassPostProcessor etc. In this step we create Bean definitions for classes annotated with annotations from ClassPathScanner
4. The next step will be go throw all bean definitions and register/create beans with dependencies
5. The last one BeanPostProcessorFactory is responsible for creating and managing a list BeanPostProcessor instances. 
It initializes the list with default post-processors such as the ScheduleBeanPostProcessor and add addition logic to them.

## Documentations

- There are two types of documentation: Markdown (see below) and [JavaDoc](https://yevgendemotestorganization.github.io/bring-core-javadoc/)


## Features:

 - Dependency Injection
   - [Constructor](core/Constructor.md)
   - [Setter](core/Setter.md)
   - [Field](core/Field.md)
   - Collections
   - @Primary
   - @Qualifier
   - @Order
   - [@Value](core/Value.md)
   - Prototype Beans into a Singleton


 - Configuration support
   - Annotation configuration `@Component`, `@Server`.
   - Annotation `@Autowired`
   - Java Configuration `@Bean`, `@Configuration`.


- Dependency Injection exceptions
  - [Circular Dependencies](core/CircularDependencies.md)
  - NoSuchBeanException
  - NoUniqueBeanException
  - NoConstructorWithAutowiredAnnotationBeanException
  - etc


- addition items:
  - [Scheduling](core/Scheduling.md)
  - [Properties File Support](core/PropertiesFileSupport.md)
  - [Logging](core/Logging.md)
  - [@PostConstruct](core/PostConstruct.md)
  - PreDestroy
  - Logo