Mock Framework Review
=====================

The goal of this repository is to show a side-by-side comparison of some Java mocking frameworks. However, we first 
we need to identify the key differences between frameworks so we can achieve a clear apples to apples test. 

## Frameworks
- Proxy & CGLIB
    - [Mockito](http://site.mockito.org/)
    - [EasyMock](http://easymock.org/)
- Bytecode Instrumentation
    - [JMockit](http://jmockit.org/)
    - [PowerMock](http://powermock.github.io/)

## Proxy & CGLIB vs Bytecode Instrumentation

With the release Java 3, the ability to create proxies was introduced. This allowed mocking frameworks to 
be built which handled injection using dynamic proxy via interface and CGLIB via subclasses. Below is an excerpt from
JMockit reviewing this implementation. 

> Mockito and EasyMock are based on the dynamic generation of implementation classes (through java.lang.reflect.Proxy, 
when given an interface to be mocked) and subclasses (through CGLIB, when given a non-final class to be mocked). 
This particular mocking implementation technique, in which a subclass with overriding mock methods is created, 
implies some penalties: final classes, constructors, and non-overridable methods simply cannot be mocked. 
Most importantly, however, these limitations mean that any dependency that a class under test may have on other 
classes must be controlled by the tests, so that mock instances can be passed to the clients of those dependencies; 
that is, dependencies cannot simply be instantiated with the new operator in the client class.
>
> -- [JMockit: Proxy & GCLIB](http://jmockit.org/about.html#conventionalTools)

With the release of Java 5 and further revisions in Java 6 came bytecode instrumentation. This allowed applications to 
redefine classes loaded in the JVM. Both JMockit and PowerMock have been writen to take advantage of this feature to 
grant access to final classes, constructors, and private methods.

> In JMockit, the Java SE 6+ bytecode instrumentation feature (the java.lang.instrument package) is used extensively. 
For the reading and in-memory generation of modified bytecode arrays, the ASM library is used.
>  
> Mocking features mostly use the JVM's ability to redefine classes that have already been loaded, as exposed 
through the java.lang.instrument.Instrumentation#redefineClasses(ClassDefinition...) method. This method uses the 
same mechanism behind the "hotswap" feature commonly used while debugging Java code, which allows a new version of a 
class to take the place of its current version in a running JVM.
> 
> Whenever a class to be mocked is found in the course of a test run, it has its current bytecode definition 
temporarily modified, so that the regular behavior of methods and constructors can be replaced with mock behavior, 
as specified in a test. As soon as the test is completed, the class has its bytecode restored, and its original 
behavior is resumed. This process repeats itself for the next test, whenever needed as specified in test code through 
the mocking API.
>
> -- [JMockit: Bytecode Instrumentation](http://jmockit.org/about.html#internalWorkings)

## Feature Comparison
| *Feature*                | JMockit            | PowerMock          | EasyMock           | Mockito            |
| ------------------------ | ------------------ | ------------------ | ------------------ | ------------------ |
| Standalone framework     | :white_check_mark: | :x:                | :white_check_mark: | :white_check_mark: |
| Access private fields    | :white_check_mark: | :white_check_mark: | :x:                | :x:                |
| Mock static methods      | :white_check_mark: | :white_check_mark: | :x:                | :x:                |
| Mock private methods     | :white_check_mark: | :white_check_mark: | :x:                | :x:                |
| Mock constructors        | :white_check_mark: | :white_check_mark: | :x:                | :x:                |
| Mock hard dependencies   | :white_check_mark: | :white_check_mark: | :x:                | :x:                |

## Microservice Architecture Testing Levels

![Tiered Testing](https://static1.squarespace.com/static/513914cde4b0f86e34bbb954/t/5552577be4b0c072ca6e2fd6/1431459720854/?format=400w)

- *Unit*: An isolated method with all external dependencies mocked. Unit tests prove each method within the 
application behaves as expected.
    - Recommended Owner: Developers
    - Test Naming `${name of class under test} + Test.java`: `TargetClassTest.java`
    - Triggered during `mvn test` using the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

- *Service*: A logical flow through an isolated microservice. Service tests (commonly Functional, Component, 
Integration) are called through an exposed entry point (APIs/controllers) with only external systems or databases 
mocked. This proves the business functionality (integration between methods) works given known responses from external 
dependencies. 
    - Recommended Owner: Developers
    - Test Naming `${name of entrypoint} + TestIT.java`: `UserControllerTestIT.java`
    - Triggered during `mvn verify` using the [maven-failsafe-plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/)

- *System Integration*: An execution of a collection of microservices that make up full piece of business 
functionality. System Integration tests (SIT) are called through the first microservice's exposed entry points and 
allowed to propagate through all sub-systems. It's worth noting; if the entire architecture is a single microservice, 
without any dependencies, then the SIT and Service tests are one and the same.
    - Recommended Owner: SDETs with assistance from Developers
    - Usually stored within a separate module/project. That way it can be built out and executed against the fully 
    deployed architecture.  

- *User Acceptance*: An end user flow through the system. User Acceptance tests (UAT) are commonly performed through
a UI accessing the underlying microservices. They should prove a user is able to complete an expected set of actions. 
Common UAT's would be: A user is able to register then login or a user is able to add items to a cart then checkout.
    - Recommended Owner: SDETs or QA

- *Non Functional*: A catch all for performance, security, stability, etc.
