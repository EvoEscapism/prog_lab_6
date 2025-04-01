package client.forms;

import client.utility.console.Console;
import client.utility.Interrogator;
import common.domain.Address;
import common.domain.Organization;
import common.domain.OrganizationType;
import common.exceptions.*;

import java.util.NoSuchElementException;

/**
 * Форма организации.

 */
public class OrganizationForm extends Form<Organization> {
  private final Console console;

  public OrganizationForm(Console console) {
    this.console = console;
  }

  @Override
  public Organization build() throws IncorrectInputInScriptException, InvalidFormException {
    console.println("\n" + "Enter null or an empty label to leave the Label blank. Any other input will create a new label.");
    console.ps2();

    var fileMode = Interrogator.fileMode();
    String input = Interrogator.getUserScanner().nextLine().trim();
    if (fileMode) console.println(input);
    if (input.equals("null") || input.isEmpty()) return null;

    console.println("! Creating new organization:");
    var organization = new Organization(
      1,
      askName(),
      askEmployeesCount(),
      askType(),
      askPostalAddress()
    );

    if (!organization.validate()) throw new InvalidFormException();
    return organization;
  }

  private String askName() throws IncorrectInputInScriptException {
    String name;
    var fileMode = Interrogator.fileMode();
    while (true) {
      try {
        console.println("Enter Labels name:");
        console.ps2();

        name = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(name);
        if (name.equals("")) throw new MustBeNotEmptyException();
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Label`s name is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (MustBeNotEmptyException exception) {
        console.printError("Label`s name cannot be empty!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }

    return name;
  }

  private long askEmployeesCount() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();
    long employeesCount;
    while (true) {
      try {
        console.println("Enter amount of participants:");
        console.ps2();

        var strEmployeesCount = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strEmployeesCount);

        employeesCount = Long.parseLong(strEmployeesCount);
        long MIN_EMPLOYEES_COUNT = 1;
        if (employeesCount < MIN_EMPLOYEES_COUNT) throw new NotInDeclaredLimitsException();
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Participants amount is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NotInDeclaredLimitsException exception) {
        console.printError("Participants amount must be more than 0!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NumberFormatException exception) {
        console.printError("Participants amount must be number!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (NullPointerException | IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return employeesCount;
  }

  private OrganizationType askType() throws IncorrectInputInScriptException {
    return new OrganizationTypeForm(console).build();
  }

  private Address askPostalAddress() throws IncorrectInputInScriptException, InvalidFormException {
    return new AddressForm(console).build();
  }
}
