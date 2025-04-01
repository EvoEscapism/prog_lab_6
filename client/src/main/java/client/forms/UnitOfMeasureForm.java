package client.forms;

import client.utility.console.Console;
import client.utility.Interrogator;
import common.domain.UnitOfMeasure;
import common.exceptions.*;

import java.util.NoSuchElementException;

/**
 * Форма меры весов.

 */
public class UnitOfMeasureForm extends Form<UnitOfMeasure> {
  private final Console console;

  public UnitOfMeasureForm(Console console) {
    this.console = console;
  }

  @Override
  public UnitOfMeasure build() throws IncorrectInputInScriptException {
    var fileMode = Interrogator.fileMode();

    String strUnitOfMeasure;
    UnitOfMeasure unitOfMeasure;
    while (true) {
      try {
        console.println("Genre List - " + UnitOfMeasure.names());
        console.println("Enter genre (or null):");
        console.ps2();

        strUnitOfMeasure = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(strUnitOfMeasure);

        if (strUnitOfMeasure.equals("") || strUnitOfMeasure.equals("null")) return null;
        unitOfMeasure = UnitOfMeasure.valueOf(strUnitOfMeasure.toUpperCase());
        break;
      } catch (NoSuchElementException exception) {
        console.printError("Genre is not recognized!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalArgumentException exception) {
        console.printError("There no  such genre in List!");
        if (fileMode) throw new IncorrectInputInScriptException();
      } catch (IllegalStateException exception) {
        console.printError("Unexpected error!");
        System.exit(0);
      }
    }
    return unitOfMeasure;
  }
}
