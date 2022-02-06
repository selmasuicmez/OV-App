# OV-app
HU-project

Clean code richtlijnensemester 1 Ad Software Development“Code is clean if it can be understood easily –by everyone on the team. Clean code can be read and enhanced by a developer other than its original author. With understandability comes readability, changeability, extensibility and maintainability.” (bron)

# Code style guide:

The Java code conventions are defined by Oracle in the coding conventions document. In short, these conventions ask the user to use camel case when defining classes, methods, or variables. Classes start with a capital letter and should be nouns, like CalendarDialogView. For methods, the names should be verbs in imperative form, like getBrakeSystemType, and should start with a lowercase letter.

It is important to get used to and follow coding conventions, so that code written by multiple programmers will appear the same. Projects may re-define the standard code conventions to better fit their needs. Examples include a list of allowed abbreviations, as these can often make the code difficult to understand for other designers. Documentation should always accompany code.

One example from the coding conventions is how to define a constant. Constants should be written with capital letters in Java, where the words are separated by an underscore ('_') character. In the Java coding conventions, a constant is a static final field in a class.

The reason for this diversion is that Java is not 100% object-oriented and discerns between "simple" and "complex" types. These will be handled in detail in the following sections. An example for a simple type is the byte type. An example for a complex type is a class. A subset of the complex types are classes that cannot be modified after creation, like a String, which is a concatenation of characters.

For instance, consider the following "constants":

```java
public class MotorVehicle {
  /** Number of motors */
  private static final int MOTORS = 1;

  /** Name of a motor */
  private static final String MOTOR_NAME = "Mercedes V8";

  /** The motor object */
  private static final Motor THE_MOTOR = new MercedesMotor();

  /**
   * Constructor
   */
  public MotorVehicle() {
    MOTORS = 2;                     // Gives a syntax error as MOTORS has already been assigned a value.
    THE_MOTOR = new ToshibaMotor(); // Gives a syntax error as THE_MOTOR has already been assigned a value.
    MOTOR_NAME.toLowercase();       // Does not give a syntax error, because it returns a new String rather than editing the MOTOR_NAME variable.
    THE_MOTOR.fillFuel(20.5);       // Does not give a syntax error, as it changes a variable in the motor object, not the variable itself.
    }
}
```
Read more at: https://en.wikibooks.org/wiki/Java_Programming/Coding_conventions



# General Rules
* Volg de standaard (naming/coding)conventions (afspraken), zowel taal gerelateerd als team gerelateerd

* Keep it simple stupid. Eenvoud is altijd beter. Reduceer complexiteit zoveel mogelijk

* Zoek altijd naar de kernoorzaak van een probleem. Los het daar ook op

* Vermijd meerdere malen van dezelfde code (duplicaten)

* Controleer altijd de waarden van onzekere bronnen, zoals bij I/O

* Schrijf altijd brackets om een een-regel block (zoals bij if, while)

* Wees consistent. Doe je iets op een bepaalde manier, doe het overal

* Gebruik verklarende namen voor variabelen, zoals ‘naam=..’ en niet ‘n=..’

* Kies betekenisvolle en duidelijke namen voor bestanden, classes, methods, etc.

* Schrijf kleine methods/functies

* Doe één ding in een method/functies

* Maak jezelf begrijpelijk in code, zoals een verhaal. Gebruik commentswijselijk en beperkt

* Verberg interne structuren in een class (encapsulation)

* Doe één ding in een class(abstraction)

* Gebruik een enkele assert per unit testcase

* Schrijf alle code en comments in het Engels

* Review je code voor compile en run, weet wat je code doet

* Gebruik een versiebeheer systeem (zoals Git)

* Geef de voorkeur aan niet-statische methoden boven statische methoden
