package client.forms;

import client.utility.console.Console;
import client.utility.Interrogator;
import common.domain.OrganizationType;
import common.exceptions.*;

import java.util.NoSuchElementException;

/**
 * Форма типа организации.

 */
public class OrganizationTypeForm extends Form<OrganizationType> {
  private final Console console;

  public OrganizationTypeForm(Console console) {
    this.console = console;
  }

  @Override
  public OrganizationType build() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();

    String strOrganizationType;
    OrganizationType organizationType;
    while (true) {
      try {
        console.println("List of Types - " + OrganizationType.names());
        console.println("Enter type of Label:");
        console.ps2();

        strOrganizationType = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strOrganizationType);

        organizationType = OrganizationType.valueOf(strOrganizationType.toUpperCase());
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Type is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalArgumentException exception) {
        console.printError("There is no such Type in list!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return organizationType;
  }
}
